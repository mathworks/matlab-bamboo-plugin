package com.mathworks.ci.helper;

/**
 * Copyright 2022 The MathWorks, Inc.
 */

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.utils.SystemProperty;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.utils.process.ExternalProcess;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import net.lingala.zip4j.ZipFile;

public class MatlabCommandRunner implements MatlabBuild {
    private final File tempDirectory;
    private final CapabilityContext capabilityContext;
    private final ProcessService processService;

    public MatlabCommandRunner(ProcessService processService, CapabilityContext capabilityContext) {
        this.tempDirectory = getTempWorkingDirectory();
        this.capabilityContext = capabilityContext;
        this.processService = processService;
    }

    public ExternalProcess run(String matlabCommand, TaskContext taskContext) throws IOException {
        BuildLogger buildLogger = taskContext.getBuildLogger();
        File workingDirectory = taskContext.getRootDirectory();
        String matlabRoot = getMatlabRoot(taskContext, capabilityContext, buildLogger);
        if (!StringUtils.isNotEmpty(matlabRoot)) {
            buildLogger.addErrorLogEntry("Invalid MATLAB Executable");
        }
        try {
            buildLogger.addBuildLogEntry("Running MATLAB command: " + matlabCommand);
            List<String> command = generateCommand(matlabCommand, workingDirectory);
            ExternalProcessBuilder processBuilder = new ExternalProcessBuilder()
                .workingDirectory(workingDirectory)
                .command(command)
                .env(SystemProperty.PATH.getKey(), matlabRoot);
            ExternalProcess process = processService.createExternalProcess(taskContext, processBuilder);
            process.execute();
            return process;    
        } finally {
            clearTempDirectory(tempDirectory, buildLogger);
        }
    }

    public void getScriptgen() throws IOException {
        // copy genscript package
        copyFileInWorkspace("matlab-script-generator.zip", tempDirectory);
        File zipFileLocation = new File(tempDirectory, "matlab-script-generator.zip");

        // Unzip the file in temp folder.
        ZipFile zipFile = new ZipFile(zipFileLocation);
        zipFile.extractAll(tempDirectory.toString());
    }

    @NotNull
    List<String> generateCommand(String matlabCommand, File workingDirectory) throws IOException {
        List<String> command = new ArrayList<>();
        final String uniqueCommandFile =
            "matlab_" + UUID.randomUUID().toString().replaceAll("-", "_");
        String commandToExecute = "addpath('" + tempDirectory.toString().replaceAll("'", "''") + "');" + uniqueCommandFile;

        // Create MATLAB script
        createMatlabScriptByName(matlabCommand, workingDirectory, tempDirectory, uniqueCommandFile);
        command.add(getPlatformSpecificRunner(tempDirectory));
        command.add(commandToExecute);
        return command;
    }

    // Create a new command runner script in the temp folder.
    private void createMatlabScriptByName(String command, File workingDirectory, File uniqeTmpFolderPath, String uniqueScriptName) throws IOException {
        final File matlabCommandFile =
            new File(uniqeTmpFolderPath, uniqueScriptName + ".m");
        final String matlabCommandFileContent =
            "cd '" + workingDirectory.toString().replaceAll("'", "''") + "';\n" + command;
        BufferedWriter writer = new BufferedWriter(new FileWriter(matlabCommandFile));
        writer.write(matlabCommandFileContent);
        writer.close();
    }
}
