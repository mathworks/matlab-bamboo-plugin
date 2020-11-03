package com.mathworks.ci.task;

/**
 * Copyright 2020 The MathWorks, Inc.
 */

import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.utils.process.ExternalProcess;
import org.jetbrains.annotations.NotNull;

@Scanned
public class MatlabTestTask implements TaskType
{

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) {


        return null;
    }



}
