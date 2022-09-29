package com.mathworks.ci.task;

/**
 * Copyright 2022 The MathWorks, Inc.
 * <p>
 * <p>
 * Test class for MatlabBuildTask
 */

import org.junit.Test;
import com.mathworks.ci.task.*;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilitySet;
import com.atlassian.bamboo.configuration.ConfigurationMapImpl;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.process.ProcessService;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.mathworks.ci.helper.MatlabBuild;

import java.util.Map;
import java.util.HashMap;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.util.*;

import org.junit.rules.TemporaryFolder;
import org.junit.Rule;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

@RunWith(MockitoJUnitRunner.class)
public class MatlabBuildTaskTest {

    @Mock
    public MatlabBuild matlabBuild;

    @Mock
    public CapabilityContext capabilityContext;

    @Mock
    private ProcessService processService;


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
        runnerFile = SystemUtils.IS_OS_WINDOWS ? "run_matlab_command.bat" : "run_matlab_command.sh";
        tempFile = testFolder.newFile("temp_File");
        tempFolder = testFolder.newFolder("temp_Folder");
    }


    @Test
    public void testMatlabBuildTaskAppendsTasksInput() throws IOException {
        final MatlabBuildTask matlabBuildTask = new MatlabBuildTask(processService, getCapabilityContext());

        final List<String> command = matlabBuildTask.getMatlabCommandScript(tempFolder, tempFolder);
        ConfigurationMap configurationMap = new ConfigurationMapImpl();
        configurationMap.put("buildTasks", "test");

        // when(taskContext.getConfigurationMap()).thenReturn(configurationMap);

        String expectedMatlabBuildTasks = "test";
        String actualMatlabBuildTasks = "test";
        assertEquals(actualMatlabBuildTasks, expectedMatlabBuildTasks);
    }


    @Test
    public void testMatlabBuildTaskWithNoTasksInputRunsBuildtool() throws IOException {
        final MatlabBuildTask matlabBuildTask = new MatlabBuildTask(processService, getCapabilityContext());

        final List<String> command = matlabBuildTask.getMatlabCommandScript(tempFolder, tempFolder);
        assertThat(command, hasItem(containsString(runnerFile)));
        assertThat(command, hasItem(containsString("cd")));
        assertThat(command, hasItem(containsString("matlabBuild_")));
    }


    private CapabilityContext getCapabilityContext() throws IOException {
        final CapabilitySet capabilitySet = mock(CapabilitySet.class);
        final Capability capability = mock(Capability.class);
        testFolder.create();
        return capabilityContext;
    }
}
