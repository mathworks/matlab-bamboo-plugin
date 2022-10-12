package com.mathworks.ci;

/**
 * Copyright 2021-2022 The MathWorks, Inc.
 */

public class MatlabVersionNotFoundException extends Exception {
    MatlabVersionNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    MatlabVersionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
