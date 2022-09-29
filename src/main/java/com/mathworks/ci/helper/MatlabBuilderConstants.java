package com.mathworks.ci.helper;

/**
 * Copyright 2020-2021 The MathWorks, Inc.
 */

public class MatlabBuilderConstants {

    // MATLAB executable
    public static final String MATLAB_PREFIX = "system.builder.matlab.";
    public static final String MATLAB_CAPABILITY_PREFIX = "system.builder.matlab.MATLAB ";
    public static final String MATLAB_CFG_KEY = "matlabExecutable";

    // UIConfigBean populated in Configurators by Bamboo
    public static final String UI_CONFIG_SUPPORT = "uiConfigSupport";

    // MATLAB build field
    public static final String MATLAB_BUILD_TASKS = "buildTasks";

    //MATLAB command field
    public static final String MATLAB_COMMAND_CFG_KEY = "matlabCommand";

    //MATLAB Test fields

    //Source folder options
    public static final String SOURCE_FOLDER = "srcfolder";
    public static final String SRC_FLDR_CHX = "srcFolderChecked";

    //Test Selection options
    public static final String FILTER_BY_FOLDER_CHX ="byFolderChecked";
    public static final String TEST_FOLDERS ="testFolders";
    public static final String FILTER_BY_TAG_CHX ="byTagChecked";
    public static final String TEST_TAG ="testTag";
    
    //Test artifacts
    public static final String JUNIT_FILE = "junit";
    public static final String JUNIT_RESULTS_CHX ="junitChecked";
    public static final String JUNIT_DEFAULT_FILE ="matlab-artifacts/test-reports/junit.xml";

    public static final String PDF_FILE = "pdf";
    public static final String PDF_RESULTS_CHX ="pdfChecked";
    public static final String PDF_DEFAULT_FILE ="matlab-artifacts/test-reports/report.pdf";

    public static final String STM_FILE ="stm";
    public static final String STM_RESULTS_CHX ="stmChecked";
    public static final String STM_DEFAULT_FILE ="matlab-artifacts/test-reports/results.mldatx";

    //Code coverge artifacts
    public static final String HTML_COVFOLDER = "html";
    public static final String HTML_CODECOV_CHX ="htmlCoverageChecked";
    public static final String HTML_CODECOV_DEFAULT_DIR ="matlab-artifacts/code-coverage";

    public static final String HTML_MODELCOV_FOLDER = "htmlModel";
    public static final String HTML_MODELCOV_CHX ="htmlModelCoverageChecked";
    public static final String HTML_MODELCOV_DEFAULT_DIR ="matlab-artifacts/model-coverage";


    // Matlab Runner files å
    public static final String BAT_RUNNER_FILE = "run_matlab_command.bat";
    public static final String SHELL_RUNNER_FILE = "run_matlab_command.sh";

    // MATLAB runner script
    public static final String TEST_RUNNER_SCRIPT = "testScript = genscript(${PARAMS});\n" + "\n"
            + "disp('Running MATLAB script with contents:');\n"
            + "disp(testScript.Contents);\n"
            + "fprintf('___________________________________\\n\\n');\n" + "run(testScript);\n" + "";
    String testOptions="";

    //Test runner file prefix 
    public static final String MATLAB_TEST_RUNNER_FILE_PREFIX = "test_runner_";

}
