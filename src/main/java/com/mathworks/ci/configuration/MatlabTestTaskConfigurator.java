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
import com.mathworks.ci.helper.MatlabBuilderConstants;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class MatlabTestTaskConfigurator extends MatlabTaskConfigurator {
    
    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        config.put(MatlabBuilderConstants.SRC_FLDR_CHX,String.valueOf(params.getBoolean(MatlabBuilderConstants.SRC_FLDR_CHX)));
        config.put(MatlabBuilderConstants.HTML_CODECOV_CHX,String.valueOf(params.getBoolean(MatlabBuilderConstants.HTML_CODECOV_CHX)));
        config.put(MatlabBuilderConstants.JUNIT_RESULTS_CHX,String.valueOf(params.getBoolean(MatlabBuilderConstants.JUNIT_RESULTS_CHX)));
        config.put(MatlabBuilderConstants.SOURCE_FOLDER, params.getString(MatlabBuilderConstants.SOURCE_FOLDER));
        config.put(MatlabBuilderConstants.JUNIT_FILE, params.getString(MatlabBuilderConstants.JUNIT_FILE));
        config.put(MatlabBuilderConstants.HTML_FOLDER, params.getString(MatlabBuilderConstants.HTML_FOLDER));
        return config;
    }


    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
        context.put(MatlabBuilderConstants.JUNIT_FILE, MatlabBuilderConstants.JUNIT_DEFAULT_FILE);
        context.put(MatlabBuilderConstants.HTML_FOLDER, MatlabBuilderConstants.HTML_DEFAULT_DIR);


    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        context.put(MatlabBuilderConstants.SOURCE_FOLDER, taskDefinition.getConfiguration().get(MatlabBuilderConstants.SOURCE_FOLDER));
        context.put(MatlabBuilderConstants.JUNIT_FILE, taskDefinition.getConfiguration().get(MatlabBuilderConstants.JUNIT_FILE));
        context.put(MatlabBuilderConstants.HTML_FOLDER, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_FOLDER));
        context.put(MatlabBuilderConstants.JUNIT_RESULTS_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.JUNIT_RESULTS_CHX));
        context.put(MatlabBuilderConstants.HTML_CODECOV_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_CODECOV_CHX));
        context.put(MatlabBuilderConstants.SRC_FLDR_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.SRC_FLDR_CHX));

    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);
        if(params.getBoolean(MatlabBuilderConstants.SRC_FLDR_CHX)&&(StringUtils.isBlank(params.getString(MatlabBuilderConstants.SOURCE_FOLDER)))){
           errorCollection.addError(MatlabBuilderConstants.SOURCE_FOLDER, "Please specify Source folder"); 
        }
        if(params.getBoolean(MatlabBuilderConstants.JUNIT_RESULTS_CHX)&&(StringUtils.isBlank(params.getString(MatlabBuilderConstants.JUNIT_FILE)))){
           errorCollection.addError(MatlabBuilderConstants.JUNIT_FILE, "Please specify JUnit results path"); 
        }
        if(params.getBoolean(MatlabBuilderConstants.HTML_CODECOV_CHX)&&(StringUtils.isBlank(params.getString(MatlabBuilderConstants.HTML_FOLDER)))){
           errorCollection.addError(MatlabBuilderConstants.HTML_FOLDER, "Please specify a directory for code coverage"); 
        }

    }


}
