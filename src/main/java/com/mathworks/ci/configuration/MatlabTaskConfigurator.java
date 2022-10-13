/**
 * Copyright 2020-2022 The MathWorks, Inc.
 */

package com.mathworks.ci.configuration;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.BuildTaskRequirementSupport;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.agent.capability.Requirement;
import com.atlassian.bamboo.v2.build.agent.capability.RequirementImpl;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.BambooImport;
import com.google.common.collect.Sets;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

@Scanned
public class MatlabTaskConfigurator extends AbstractTaskConfigurator implements BuildTaskRequirementSupport {
    @BambooImport("uiConfigBean")
    @Inject
    private UIConfigSupport uiConfigSupport;

    /*
     * Called automatically by Bamboo
     */
    @SuppressWarnings("unused")
    @Autowired
    public void setUiConfigSupport(UIConfigSupport uiConfigSupport) {
        this.uiConfigSupport = uiConfigSupport;
    }

    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        config.put(MatlabBuilderConstants.MATLAB_CFG_KEY, params.getString(MatlabBuilderConstants.MATLAB_CFG_KEY));
        return config;
    }

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
        populateContextForAll(context);
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        populateContextForAll(context);
        context.put(MatlabBuilderConstants.MATLAB_CFG_KEY, taskDefinition.getConfiguration().get(MatlabBuilderConstants.MATLAB_CFG_KEY));
    }

    public void populateContextForAll(@NotNull final Map<String, Object> context) {
        context.put(MatlabBuilderConstants.UI_CONFIG_SUPPORT, uiConfigSupport);
    }

    // Validating path is tedious factoring different platforms and remote agent
    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);
        if (StringUtils.isBlank(params.getString(MatlabBuilderConstants.MATLAB_CFG_KEY))) {
            errorCollection.addError(MatlabBuilderConstants.MATLAB_CFG_KEY, "Specify a MATLAB executable.");
        }
    }

    @NotNull
    @Override
    public Set<Requirement> calculateRequirements(@NotNull TaskDefinition taskDefinition, @NotNull Job job) {
        Set<Requirement> requirements = Sets.newHashSet();

        if (StringUtils.isNotBlank(MatlabBuilderConstants.MATLAB_PREFIX)) {
            final String matlabExecutableLabel = taskDefinition.getConfiguration().get(MatlabBuilderConstants.MATLAB_CFG_KEY);
            if (matlabExecutableLabel != null) {
                requirements.add(new RequirementImpl(MatlabBuilderConstants.MATLAB_PREFIX + matlabExecutableLabel, true, ".*"));
            }
        }
        return requirements;
    }
}
