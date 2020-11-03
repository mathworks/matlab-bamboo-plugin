package com.mathworks.ci.configuration;

/**
 * Copyright 2020 The MathWorks, Inc.
 */

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.struts.TextProvider;
import com.atlassian.util.concurrent.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


//TODO : create a separate class to handle constants
//TODO: Error handling pending
public class MatlabTestTaskConfigurator extends MatlabTaskConfigurator {
    private static final String SOURCE_FOLDER = "srcfolder";
    private static final String JUNIT_FILE = "junit";
    private static final String HTML_FOLDER = "html";
    private static final String SRC_FLDR_CHX = "srcFolderChecked";
    private static final String HTML_CODECOV_CHX ="htmlCoverageChecked";
    private static final String JUNIT_RESULTS_CHX ="junitChecked";
    private static final String JUNIT_DEFAULT_FILE ="matlab-artifacts/test-reports/junit.xml";
    private static final String HTML_DEFAULT_DIR ="matlab-artifacts/code-coverage";

    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        config.put(SRC_FLDR_CHX,String.valueOf(params.getBoolean(SRC_FLDR_CHX)));
        config.put(HTML_CODECOV_CHX,String.valueOf(params.getBoolean(HTML_CODECOV_CHX)));
        config.put(JUNIT_RESULTS_CHX,String.valueOf(params.getBoolean(JUNIT_RESULTS_CHX)));
        config.put(SOURCE_FOLDER, params.getString(SOURCE_FOLDER));
        config.put(JUNIT_FILE, params.getString(JUNIT_FILE));
        config.put(HTML_FOLDER, params.getString(HTML_FOLDER));
        return config;
    }


    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
        context.put(JUNIT_FILE, JUNIT_DEFAULT_FILE);
        context.put(HTML_FOLDER, HTML_DEFAULT_DIR);


    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        context.put(SOURCE_FOLDER, taskDefinition.getConfiguration().get(SOURCE_FOLDER));
        context.put(JUNIT_FILE, taskDefinition.getConfiguration().get(JUNIT_FILE));
        context.put(HTML_FOLDER, taskDefinition.getConfiguration().get(HTML_FOLDER));
        context.put(JUNIT_RESULTS_CHX, taskDefinition.getConfiguration().get(JUNIT_RESULTS_CHX));
        context.put(HTML_CODECOV_CHX, taskDefinition.getConfiguration().get(HTML_CODECOV_CHX));
        context.put(SRC_FLDR_CHX, taskDefinition.getConfiguration().get(SRC_FLDR_CHX));

    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);

    }


}
