package com.mathworks.ci.task;

/**
 * Copyright 2020 The MathWorks, Inc.
 */
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.utils.process.ExternalProcess;
import com.atlassian.bamboo.utils.SystemProperty;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import org.jetbrains.annotations.NotNull;
import com.mathworks.ci.helper.MatlabBuild;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FilenameUtils;
import java.util.*;
import java.io.*;

@Scanned
public class MatlabTestTask implements TaskType, MatlabBuild
{

    private String matlabTestOptions;

    @ComponentImport
    private final ProcessService processService;

    @ComponentImport
    private final CapabilityContext capabilityContext;

     public MatlabTestTask(ProcessService processService, CapabilityContext capabilityContext) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException{
     TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
     BuildLogger buildLogger = taskContext.getBuildLogger();
     File tempDirectory = getTempWorkingDirectory();
     matlabTestOptions = getInputArguments(taskContext);
     System.out.println(matlabTestOptions);
     String matlabRoot = getMatlabRoot(taskContext, capabilityContext);
     //TODO: Need to validate matlabRoot  
        if (!StringUtils.isNotEmpty(matlabRoot)) {
            buildLogger.addErrorLogEntry("Invalid MATLAB Executable");
            return taskResultBuilder.failedWithError().build();
        }
        buildLogger.addBuildLogEntry("Running MATLAB tests: ");
        try {
            ExternalProcessBuilder processBuilder = new ExternalProcessBuilder()
                .workingDirectory(taskContext.getRootDirectory())
                .command(getMatlabCommandScript(taskContext.getRootDirectory(), tempDirectory))
                .env(SystemProperty.PATH.getKey(),matlabRoot);
            ExternalProcess process = processService.createExternalProcess(taskContext, processBuilder);
            process.execute();
            taskResultBuilder.checkReturnCode(process);
            //clearTempDirectory(tempDirectory);
        } catch (Exception e) {
            buildLogger.addErrorLogEntry(e.getMessage());
        }
        return taskResultBuilder.build();
    }

    private List < String > getMatlabCommandScript(File rootDirectory, File tempDirectory) throws IOException {
        List < String > command = new ArrayList < > ();
      
        command.add(getPlatformSpecificRunner(tempDirectory));
        command.add(constructCommandForTest(tempDirectory));
        prepareTmpFldr(tempDirectory, getRunnerScript(
                   MatlabBuilderConstants.TEST_RUNNER_SCRIPT, matlabTestOptions));
        System.out.println(command);
        return command;
    }

    public String constructCommandForTest(File scriptPath) {
       final String matlabScriptName = getValidMatlabFileName(FilenameUtils.getBaseName(scriptPath.toString()));
       final String runCommand = "addpath('" + scriptPath.toString().replaceAll("'", "''")
                + "'); " + matlabScriptName;
       System.out.println(runCommand);
       return runCommand;
    }


    // Concatenate the input arguments
    private String getInputArguments(TaskContext taskContext) {

        final List<String> inputArgsList = new ArrayList<String>();
        inputArgsList.add("'Test'");

        if(Boolean.parseBoolean(taskContext.getConfigurationMap().get("junitChecked"))){
        inputArgsList.add("'" + "JUnitTestResults"  + "'" + "," + "'" + taskContext.getConfigurationMap().get("junit") + "'");
        }

        if(Boolean.parseBoolean(taskContext.getConfigurationMap().get("htmlCoverageChecked"))){
        inputArgsList.add("'" + "HTMLCodeCoverage"  + "'" + "," + "'" + taskContext.getConfigurationMap().get("html") + "'");
        }

        /*
        * Add source folder options to argument.
        * For source folder we create a MATLAB cell array and add it to input argument list.
        * */
        
        if(Boolean.parseBoolean(taskContext.getConfigurationMap().get("srcFolderChecked"))){
        List<String> listOfSrcFolders = Arrays.asList((taskContext.getConfigurationMap().get("srcfolder")).trim());
        listOfSrcFolders.replaceAll(val -> "'" + val + "'");
        inputArgsList.add("'" + "SourceFolder"  + "'" + "," +  listOfSrcFolders);
        }
        
        return String.join(",", inputArgsList);
    }


}
