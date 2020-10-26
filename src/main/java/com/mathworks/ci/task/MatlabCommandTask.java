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
import com.atlassian.bamboo.process.EnvironmentVariableAccessor;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.utils.process.ExternalProcess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.Maps;
import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import com.atlassian.bamboo.utils.SystemProperty;
import com.mathworks.ci.helper.MatlabBuild;
import java.util.*;
import java.io.*;


@Scanned
public class MatlabCommandTask implements TaskType, MatlabBuild { 

    private static final String MATLAB_COMMAND_CFG_KEY = "matlabCommand";
    private static final String MATLAB_CFG_KEY = "matlabExecutable";
    private static final String MATLAB_PREFIX = "system.builder.matlab.";
    private String matlabCommand;

    @ComponentImport
    private final ProcessService processService;

    @ComponentImport
    private final CapabilityContext capabilityContext;

    @ComponentImport
    private final EnvironmentVariableAccessor environmentVariableAccessor;

    public MatlabCommandTask(ProcessService processService, CapabilityContext capabilityContext,EnvironmentVariableAccessor environmentVariableAccessor) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
        this.environmentVariableAccessor = environmentVariableAccessor;
    }


    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException{
       List<String> command = new ArrayList<>();
       TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
       Map<String, String> environment = Maps.newHashMap(environmentVariableAccessor.getEnvironment(taskContext));
       environment = buildEnvironment(taskContext,environment,capabilityContext);
       File bambooWorkspace = taskContext.getRootDirectory();
       File matlabWorkspace = getTempWorkingDirectory();
       matlabCommand = taskContext.getConfigurationMap().get(MATLAB_COMMAND_CFG_KEY);
       try{
       command = getMatlabCommandScript(bambooWorkspace,matlabWorkspace);
       }catch(Exception e){
       }
       ExternalProcessBuilder processBuilder = new ExternalProcessBuilder()
                .workingDirectory(matlabWorkspace).command(command).env(environment).env(SystemProperty.PATH.getKey(),environment.get("matlab_root"));
       ExternalProcess process = processService.createExternalProcess(taskContext, processBuilder);
       process.execute();
       taskResultBuilder.checkReturnCode(process);                            
       return taskResultBuilder.build();
    }

    private List<String> getMatlabCommandScript(File bambooWorkspace, File matlabWorkspace)throws IOException, InterruptedException{
    List<String> command = new ArrayList<>();
    final String uniqueTmpFldrName = getUniqueNameForRunnerFile();
    final String uniqueCommandFile =
                "command_" + getUniqueNameForRunnerFile().replaceAll("-", "_");
    // Create MATLAB script
    createMatlabScriptByName(matlabWorkspace, uniqueCommandFile, bambooWorkspace);
    command.add(getPlatformSpecificRunner(uniqueTmpFldrName,matlabWorkspace));
    command.add(uniqueCommandFile);
    return command;
    }

    private void createMatlabScriptByName(File uniqeTmpFolderPath, String uniqueScriptName, File workspace) throws IOException, InterruptedException {

        // Create a new command runner script in the temp folder.
        final File matlabCommandFile =
                new File(uniqeTmpFolderPath, uniqueScriptName + ".m");
        final String matlabCommandFileContent =
                "cd '" + workspace.toString().replaceAll("'", "''") + "';\n" + matlabCommand;
        FileOutputStream is = new FileOutputStream(matlabCommandFile);
        OutputStreamWriter osw = new OutputStreamWriter(is);
        Writer w = new BufferedWriter(osw);
        w.write(matlabCommandFileContent);
        w.close();
    }

   private String getPlatformSpecificRunner(String uniqueName,File matlabWorkspace)throws IOException, InterruptedException{
   String runMatlabCommandScript = null;
   String runnerScriptName=null;
   if(SystemUtils.IS_OS_WINDOWS){
      runnerScriptName = "run_matlab_command.bat";
      runMatlabCommandScript= "run_matlab_command.bat";
   }
   else{
      runnerScriptName = "run_matlab_command.sh";
      runMatlabCommandScript= "./run_matlab_command.sh";
   }  
   copyFileInWorkspace(runnerScriptName,uniqueName,matlabWorkspace);  
   return runMatlabCommandScript;
   }
  
}
