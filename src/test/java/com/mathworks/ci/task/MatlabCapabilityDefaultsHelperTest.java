package com.mathworks.ci.task;

/**
 * Copyright 2021 The MathWorks, Inc.
 * <p>
 * <p>
 * Test class for MatlabCapabilityDefaultsHelper
 */

import com.mathworks.ci.MatlabCapabilityDefaultsHelper;
import com.atlassian.bamboo.v2.build.agent.capability.ExecutablePathUtils;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilitySet;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityImpl;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilitySetImpl;
import com.atlassian.bamboo.utils.SystemProperty;
import org.apache.commons.lang3.SystemUtils;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.mathworks.ci.MatlabReleaseInfo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class MatlabCapabilityDefaultsHelperTest {

    private String EXECUTABLE_NAME;
    private CapabilitySetImpl capabilitySet;

    @Before
    public void init() {
        String EXECUTABLE_NAME = SystemUtils.IS_OS_WINDOWS ? "matlab.exe" : "matlab";
    }

    @Test
    public void testWhenMATLABNotOnPath() {
        MatlabCapabilityDefaultsHelper matlabCapabilityDefaultsHelper = new MatlabCapabilityDefaultsHelper();
        capabilitySet = new CapabilitySetImpl();
        CapabilitySet actualCapabilitySet = matlabCapabilityDefaultsHelper.addDefaultCapabilities(capabilitySet);
        assertEquals(actualCapabilitySet.getCapability(MatlabBuilderConstants.MATLAB_CAPABILITY_PREFIX), null);
    }


    @Test
    public void testWhenMATLABExistOnPath() {
        MatlabCapabilityDefaultsHelper matlabCapabilityDefaultsHelper = new MatlabCapabilityDefaultsHelper();
        capabilitySet = new CapabilitySetImpl();
        String matlabRoot = "/local-ssd/ppandian/MATLAB_ISU/R2019b";
        SystemProperty.PATH.setValue("/local-ssd/ppandian/MATLAB_ISU/R2019b/bin/");
        CapabilitySet actualCapabilitySet = matlabCapabilityDefaultsHelper.addDefaultCapabilities(capabilitySet);
        assertEquals(actualCapabilitySet.getCapability(MatlabBuilderConstants.MATLAB_CAPABILITY_PREFIX + "R2019b").getValue(), matlabRoot);
    }
}

