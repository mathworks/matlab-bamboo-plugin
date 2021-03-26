package com.mathworks.ci.task;

/**
 * 
 * Copyright 2020-2021 The MathWorks, Inc.
 *
 * 
 * Test class for MatlabTestTask
 * 
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
import org.mockito.MockitoAnnotations.Mock;
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
public class MatlabTaskTest { 

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
    public void testMatlabtest()  throws IOException {
        final MatlabTestTask matlabTestTask = new MatlabTestTask(processService,getCapabilityContext());

         when(matlabBuild.getTempWorkingDirectory()).thenReturn(tempFile);
         when(matlabBuild.getPlatformSpecificRunner(tempFolder)).thenReturn(runnerFile);
         final List<String> command = matlabTestTask.getMatlabCommandScript(tempFolder,tempFolder);
         assertThat(command, hasItem(containsString(runnerFile)));
         assertThat(command, hasItem(containsString("addpath")));
         assertThat(command, hasItem(containsString("test_runner_")));

         ConfigurationMap configurationMap = new ConfigurationMapImpl();
         configurationMap.put("junitChecked","true");
         configurationMap.put("pdfChecked","true");
         configurationMap.put("htmlCoverageChecked","true");
         configurationMap.put("stmChecked","true");
         configurationMap.put("srcFolderChecked","true");
         configurationMap.put("byFolderChecked","true");
         configurationMap.put("byTagChecked","true");
         configurationMap.put("junit","junit.xml");
         configurationMap.put("pdf","report.pdf");
         configurationMap.put("html","code-coverage");
         configurationMap.put("stm","results.mldatx");
         configurationMap.put("srcfolder","src/:src1");
         configurationMap.put("testFolders","test/");
         configurationMap.put("testTag","all");

         when(taskContext.getConfigurationMap()).thenReturn(configurationMap);

         String expectedMatlabTestOptions = "'Test','JUnitTestResults','junit.xml','PDFTestReport','report.pdf',"
                                   + "'HTMLCodeCoverage','code-coverage','SimulinkTestResults',"
                                   + "'results.mldatx','SourceFolder','src/:src1','SelectByFolder',"
                                   + "'test/','SelectByTag','all'";

         String actualMatlabTestOptions = matlabTestTask.getInputArguments(taskContext);
         assertEquals(actualMatlabTestOptions, expectedMatlabTestOptions);
    
    }


    @Test
    public void testMatlabCommandTask()  throws IOException {
        final MatlabCommandTask matlabCommandTask = new MatlabCommandTask(processService,getCapabilityContext());

         when(matlabBuild.getTempWorkingDirectory()).thenReturn(tempFile);
         when(matlabBuild.getPlatformSpecificRunner(tempFolder)).thenReturn(runnerFile);
         final List<String> command = matlabCommandTask.getMatlabCommandScript(tempFolder,tempFolder);
         assertThat(command, hasItem(containsString(runnerFile)));
         assertThat(command, hasItem(containsString("cd")));
         assertThat(command, hasItem(containsString("command_")));
    }


    private CapabilityContext getCapabilityContext() throws IOException
    {
        final CapabilitySet capabilitySet = mock(CapabilitySet.class);
        final Capability capability = mock(Capability.class);
        testFolder.create();
        when(capability.getValue()).thenReturn(testFolder.getRoot().getAbsolutePath());
        when(capabilitySet.getCapability(anyString())).thenReturn(capability);
        when(capabilityContext.getCapabilitySet()).thenReturn(capabilitySet);
        return capabilityContext;
    }
}
