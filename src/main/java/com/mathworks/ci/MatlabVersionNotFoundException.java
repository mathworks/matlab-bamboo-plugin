package com.mathworks.ci;

/*
 * Copyright 2021 The MathWorks, Inc. This Exception class provides a business exception for all
 * Classes/methods which tries to get version information of MATLAB.
 * 
 */

public class MatlabVersionNotFoundException extends Exception {
    MatlabVersionNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
