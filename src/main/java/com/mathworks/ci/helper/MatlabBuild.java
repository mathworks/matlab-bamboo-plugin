/**
 * Copyright 2020 - 2023 The MathWorks, Inc.
 */

package com.mathworks.ci.helper;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.v2.build.agent.capability.ReadOnlyCapabilitySet;
import com.google.common.base.Strings;
import com.mathworks.ci.MatlabReleaseInfo;
import com.mathworks.ci.MatlabVersionNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MatlabBuild {
    @Nullable
    default String getMatlabRoot(@NotNull TaskContext taskContext, CapabilityContext capabilityContext, BuildLogger buildLogger) {
        String matlabRoot = "";
        String matlabRelease = null;
        ReadOnlyCapabilitySet capabilitySet = capabilityContext.getCapabilitySet();
        if (capabilitySet != null) {
            String matlabLabel = taskContext.getConfigurationMap().get(MatlabBuilderConstants.MATLAB_CFG_KEY);
            Capability capability = capabilitySet.getCapability(MatlabBuilderConstants.MATLAB_PREFIX + matlabLabel);
            if (capability != null) {
                matlabRoot = capability.getValue();
            }
        }
        try {
            MatlabReleaseInfo matlabReleaseInfo = new MatlabReleaseInfo(matlabRoot);
            matlabRelease = matlabReleaseInfo.getMatlabReleaseNumber();
        } catch (MatlabVersionNotFoundException exception) {
            buildLogger.addErrorLogEntry(exception.getMessage());
        }

        buildLogger.addBuildLogEntry("Running task on MATLAB release: " + matlabRelease);
        return matlabRoot + "/bin";
    }

    default File getTempWorkingDirectory() {
        File tmpDir = SystemUtils.getJavaIoTmpDir();
        File tempDirectory = new File(tmpDir, UUID.randomUUID().toString());
        tempDirectory.mkdirs();
        return tempDirectory;
    }

    default String getPlatformSpecificRunner(File tempDirectory) throws IOException {
        String sourceFile;
        String destinationFile;
        if (SystemUtils.IS_OS_WINDOWS) {
            sourceFile = "win64\\run-matlab-command.exe";
            destinationFile = tempDirectory + "\\" + "run-matlab-command.exe";
        } else if (SystemUtils.IS_OS_MAC) {
            sourceFile = "maci64/run-matlab-command";
            destinationFile = tempDirectory + "/" + "run-matlab-command";
        } else {
            sourceFile = "glnxa64/run-matlab-command";
            destinationFile = tempDirectory + "/" + "run-matlab-command";
        }
        copyFileInWorkspace(sourceFile, destinationFile);
        return destinationFile;
    }

    /*
     * Method to copy given file from source to target node specific workspace.
     */
    default void copyFileInWorkspace(String sourceFile, String destinationFile) throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File destination = new File(destinationFile);
        InputStream in = classLoader.getResourceAsStream(sourceFile);
        OutputStream outputStream = new FileOutputStream(destination);
        IOUtils.copy(in, outputStream);
        outputStream.flush();
        outputStream.close();
        // set executable permission
        destination.setReadable(true, true);
        destination.setWritable(true);
        destination.setExecutable(true, true);
    }

    default void clearTempDirectory(File workspace, BuildLogger buildLogger) {
        try {
            FileUtils.cleanDirectory(workspace);
            FileUtils.deleteDirectory(workspace);
        } catch (Exception e) {
            buildLogger.addErrorLogEntry(e.getMessage());
        }
    }
}
