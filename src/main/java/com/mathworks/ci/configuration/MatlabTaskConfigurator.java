package com.mathworks.ci.configuration;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.BuildTaskRequirementSupport;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.v2.build.agent.capability.Requirement;
import com.atlassian.bamboo.v2.build.agent.capability.RequirementImpl;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;
import com.atlassian.struts.TextProvider;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Sets;
import com.mathworks.ci.utils.CapabilityListHelper;
import java.util.Map;
import java.util.Set;


@Scanned
public class MatlabTaskConfigurator extends AbstractTaskConfigurator implements BuildTaskRequirementSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatlabTaskConfigurator.class);
    private static final String MATLAB_PREFIX = "system.builder.matlab";
    private static final String MATLAB_CFG_KEY = "matlabExecutable";
    private static final String UI_CONFIG_SUPPORT = "uiConfigSupport";

    @ComponentImport
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
        config.put(MATLAB_CFG_KEY, params.getString(MATLAB_CFG_KEY));
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
        context.put(MATLAB_CFG_KEY, taskDefinition.getConfiguration().get(MATLAB_CFG_KEY));
    }

    public void populateContextForAll(@NotNull final Map<String, Object> context) {
        context.put(UI_CONFIG_SUPPORT, uiConfigSupport);
    }


    //TODO:
    // Validate matlab executable path here
    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);
    }

    @NotNull
    @Override
    public Set<Requirement> calculateRequirements(@NotNull TaskDefinition taskDefinition, @NotNull Job job) {
        Set<Requirement> requirements = Sets.newHashSet();

        if (StringUtils.isNotBlank(MATLAB_PREFIX)) {
            final String matlabExecutableLabel = taskDefinition.getConfiguration().get(MATLAB_CFG_KEY);
            if (matlabExecutableLabel != null) {
                requirements.add(new RequirementImpl(MATLAB_PREFIX + "." + matlabExecutableLabel, true, ".*"));
            }
        }
        return requirements;
    }

} 

