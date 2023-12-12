/**
 * Copyright 2022-2023 The MathWorks, Inc.
 */

package com.mathworks.ci.helper;

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
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import net.lingala.zip4j.ZipFile;

public class MatlabCommandRunner implements MatlabBuild {
    private File tempDirectory = getTempWorkingDirectory();
    private final CapabilityContext capabilityContext;
    private final ProcessService processService;

    public MatlabCommandRunner(ProcessService processService, CapabilityContext capabilityContext) {
        this.capabilityContext = capabilityContext;
        this.processService = processService;
    }

    public ExternalProcess run(String matlabCommand, TaskContext taskContext) throws IOException {
        BuildLogger buildLogger = taskContext.getBuildLogger();
        File workingDirectory = taskContext.getRootDirectory();
        String matlabRoot = getMatlabRoot(taskContext, capabilityContext, buildLogger);
        buildLogger.addBuildLogEntry("Running MATLAB command: " + matlabCommand);
        List<String> command = generateCommand(matlabCommand, workingDirectory);
        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get(MatlabBuilderConstants.OPTIONS_CHX))) {
            String startupOpts = taskContext.getConfigurationMap().get(MatlabBuilderConstants.MATLAB_OPTIONS_KEY);
            command.addAll(Arrays.asList(startupOpts.split(" ")));
            command.removeIf(s -> s.isEmpty());
        }
        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder()
            .workingDirectory(workingDirectory)
            .command(command)
            .env(SystemProperty.PATH.getKey(), matlabRoot);
        ExternalProcess process = processService.createExternalProcess(taskContext, processBuilder);
        process.execute();
        return process;    
    }

    public void unzipToTempDir(String zipName) throws IOException {
        // copy zip to tempDirectory
        File zipFileLocation = new File(tempDirectory, zipName);
        copyFileInWorkspace(zipName, zipFileLocation.toString());

        // Unzip the file to temp folder
        ZipFile zipFile = new ZipFile(zipFileLocation);
        zipFile.extractAll(tempDirectory.toString());
    }

    public void cleanup(BuildLogger buildLogger) {
        clearTempDirectory(tempDirectory, buildLogger);
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
