package com.mathworks.ci.task;

/**
 * Copyright 2022 The MathWorks, Inc.
 */

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.utils.process.ExternalProcess;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.mathworks.ci.helper.MatlabCommandRunner;
import org.jetbrains.annotations.NotNull;

/**
 * Run MATLAB Build Task Invocation
 */

@Scanned
public class MatlabBuildTask implements TaskType {
    @ComponentImport
    private final ProcessService processService;

    @ComponentImport
    private final CapabilityContext capabilityContext;

    private MatlabCommandRunner matlabCommandRunner;

    public MatlabBuildTask(ProcessService processService, CapabilityContext capabilityContext) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
        this.matlabCommandRunner = new MatlabCommandRunner(processService, capabilityContext);
    }

    public MatlabBuildTask(ProcessService processService, CapabilityContext capabilityContext, MatlabCommandRunner matlabCommandRunner) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
        this.matlabCommandRunner = matlabCommandRunner;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
        BuildLogger buildLogger = taskContext.getBuildLogger();

        // Construct buildtool command from inputs
        String buildTasks = taskContext.getConfigurationMap().get(MatlabBuilderConstants.MATLAB_BUILD_TASKS).trim();
        String buildtoolCommand = "buildtool";
        if (!buildTasks.trim().isEmpty()) {
            buildtoolCommand += " " + buildTasks;
        }

        try {
            ExternalProcess process = matlabCommandRunner.run(buildtoolCommand, taskContext);
            taskResultBuilder.checkReturnCode(process);
        } catch (Exception e) {
            buildLogger.addErrorLogEntry(e.getMessage());
        }
        return taskResultBuilder.build();
    }
}
