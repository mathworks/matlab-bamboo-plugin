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
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import com.atlassian.struts.TextProvider;
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
import java.util.Map;

@Scanned
public class MatlabCommandTaskConfigurator extends MatlabTaskConfigurator {
    private TextProvider textProvider;


    @NotNull
    @Override
    public Map < String, String > generateTaskConfigMap(@NotNull final ActionParametersMap params, final TaskDefinition previousTaskDefinition) {
        final Map < String, String > config = super.generateTaskConfigMap(params, previousTaskDefinition);
        config.put(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY, params.getString(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY));
        return config;
    }


    @Override
    public void populateContextForCreate(@NotNull final Map < String, Object > context) {
        super.populateContextForCreate(context);
    }

    @Override
    public void populateContextForEdit(@NotNull final Map < String, Object > context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        context.put(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY, taskDefinition.getConfiguration().get(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY));
    }


    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);
        if (StringUtils.isBlank(params.getString(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY))) {
            errorCollection.addError(MatlabBuilderConstants.MATLAB_COMMAND_CFG_KEY, "Specify a valid MATLAB command.");
        }
    }

}
