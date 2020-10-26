package com.mathworks.ci.task;

import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.mathworks.ci.helper.MatlabBuild;
import com.atlassian.bamboo.process.EnvironmentVariableAccessor;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.utils.process.ExternalProcess;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.io.*;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.SystemUtils;


@Scanned
public class MatlabTestTask implements TaskType, MatlabBuild{

    // MATLAB runner script
    static final String TEST_RUNNER_SCRIPT = "testScript = genscript(${PARAMS});\n" + "\n"
            + "disp('Running MATLAB script with content:');\n"
            + "disp(strtrim(testScript.writeToText()));\n"
            + "fprintf('___________________________________\\n\\n');\n" + "run(testScript);\n" + "";
    String testOptions="";

    @ComponentImport
    private final ProcessService processService;

    @ComponentImport
    private final CapabilityContext capabilityContext;

    @ComponentImport
    private final EnvironmentVariableAccessor environmentVariableAccessor;

    public MatlabTestTask(ProcessService processService, CapabilityContext capabilityContext,EnvironmentVariableAccessor environmentVariableAccessor) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
        this.environmentVariableAccessor = environmentVariableAccessor;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) {
       List<String> command = new ArrayList<>();
       TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
       File bambooWorkspace = taskContext.getRootDirectory();
       File matlabWorkspace = getTempWorkingDirectory();
       Map<String, String> environment = Maps.newHashMap(environmentVariableAccessor.getEnvironment(taskContext));
       environment = buildEnvironment(taskContext,environment,capabilityContext);
       testOptions = getInputArguments(taskContext);
       try{
       command = getMatlabCommandScript(bambooWorkspace,matlabWorkspace);
       }catch(Exception e){
       }
       System.out.println("Hieeeeeeeeeee"+command); 
       return null;
    }


    private List<String> getMatlabCommandScript(File bambooWorkspace, File matlabWorkspace)throws IOException, InterruptedException{
    List<String> command = new ArrayList<>();
    final String uniqueTmpFldrName = getUniqueNameForRunnerFile();
    File genScriptLocation = matlabWorkspace;
    System.out.println(genScriptLocation);
    command.add(getPlatformSpecificRunner(uniqueTmpFldrName,matlabWorkspace));
    command.add(constructCommandForTest(genScriptLocation));
    // copy genscript package in temp folder and write a runner script.
    prepareTmpFldr(genScriptLocation, getRunnerScript(
                   TEST_RUNNER_SCRIPT, testOptions));
    System.out.println("Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" + command); 
    return command;
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

    public String constructCommandForTest(File scriptPath) {
       final String matlabScriptName = getValidMatlabFileName("temp");
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

        //if(Boolean.parseBoolean(taskContext.getConfigurationMap().get("htmlCoverageChecked"))){
        //inputArgsList.add("'" + "HtmlCodeCoverage"  + "'" + "," + "'" + taskContext.getConfigurationMap().get("html") + "'");
        //}

        /*
        * Add source folder options to argument.
        * For source folder we create a MATLAB cell array and add it to input argument list.
        * */
        
        return String.join(",", inputArgsList);
    }


}
