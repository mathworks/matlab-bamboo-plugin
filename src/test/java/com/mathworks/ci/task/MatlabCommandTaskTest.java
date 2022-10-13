/**
 * Copyright 2022 The MathWorks, Inc.
 */

package com.mathworks.ci.task;

import org.junit.Test;
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.configuration.ConfigurationMapImpl;
import com.atlassian.utils.process.ExternalProcess;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.mathworks.ci.helper.MatlabBuild;
import com.mathworks.ci.helper.MatlabCommandRunner;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatlabCommandTaskTest {
    @Mock
    public MatlabCommandRunner matlabCommandRunner;

    @Mock
    public ProcessService processService;

    @Mock
    public CapabilityContext capabilityContext;

    @Mock
    public TaskContext taskContext;

    @Mock
    public BuildLogger buildLogger;

    @Mock
    public TaskResultBuilder resultBuilder;

    @InjectMocks
    MatlabCommandTask task;

    @Before
    public void init() {
        when(taskContext.getBuildLogger()).thenReturn(buildLogger);
    }

    @Test
    public void testExectuteRunsWithProvidedCommand() throws TaskException, IOException {
        ConfigurationMap configurationMap = new ConfigurationMapImpl();
        configurationMap.put(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY, "disp('yo world')");
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);

        try (MockedStatic<TaskResultBuilder> taskResultBuilder = Mockito.mockStatic(TaskResultBuilder.class)) {
            taskResultBuilder.when(() -> TaskResultBuilder.newBuilder(Mockito.any()))
                .thenReturn(resultBuilder);
            task.execute(taskContext);
        }
        ArgumentCaptor<String> matlabCommand = ArgumentCaptor.forClass(String.class);
        Mockito.verify(matlabCommandRunner).run(matlabCommand.capture(), Mockito.any());

        assertEquals("disp('yo world')", matlabCommand.getValue());
    }

    @Test
    public void testExecuteExceptionsAreAddedToBuildlog() throws TaskException, IOException {
        ConfigurationMap configurationMap = new ConfigurationMapImpl();
        configurationMap.put(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY, "disp('yo world')");
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);
        when(matlabCommandRunner.run(Mockito.any(), Mockito.any())).thenThrow(new IOException("BAM!"));

        try (MockedStatic<TaskResultBuilder> taskResultBuilder = Mockito.mockStatic(TaskResultBuilder.class)) {
            taskResultBuilder.when(() -> TaskResultBuilder.newBuilder(Mockito.any()))
                .thenReturn(resultBuilder);
            task.execute(taskContext);
        }
        ArgumentCaptor<String> buildException = ArgumentCaptor.forClass(String.class);
        Mockito.verify(buildLogger).addErrorLogEntry(buildException.capture());

        assertEquals("BAM!", buildException.getValue());
    }
}
