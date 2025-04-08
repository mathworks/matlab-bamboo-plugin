/**
 * Copyright 2020-2022 The MathWorks, Inc.
 * 
 * Run MATLAB Command Task Invocation
 */

package com.mathworks.ci.task;

import javax.inject.Inject;
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.utils.process.ExternalProcess;
import com.mathworks.ci.helper.MatlabCommandRunner;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import org.jetbrains.annotations.NotNull;

public class MatlabCommandTask implements TaskType {
    @Inject
    @ComponentImport
    private final ProcessService processService;

    @Inject
    @ComponentImport
    private final CapabilityContext capabilityContext;

    private MatlabCommandRunner matlabCommandRunner;

    public MatlabCommandTask(ProcessService processService, CapabilityContext capabilityContext, MatlabCommandRunner matlabCommandRunner) {
        this.processService = processService;
        this.capabilityContext = capabilityContext;
        this.matlabCommandRunner = matlabCommandRunner;
    }

    public MatlabCommandTask(ProcessService processService, CapabilityContext capabilityContext) {
        this(processService, capabilityContext, new MatlabCommandRunner(processService, capabilityContext));
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
        BuildLogger buildLogger = taskContext.getBuildLogger();

        String matlabCommand = taskContext.getConfigurationMap().get(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY);

        try {
            ExternalProcess process = matlabCommandRunner.run(matlabCommand, taskContext);
            taskResultBuilder.checkReturnCode(process);
        } catch (Exception e) {
            buildLogger.addErrorLogEntry(e.getMessage());
        } finally {
            matlabCommandRunner.cleanup(buildLogger);
        }
        return taskResultBuilder.build();
    }
}
