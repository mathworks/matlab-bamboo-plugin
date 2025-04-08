/**
 * Copyright 2021-2022 The MathWorks, Inc.
 */

package com.mathworks.ci;

import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityDefaultsHelper;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityImpl;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilitySet;
import com.atlassian.bamboo.v2.build.agent.capability.ExecutablePathUtils;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import java.io.File;
import java.util.Objects;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;

public class MatlabCapabilityDefaultsHelper implements CapabilityDefaultsHelper {
    private static final String EXECUTABLE_NAME = SystemUtils.IS_OS_WINDOWS ? "matlab.exe" : "matlab";

    @Override
    @NotNull
    public final CapabilitySet addDefaultCapabilities(@NotNull final CapabilitySet capabilitySet) {
        File executablePath = ExecutablePathUtils.detectExecutableOnPath(EXECUTABLE_NAME);
        if (Objects.isNull(executablePath) || !(executablePath.exists())) {
            return capabilitySet;
        }

        File matlabroot = ExecutablePathUtils.getHomeFromExecutableInHomeBin(executablePath);
        MatlabReleaseInfo matlabReleaseInfo = new MatlabReleaseInfo(matlabroot.toString());

        try {
            String matlabRelease = matlabReleaseInfo.getMatlabReleaseNumber();
            Capability capability = new CapabilityImpl(MatlabBuilderConstants.MATLAB_CAPABILITY_PREFIX + matlabRelease, matlabroot.toString());
            capabilitySet.addCapability(capability);
        } catch (MatlabVersionNotFoundException e) {
            e.printStackTrace();
        }

        return capabilitySet;
    }
}
