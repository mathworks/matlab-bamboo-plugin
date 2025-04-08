/**
 * Copyright 2022-2024 The MathWorks, Inc.
 */

package com.mathworks.ci.task.test;

import com.mathworks.ci.task.MatlabTestTask;
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
public class MatlabTestTaskTest {
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

    public ExternalProcess process;

    @InjectMocks
    MatlabTestTask task;

    @Before
    public void init() {
        when(taskContext.getBuildLogger()).thenReturn(buildLogger);
        ConfigurationMap configurationMap = new ConfigurationMapImpl();
        configurationMap.put("junitChecked", "false");
        configurationMap.put("htmlTestResultsChecked", "false");
        configurationMap.put("pdfChecked", "false");
        configurationMap.put("htmlCoverageChecked", "false");
        configurationMap.put("stmChecked", "false");
        configurationMap.put("srcFolderChecked", "false");
        configurationMap.put("byFolderChecked", "false");
        configurationMap.put("byTagChecked", "false");
        configurationMap.put("strictChecked", "false");
        configurationMap.put("useParallelChecked", "false");
        configurationMap.put("outputDetail", "Default");
        configurationMap.put("loggingLevel", "Default");
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);
        when(matlabCommandRunner.getTempDirectory()).thenReturn(new File("/path/to/.matlab"));
    }

    @Test
    public void testExectuteRunsWithDefaultArguments() throws TaskException, IOException {
        try (MockedStatic<TaskResultBuilder> taskResultBuilder = Mockito.mockStatic(TaskResultBuilder.class)) {
            taskResultBuilder.when(() -> TaskResultBuilder.newBuilder(Mockito.any()))
                .thenReturn(resultBuilder);
            task.execute(taskContext);
        }
        ArgumentCaptor<String> matlabCommand = ArgumentCaptor.forClass(String.class);
        Mockito.verify(matlabCommandRunner).run(matlabCommand.capture(), Mockito.any());

        String expectedCommand = "addpath('/path/to/.matlab');\n" 
            + "testScript = genscript('Test');\n"
            + "disp('Running MATLAB script with contents:');\n"
            + "disp(testScript.Contents);\n"
            + "fprintf('___________________________________\\n\\n');\n" + "run(testScript);\n" + "";
        assertEquals(expectedCommand, matlabCommand.getValue());
    }

    @Test
    public void testExectuteRunsWithProvidedArguments() throws TaskException, IOException {
        ConfigurationMap configurationMap = new ConfigurationMapImpl();
        configurationMap.put("junitChecked", "true");
        configurationMap.put("htmlTestResultsChecked", "true");
        configurationMap.put("pdfChecked", "true");
        configurationMap.put("htmlCoverageChecked", "true");
        configurationMap.put("stmChecked", "true");
        configurationMap.put("htmlModelCoverageChecked", "true");
        configurationMap.put("srcFolderChecked", "true");
        configurationMap.put("byFolderChecked", "true");
        configurationMap.put("byTagChecked", "true");
        configurationMap.put("strictChecked", "true");
        configurationMap.put("useParallelChecked", "true");
        configurationMap.put("junit", "junit.xml");
        configurationMap.put("htmlTestResults", "test-reports");
        configurationMap.put("pdf", "report.pdf");
        configurationMap.put("html", "code-coverage");
        configurationMap.put("htmlModel", "model-coverage");
        configurationMap.put("stm", "results.mldatx");
        configurationMap.put("srcfolder", "src/:src1");
        configurationMap.put("testFolders", "test/");
        configurationMap.put("testTag", "all");
        configurationMap.put("outputDetail", "Detailed");
        configurationMap.put("loggingLevel", "Detailed");
        when(taskContext.getConfigurationMap()).thenReturn(configurationMap);

        try (MockedStatic<TaskResultBuilder> taskResultBuilder = Mockito.mockStatic(TaskResultBuilder.class)) {
            taskResultBuilder.when(() -> TaskResultBuilder.newBuilder(Mockito.any()))
                .thenReturn(resultBuilder);
            task.execute(taskContext);
        }
        ArgumentCaptor<String> matlabCommand = ArgumentCaptor.forClass(String.class);
        Mockito.verify(matlabCommandRunner).run(matlabCommand.capture(), Mockito.any());

        String expectedCommand = "addpath('/path/to/.matlab');\n"
            + "testScript = genscript("
            + "'Test','JUnitTestResults','junit.xml',"
            + "'HTMLTestReport','test-reports',"
            + "'PDFTestReport','report.pdf',"
            + "'HTMLCodeCoverage','code-coverage',"
            + "'SimulinkTestResults','results.mldatx',"
            + "'HTMLModelCoverage','model-coverage',"
            + "'SourceFolder','src/:src1',"
            + "'SelectByFolder','test/',"
            + "'SelectByTag','all',"
            + "'Strict',true,"
            + "'UseParallel',true,"
            + "'OutputDetail','Detailed',"
            + "'LoggingLevel','Detailed'"
            + ");\n"
            + "disp('Running MATLAB script with contents:');\n"
            + "disp(testScript.Contents);\n"
            + "fprintf('___________________________________\\n\\n');\n" + "run(testScript);\n" + "";
        assertEquals(expectedCommand, matlabCommand.getValue());
    }

    @Test
    public void testExecuteExceptionsAreAddedToBuildlog() throws TaskException, IOException {
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

    @Test
    public void testExectuteUnzipsScriptgenToTempDir() throws TaskException, IOException {
        try (MockedStatic<TaskResultBuilder> taskResultBuilder = Mockito.mockStatic(TaskResultBuilder.class)) {
            taskResultBuilder.when(() -> TaskResultBuilder.newBuilder(Mockito.any()))
                .thenReturn(resultBuilder);
            task.execute(taskContext);
        }
        ArgumentCaptor<String> zipName = ArgumentCaptor.forClass(String.class);
        Mockito.verify(matlabCommandRunner).unzipToTempDir(zipName.capture());

        assertEquals("matlab-script-generator.zip", zipName.getValue());
    }
}
