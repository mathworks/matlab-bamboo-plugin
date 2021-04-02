package com.mathworks.ci;

import com.atlassian.bamboo.v2.build.agent.capability.CapabilityDefaultsHelper;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilitySet;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityImpl;
import com.atlassian.bamboo.v2.build.agent.capability.ExecutablePathUtils;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import java.io.File;
import org.jetbrains.annotations.NotNull;
import com.mathworks.ci.helper.MatlabBuilderConstants;

/**
 * Copyright 2021 The MathWorks, Inc.
 */

@Scanned
public class MatlabCapabilityDefaultsHelper implements CapabilityDefaultsHelper {

    private static final String EXECUTABLE_NAME =  SystemUtils.IS_OS_WINDOWS ? "matlab.exe" : "matlab";
    File home;

    @Override
    @NotNull
    public CapabilitySet addDefaultCapabilities(@NotNull final CapabilitySet capabilitySet)
    {
        final File executablePath = ExecutablePathUtils.detectExecutableOnPath("matlab");
        if(executablePath.exists() && executablePath.isFile())
            {
                 home = ExecutablePathUtils.getHomeFromExecutableInHomeBin(executablePath);
            }
        MatlabReleaseInfo matlabReleaseInfo = new MatlabReleaseInfo(home.toString());
        try 
        {
            String matlabRelease = matlabReleaseInfo.getMatlabReleaseNumber();
            Capability capability = new CapabilityImpl(MatlabBuilderConstants.MATLAB_CAPABILITY_PREFIX + matlabRelease, home.toString());
            capabilitySet.addCapability(capability);
        } 
        catch (MatlabVersionNotFoundException e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
        return capabilitySet;
    }


}
