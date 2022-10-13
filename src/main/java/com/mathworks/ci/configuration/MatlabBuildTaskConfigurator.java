/**
 * Copyright 2022 The MathWorks, Inc.
 */

package com.mathworks.ci.configuration;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.struts.TextProvider;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

@Scanned
public class MatlabBuildTaskConfigurator extends MatlabTaskConfigurator {
    private TextProvider textProvider;

    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        config.put(MatlabBuilderConstants.MATLAB_BUILD_TASKS, params.getString(MatlabBuilderConstants.MATLAB_BUILD_TASKS));
        return config;
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        context.put(MatlabBuilderConstants.MATLAB_BUILD_TASKS, taskDefinition.getConfiguration().get(MatlabBuilderConstants.MATLAB_BUILD_TASKS));
    }
}
