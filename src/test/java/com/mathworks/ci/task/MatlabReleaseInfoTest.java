package com.mathworks.ci.task;

/**
 * Copyright 2021 The MathWorks, Inc.
 * <p>
 * <p>
 * Test class for MatlabReleaseInfo
 */


import com.mathworks.ci.MatlabReleaseInfo;
import com.mathworks.ci.MatlabVersionNotFoundException;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class MatlabReleaseInfoTest {

    @Test
    public void testGetMatlabReleaseInfoWithContentsFile() throws MatlabVersionNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File matlabRoot = new File(classLoader.getResource("MATLAB/FakeMATLABWithNoVersion").getFile());
        MatlabReleaseInfo matlabReleaseInfo = new MatlabReleaseInfo(matlabRoot.toString());
        assertEquals(matlabReleaseInfo.getMatlabReleaseNumber(), "R2019b");
    }

    @Test
    public void testGetMatlabReleaseInfoWithVersionFile() throws MatlabVersionNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File matlabRoot = new File(classLoader.getResource("MATLAB/FakeMATLABWithVersion").getFile());
        MatlabReleaseInfo matlabReleaseInfo = new MatlabReleaseInfo(matlabRoot.toString());
        assertEquals(matlabReleaseInfo.getMatlabReleaseNumber(), "R2019b");
    }

    @Test(expected = MatlabVersionNotFoundException.class)
    public void testGetMatlabReleaseInfoException() throws MatlabVersionNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File matlabRoot = new File(classLoader.getResource("MATLAB/Fake_MATLAB").getFile());
        MatlabReleaseInfo matlabReleaseInfo = new MatlabReleaseInfo(matlabRoot.toString());
        matlabReleaseInfo.getMatlabReleaseNumber();
    }
}