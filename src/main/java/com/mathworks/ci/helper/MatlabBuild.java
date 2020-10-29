package com.mathworks.ci.helper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.v2.build.agent.capability.ReadOnlyCapabilitySet;
import com.google.common.collect.Maps;
import com.google.common.base.Strings;
import org.apache.commons.lang3.SystemUtils;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import java.util.*;
import java.io.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;

public interface MatlabBuild {


    @Nullable
    default String getMatlabRoot(@NotNull TaskContext taskContext, CapabilityContext capabilityContext) {
        String matlabRoot = null;
        ReadOnlyCapabilitySet capabilitySet = capabilityContext.getCapabilitySet();
        if (capabilitySet != null) {
            String matlabLabel = taskContext.getConfigurationMap().get(MatlabBuilderConstants.MATLAB_CFG_KEY);
            Capability capability = capabilitySet.getCapability(MatlabBuilderConstants.MATLAB_PREFIX + matlabLabel);
            if (capability != null) {
                matlabRoot = Strings.emptyToNull(capability.getValue());
            }
        }
        return matlabRoot;
    }


    default File getTempWorkingDirectory() {
        File tmpDir = SystemUtils.getJavaIoTmpDir();
        File tempDirectory = new File(tmpDir, getUniqueNameForRunnerFile());
        tempDirectory.mkdirs();
        return tempDirectory;
    }

    default String getUniqueNameForRunnerFile() {
        return UUID.randomUUID().toString();
    }

    default String getPlatformSpecificRunner(File tempDirectory) throws IOException {

        if (SystemUtils.IS_OS_WINDOWS) {
            copyFileInWorkspace(MatlabBuilderConstants.BAT_RUNNER_FILE, tempDirectory);
            return tempDirectory +"\\" + "run_matlab_command.bat";
        } else {
            copyFileInWorkspace(MatlabBuilderConstants.SHELL_RUNNER_FILE, tempDirectory);
            return tempDirectory + "/" + "run_matlab_command.sh";
        }
    }

    /*
     * Method to copy given file from source to target node specific workspace.
     */
    default void copyFileInWorkspace(String sourceFile, File targetWorkspace)
    throws IOException {

        final ClassLoader classLoader = getClass().getClassLoader();
        final File destination = new File(targetWorkspace, sourceFile);
        InputStream in = classLoader.getResourceAsStream(sourceFile);
        OutputStream outputStream = new FileOutputStream(destination);
        IOUtils.copy( in , outputStream);
        outputStream.flush();
        outputStream.close();
        // set executable permission
        destination.setReadable(true, true);
        destination.setWritable(true);
        destination.setExecutable(true, true);
    }

    default void clearWorkingDirectory(File workspace) throws IOException {
        FileUtils.cleanDirectory(workspace);
        FileUtils.deleteDirectory(workspace);
    }


}
