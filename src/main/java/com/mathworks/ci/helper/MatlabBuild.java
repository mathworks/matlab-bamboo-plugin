package com.mathworks.ci.helper;

/**
 * Copyright 2020 The MathWorks, Inc.
 */

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
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

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
        return matlabRoot+File.separator+"bin";
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


    // This method prepares the temp folder by coping all helper files in it.
    default void prepareTmpFldr(File tmpFldr, String runnerScript) throws IOException{
        // Write MATLAB scratch file in temp folder.
        File scriptFile = new File(tmpFldr, (getValidMatlabFileName(FilenameUtils.getBaseName(tmpFldr.toString())) + ".m"));
        FileUtils.writeStringToFile(scriptFile,runnerScript,"UTF-8");

        // copy genscript package
        copyFileInWorkspace("matlab-script-generator.zip",tmpFldr);
        File zipFileLocation = new File(tmpFldr, "matlab-script-generator.zip");

        // Unzip the file in temp folder.
        ZipFile zipFile = new ZipFile(zipFileLocation);
        zipFile.extractAll(tmpFldr.toString());
    }


    default void clearTempDirectory(File workspace) throws IOException {
        FileUtils.cleanDirectory(workspace);
        FileUtils.deleteDirectory(workspace);
    }

     default String getRunnerScript(String script, String params) {
        script = script.replace("${PARAMS}", params);
        return script;
    }

     default String getValidMatlabFileName(String actualName) {
        return MatlabBuilderConstants.MATLAB_TEST_RUNNER_FILE_PREFIX
                + actualName.replaceAll("-", "_");
    }


}
