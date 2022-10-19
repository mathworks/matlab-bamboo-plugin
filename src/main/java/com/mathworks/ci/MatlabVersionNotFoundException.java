/**
 * Copyright 2021-2022 The MathWorks, Inc.
 * 
 * This Exception class provides a exception for all Classes/methods which
 * tries to get version information of MATLAB.
 */

package com.mathworks.ci;

public class MatlabVersionNotFoundException extends Exception {
    MatlabVersionNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    MatlabVersionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
