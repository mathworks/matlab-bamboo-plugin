package com.mathworks.ci.task;

import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.utils.process.ExternalProcess;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class MATLABTestTask extends BaseMATLABTaskType
{

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) {
       // TaskResultBuilder builder = TaskResultBuilder.create(taskContext);
       // final String matlabCommand = taskContext.getConfigurationMap().get("matlabcommand");
       // final String matlabRoot = taskContext.getConfigurationMap().get("MATLABVersion");

        // TODO
        // To replace this logic with using Run MATLAB Command script
        // Also account for linux and windows
       /** ExternalProcess process = processService.createExternalProcess(taskContext,
                new ExternalProcessBuilder()
                        .command(Arrays.asList(matlabRoot, "-batch", matlabCommand))
                        .workingDirectory(taskContext.getWorkingDirectory()));

        process.execute();**/

        return null;
    }



}
