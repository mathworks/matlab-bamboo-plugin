package com.mathworks.ci.helper;

public class MatlabBuilderConstants {

    // MATLAB executable
    public static final String MATLAB_PREFIX = "system.builder.matlab.";
    public static final String MATLAB_CFG_KEY = "matlabExecutable";

    // UIConfigBean populated in Configurators by Bamboo
    public static final String UI_CONFIG_SUPPORT = "uiConfigSupport";

    //MATLAB command field
    public static final String MATLAB_COMMAND_CFG_KEY = "matlabCommand";

    // Matlab Runner files 
    public static final String BAT_RUNNER_FILE = "run_matlab_command.bat";
    public static final String SHELL_RUNNER_FILE = "run_matlab_command.sh";

}
