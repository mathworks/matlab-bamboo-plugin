/**
 * Copyright 2022-2024 The MathWorks, Inc.
 */

package com.mathworks.ci.helper;

import org.junit.Test;
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.configuration.ConfigurationMapImpl;
import com.atlassian.utils.process.ExternalProcess;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityImpl;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilitySetImpl;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.mathworks.ci.helper.MatlabBuild;
import com.mathworks.ci.helper.MatlabCommandRunner;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatlabCommandRunnerTest {
    @Mock
    public ProcessService processService;

    @Mock
    public CapabilityContext capabilityContext;

    @Mock
    public TaskContext taskContext;

    @Mock
    public BuildLogger buildLogger;

    @Mock
    public ExternalProcess process;

    @InjectMocks
    public MatlabCommandRunner matlabCommandRunner;

    @Before
    public void init() {
        String PWD = System.getProperty("user.dir");
        Path mockMatlab = Paths.get(PWD, "src", "test", "resources", "MATLAB", "FakeMATLABWithVersion");

        CapabilityImpl capability = new CapabilityImpl("system.builder.matlab.R2019b", mockMatlab.toString());
        CapabilitySetImpl capabilitySet = new CapabilitySetImpl();
        capabilitySet.addCapability(capability);

        ConfigurationMapImpl configurationMap = new ConfigurationMapImpl();
        configurationMap.put(MatlabBuilderConstants.MATLAB_CFG_KEY, "R2019b");
        configurationMap.put(MatlabBuilderConstants.OPTIONS_CHX, "false");

        when(capabilityContext.getCapabilitySet()).thenReturn(capabilitySet);
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);
        when(taskContext.getBuildLogger()).thenReturn(buildLogger);
        when(taskContext.getRootDirectory()).thenReturn(new File("/home/galois"));
        when(processService.createExternalProcess(Mockito.any(TaskContext.class), Mockito.any(ExternalProcessBuilder.class))).thenReturn(process);
    }

    @Test
    public void testRunLogsMATLABVersionAndCommand() throws TaskException, IOException {
        matlabCommandRunner.run("myScript", taskContext);
        ArgumentCaptor<String> logEntry = ArgumentCaptor.forClass(String.class);
        Mockito.verify(buildLogger, Mockito.times(2)).addBuildLogEntry(logEntry.capture());
        List<String> buildLog = logEntry.getAllValues();

        assertEquals("Running task on MATLAB release: R2019b", buildLog.get(0));
        assertEquals("Running MATLAB command: myScript", buildLog.get(1));
    }

    @Test
    public void testRunReturnsExecutedProcess() throws TaskException, IOException {
        ExternalProcess actual = matlabCommandRunner.run("myScript", taskContext);
        Mockito.verify(actual, Mockito.times(1)).execute();
    }

    @Test
    public void testRunLogsErrors() throws TaskException, IOException {
        CapabilityImpl capability = new CapabilityImpl("system.builder.matlab.R2019b", "");
        CapabilitySetImpl capabilitySet = new CapabilitySetImpl();
        capabilitySet.addCapability(capability);
        when(capabilityContext.getCapabilitySet()).thenReturn(capabilitySet);

        matlabCommandRunner.run("myScript", taskContext);

        ArgumentCaptor<String> buildException = ArgumentCaptor.forClass(String.class);
        Mockito.verify(buildLogger).addErrorLogEntry(buildException.capture());

        assertEquals("MATLAB version info not found", buildException.getValue());
    }

    @Test
    public void testRunSetsOrigFolderEnvVariable() throws TaskException, IOException {
        ConfigurationMapImpl configurationMap = new ConfigurationMapImpl();
        configurationMap.put(MatlabBuilderConstants.MATLAB_CFG_KEY, "R2019b");

        matlabCommandRunner.run("myScript", taskContext);

        ArgumentCaptor<ExternalProcessBuilder> captor = ArgumentCaptor.forClass(ExternalProcessBuilder.class);
        Mockito.verify(processService).createExternalProcess(Mockito.any(TaskContext.class), captor.capture());
        List<String> arg = captor.getValue().getCommand();
        assertTrue(arg.get(1).contains("setenv('MW_ORIG_WORKING_FOLDER', cd('"));
    }

    @Test
    public void testRunIgnoresOptionsWhenUnchecked() throws TaskException, IOException {
        ConfigurationMapImpl configurationMap = new ConfigurationMapImpl();
        configurationMap.put(MatlabBuilderConstants.MATLAB_CFG_KEY, "R2019b");
        configurationMap.put(MatlabBuilderConstants.OPTIONS_CHX, "false");
        configurationMap.put(MatlabBuilderConstants.MATLAB_OPTIONS_KEY, "-nojvm -display -logfile myfile.log");
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);

        matlabCommandRunner.run("myScript", taskContext);

        ArgumentCaptor<ExternalProcessBuilder> captor = ArgumentCaptor.forClass(ExternalProcessBuilder.class);
        Mockito.verify(processService).createExternalProcess(Mockito.any(TaskContext.class), captor.capture());
        List<String> arg = captor.getValue().getCommand();
        assertFalse(arg.containsAll(Arrays.asList("-nojvm -display -logfile myfile.log")));
    }

    @Test
    public void testRunUsesStartupOptions() throws TaskException, IOException {
        ConfigurationMapImpl configurationMap = new ConfigurationMapImpl();
        configurationMap.put(MatlabBuilderConstants.MATLAB_CFG_KEY, "R2019b");
        configurationMap.put(MatlabBuilderConstants.OPTIONS_CHX, "true");
        configurationMap.put(MatlabBuilderConstants.MATLAB_OPTIONS_KEY, "-nojvm -display -logfile myfile.log");
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);

        matlabCommandRunner.run("myScript", taskContext);

        ArgumentCaptor<ExternalProcessBuilder> captor = ArgumentCaptor.forClass(ExternalProcessBuilder.class);
        Mockito.verify(processService).createExternalProcess(Mockito.any(TaskContext.class), captor.capture());
        List<String> arg = captor.getValue().getCommand();
        assertTrue(arg.containsAll(Arrays.asList("-nojvm", "-display", "-logfile", "myfile.log")));
    }
}
