package com.mathworks.ci.task;

/**
 * Copyright 2020 - 2022 The MathWorks, Inc.
 */

import org.junit.Test;
import com.mathworks.ci.helper.MatlabCommandRunner;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.v2.build.agent.capability.ReadOnlyCapabilitySet;
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityImpl;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilitySetImpl;
import com.atlassian.bamboo.configuration.ConfigurationMapImpl;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.mathworks.ci.helper.MatlabBuild;

import java.util.Map;
import java.util.HashMap;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.util.*;

import org.junit.rules.TemporaryFolder;
import org.junit.Rule;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MatlabBuildTest {
    @InjectMocks
    public MatlabCommandRunner matlabCommandRunner;

    @Mock
    public MatlabBuild matlabBuild;

    @Mock
    public BuildLogger buildlogger;

    @Mock
    public CapabilityContext capabilityContext;

    @Mock
    public TaskContext taskContext;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private String runnerScript;
    private String runnerFile;
    private File tempFile;
    private File tempFolder;

    @Before
    public void init() throws IOException {
        runnerScript = SystemUtils.IS_OS_WINDOWS ? "run_matlab_command.bat" : "./run_matlab_command.sh";
        runnerFile = SystemUtils.IS_OS_WINDOWS ? "run_matlab_command.bat" : "run_matlab_command.sh";
        tempFile = testFolder.newFile("temp_File");
        tempFolder = testFolder.newFolder("temp_Folder");
    }

    @Test
    public void testGetMatlabRoot() {
        CapabilityImpl capability = new CapabilityImpl("system.builder.matlab.R2019b", "local-ssd/Downloads/R2019b");
        CapabilitySetImpl capabilitySet = new CapabilitySetImpl();
        Map<String, String> map = new HashMap<>();
        capabilitySet.addCapability(capability);
        map.put(MatlabBuilderConstants.MATLAB_CFG_KEY, "R2019b");
        ConfigurationMapImpl configurationMap = new ConfigurationMapImpl(map);
        when(capabilityContext.getCapabilitySet()).thenReturn(capabilitySet);
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);
        assertEquals(matlabCommandRunner.getMatlabRoot(taskContext, capabilityContext, buildlogger), "local-ssd/Downloads/R2019b/bin");
    }

    @Test
    public void testGetTempWorkingDirectory() {
        when(matlabBuild.getTempWorkingDirectory()).thenReturn(tempFile);
        assertEquals(matlabBuild.getTempWorkingDirectory(), tempFile);
    }


    @Test
    public void testGetPlatformSpecificRunner() throws IOException {
        when(matlabBuild.getPlatformSpecificRunner(tempFolder)).thenReturn(runnerScript);
        assertEquals(matlabBuild.getPlatformSpecificRunner(tempFolder), runnerScript);
    }
}
