/**
 * Copyright 2020-2022 The MathWorks, Inc.
 * 
 * Run MATLAB Test Task Invocation
 */

package com.mathworks.ci.task;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.utils.process.ExternalProcess;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.mathworks.ci.helper.MatlabCommandRunner;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@Scanned
public class MatlabTestTask implements TaskType {
    @ComponentImport
    private final ProcessService processService;

    @ComponentImport
    private final CapabilityContext capabilityContext;

    private MatlabCommandRunner matlabCommandRunner;

    public MatlabTestTask(ProcessService processService, CapabilityContext capabilityContext, MatlabCommandRunner matlabCommandRunner) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
        this.matlabCommandRunner = matlabCommandRunner;
    }

    public MatlabTestTask(ProcessService processService, CapabilityContext capabilityContext) {
        this(processService, capabilityContext, new MatlabCommandRunner(processService, capabilityContext));
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
        BuildLogger buildLogger = taskContext.getBuildLogger();

        String matlabTestOptions = getInputArguments(taskContext);
        String testCommand = getRunnerScript(MatlabBuilderConstants.TEST_RUNNER_SCRIPT, matlabTestOptions);

        buildLogger.addBuildLogEntry("Running MATLAB tests: ");
        try {
            matlabCommandRunner.unzipToTempDir("matlab-script-generator.zip");
            ExternalProcess process = matlabCommandRunner.run(testCommand, taskContext);
            taskResultBuilder.checkReturnCode(process);
        } catch (Exception e) {
            buildLogger.addErrorLogEntry(e.getMessage());
        } finally {
            matlabCommandRunner.cleanup(buildLogger);
        }
        return taskResultBuilder.build();
    }

    String getRunnerScript(String script, String params) {
        script = script.replace("${PARAMS}", params);
        return script;
    }

    String getInputArguments(TaskContext taskContext) {

        final List<String> inputArgsList = new ArrayList<String>();
        inputArgsList.add("'Test'");

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("junitChecked"))) {
            inputArgsList.add("'JUnitTestResults'" + "," + "'" + taskContext.getConfigurationMap().get("junit").trim().replaceAll("'", "''") + "'");
        }

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("pdfChecked"))) {
            inputArgsList.add("'PDFTestReport'" + "," + "'" + taskContext.getConfigurationMap().get("pdf").trim().replaceAll("'", "''") + "'");
        }

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("htmlCoverageChecked"))) {
            inputArgsList.add("'HTMLCodeCoverage'" + "," + "'" + taskContext.getConfigurationMap().get("html").trim().replaceAll("'", "''") + "'");
        }

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("stmChecked"))) {
            inputArgsList.add("'SimulinkTestResults'" + "," + "'" + taskContext.getConfigurationMap().get("stm").trim().replaceAll("'", "''") + "'");
        }

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("htmlModelCoverageChecked"))) {
            inputArgsList.add("'HTMLModelCoverage'" + "," + "'" + taskContext.getConfigurationMap().get("htmlModel").trim().replaceAll("'", "''") + "'");
        }
        
        /*
         * Add source folder options to argument.
         * */

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("srcFolderChecked"))) {
            inputArgsList.add("'SourceFolder'" + "," + "'" + taskContext.getConfigurationMap().get("srcfolder").trim().replaceAll("'", "''") + "'");
        }

        /*
         * Add Test Selection options to argument.
         * */

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("byFolderChecked"))) {
            inputArgsList.add("'SelectByFolder'" + "," + "'" + taskContext.getConfigurationMap().get("testFolders").trim().replaceAll("'", "''") + "'");
        }

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("byTagChecked"))) {
            inputArgsList.add("'SelectByTag'" + "," + "'" + taskContext.getConfigurationMap().get("testTag").trim().replaceAll("'", "''") + "'");
        }

        /*
         * Add test run customization options to argument.
         * */

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("strictChecked"))) {
            inputArgsList.add("'Strict'" + "," + taskContext.getConfigurationMap().get("strictChecked"));
        }

        if (Boolean.parseBoolean(taskContext.getConfigurationMap().get("useParallelChecked"))) {
            inputArgsList.add("'UseParallel'" + "," + taskContext.getConfigurationMap().get("useParallelChecked"));
        }

        if (!(taskContext.getConfigurationMap().get("outputDetail").equals("Default"))) {
            inputArgsList.add("'OutputDetail'" + "," + "'" + taskContext.getConfigurationMap().get("outputDetail").trim().replaceAll("'", "''") + "'");
        }

        if (!(taskContext.getConfigurationMap().get("loggingLevel").equals("Default"))) {
            inputArgsList.add("'LoggingLevel'" + "," + "'" + taskContext.getConfigurationMap().get("loggingLevel").trim().replaceAll("'", "''") + "'");
        }

        return String.join(",", inputArgsList);
    }
}
