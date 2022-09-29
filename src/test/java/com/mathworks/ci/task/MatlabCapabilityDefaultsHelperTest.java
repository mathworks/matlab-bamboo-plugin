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
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.reflect.Field;

public class MatlabCapabilityDefaultsHelperTest {

    private CapabilitySetImpl capabilitySet;

    @Test
    public void testWhenMATLABNotOnPath() {
        MatlabCapabilityDefaultsHelper matlabCapabilityDefaultsHelper = new MatlabCapabilityDefaultsHelper();
        capabilitySet = new CapabilitySetImpl();
        CapabilitySet actualCapabilitySet = matlabCapabilityDefaultsHelper.addDefaultCapabilities(capabilitySet);
        assertEquals(actualCapabilitySet.getCapability(MatlabBuilderConstants.MATLAB_CAPABILITY_PREFIX), null);
    }


    @Test
    public void testWhenMATLABExistOnPath() {
        CapabilitySet actualCapabilitySet;
        String PWD = System.getProperty("user.dir");
        Path fakeMatlabExePath = Paths.get(PWD, "src", "test", "resources", "MATLAB", "FakeMATLABWithVersion", "bin", "matlab");
        Path fakeMatlabHomePath = Paths.get(PWD, "src", "test", "resources", "MATLAB", "FakeMATLABWithVersion");
        File fakeMatlabExe = new File(fakeMatlabExePath.toString());
        File fakeMatlabHome = new File(fakeMatlabHomePath.toString());

        try (MockedStatic<ExecutablePathUtils> utilities = Mockito.mockStatic(ExecutablePathUtils.class)) {
            utilities.when(() -> ExecutablePathUtils.detectExecutableOnPath(Mockito.any()))
                .thenReturn(fakeMatlabExe);
            utilities.when(() -> ExecutablePathUtils.getHomeFromExecutableInHomeBin(Mockito.any()))
                .thenReturn(fakeMatlabHome);
            MatlabCapabilityDefaultsHelper matlabCapabilityDefaultsHelper = new MatlabCapabilityDefaultsHelper();
            capabilitySet = new CapabilitySetImpl();
            actualCapabilitySet = matlabCapabilityDefaultsHelper.addDefaultCapabilities(capabilitySet);
        }
        assertEquals(actualCapabilitySet.getCapability(MatlabBuilderConstants.MATLAB_CAPABILITY_PREFIX + "R2019b").getValue(), fakeMatlabHomePath.toString());
    }
}
