/**
 * Copyright 2021-2022 The MathWorks, Inc.
 */

package com.mathworks.ci.task.test;

import org.junit.Test;
import com.mathworks.ci.MatlabReleaseInfo;
import com.mathworks.ci.MatlabVersionNotFoundException;
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
