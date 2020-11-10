package com.mathworks.ci.helper;

/**
 * Copyright 2020 The MathWorks, Inc.
 */

public class MatlabBuilderConstants {

    // MATLAB executable
    public static final String MATLAB_PREFIX = "system.builder.matlab.";
    public static final String MATLAB_CFG_KEY = "matlabExecutable";

    // UIConfigBean populated in Configurators by Bamboo
    public static final String UI_CONFIG_SUPPORT = "uiConfigSupport";

    //MATLAB command field
    public static final String MATLAB_COMMAND_CFG_KEY = "matlabCommand";

    //MATLAB Test fields
    public static final String SOURCE_FOLDER = "srcfolder";
    public static final String JUNIT_FILE = "junit";
    public static final String HTML_FOLDER = "html";
    public static final String SRC_FLDR_CHX = "srcFolderChecked";
    public static final String HTML_CODECOV_CHX ="htmlCoverageChecked";
    public static final String JUNIT_RESULTS_CHX ="junitChecked";
    public static final String JUNIT_DEFAULT_FILE ="matlab-artifacts/test-reports/junit.xml";
    public static final String HTML_DEFAULT_DIR ="matlab-artifacts/code-coverage";

    // Matlab Runner files 
    public static final String BAT_RUNNER_FILE = "run_matlab_command.bat";
    public static final String SHELL_RUNNER_FILE = "run_matlab_command.sh";

    // MATLAB runner script
    public static final String TEST_RUNNER_SCRIPT = "testScript = genscript(${PARAMS});\n" + "\n"
            + "disp('Running MATLAB script with content:');\n"
            + "disp(strtrim(testScript.writeToText()));\n"
            + "fprintf('___________________________________\\n\\n');\n" + "run(testScript);\n" + "";
    String testOptions="";

    //Test runner file prefix 
    public static final String MATLAB_TEST_RUNNER_FILE_PREFIX = "test_runner_";

}
