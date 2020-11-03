package com.mathworks.ci.task;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.v2.build.agent.capability.ReadOnlyCapabilitySet;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.utils.process.ExternalProcess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import com.atlassian.bamboo.utils.SystemProperty;
import com.mathworks.ci.helper.MatlabBuild;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import java.util.*;
import java.io.*;

/**
 * Run MATLAB Command Task Invocation
 *
 *
 */


@Scanned
public class MatlabCommandTask implements TaskType, MatlabBuild {
    private String matlabCommand;

    @ComponentImport
    private final ProcessService processService;

    @ComponentImport
    private final CapabilityContext capabilityContext;


    public MatlabCommandTask(ProcessService processService, CapabilityContext capabilityContext) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
    }


    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
        BuildLogger buildLogger = taskContext.getBuildLogger();
        File tempDirectory = getTempWorkingDirectory();
        matlabCommand = taskContext.getConfigurationMap().get(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY);
        String matlabRoot = getMatlabRoot(taskContext, capabilityContext);
        //TODO: Need to validate matlabRoot  
        if (!StringUtils.isNotEmpty(matlabRoot)) {
            buildLogger.addErrorLogEntry("Invalid MATLAB Executable");
            return taskResultBuilder.failedWithError().build();
        }
        buildLogger.addBuildLogEntry("Running MATLAB command: " + matlabCommand);
        try {
            ExternalProcessBuilder processBuilder = new ExternalProcessBuilder()
                .workingDirectory(taskContext.getRootDirectory())
                .command(getMatlabCommandScript(taskContext.getRootDirectory(), tempDirectory))
                .env(SystemProperty.PATH.getKey(),matlabRoot);
            ExternalProcess process = processService.createExternalProcess(taskContext, processBuilder);
            process.execute();
            taskResultBuilder.checkReturnCode(process);
            clearTempDirectory(tempDirectory);
        } catch (Exception e) {
            buildLogger.addErrorLogEntry(e.getMessage());
        }
        return taskResultBuilder.build();
    }

    private List < String > getMatlabCommandScript(File rootDirectory, File tempDirectory) throws IOException {
        List < String > command = new ArrayList < > ();
        final String uniqueCommandFile =
            "command_" + getUniqueNameForRunnerFile().replaceAll("-", "_");
        String commandToExecute = "cd('" + tempDirectory.toString().replaceAll("'","''") + "');" + uniqueCommandFile;

        // Create MATLAB script
        createMatlabScriptByName(tempDirectory, uniqueCommandFile, rootDirectory);
        command.add(getPlatformSpecificRunner(tempDirectory));
        command.add(commandToExecute);
        return command;
    }

    // Create a new command runner script in the temp folder.
    private void createMatlabScriptByName(File uniqeTmpFolderPath, String uniqueScriptName, File workspace) throws IOException {
        final File matlabCommandFile =
            new File(uniqeTmpFolderPath, uniqueScriptName + ".m");
        final String matlabCommandFileContent =
            "cd '" + workspace.toString().replaceAll("'", "''") + "';\n" + matlabCommand;
        System.out.println(matlabCommandFileContent);
        BufferedWriter writer = new BufferedWriter(new FileWriter(matlabCommandFile));
        writer.write(matlabCommandFileContent);
        writer.close();
    }

}
