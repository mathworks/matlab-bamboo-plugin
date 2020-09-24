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
import com.atlassian.struts.TextProvider;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Sets;
import com.mathworks.ci.utils.CapabilityListHelper;
import java.util.Map;
import java.util.Set;



public class MatlabTaskConfigurator extends AbstractTaskConfigurator implements BuildTaskRequirementSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatlabTaskConfigurator.class);
    private static final String MATLAB_PREFIX = "system.builder.matlab";
    private static final String MATLAB_CFG_KEY = "matlabExecutable";
    private static final String MATLAB_LIST_CFG_KEY = "matlabExecutableList";
    private static final String MATLABROOT_CFG_KEY = "matlabRoot";
    public CapabilityListHelper capabilityListHelper;


    public MatlabTaskConfigurator(CapabilityContext capabilityContext) {
        this.capabilityListHelper = new CapabilityListHelper(capabilityContext);
    }

    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, final TaskDefinition previousTaskDefinition) {
        this.capabilityListHelper.populateMatlabExecutables();
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        config.put(MATLABROOT_CFG_KEY, this.capabilityListHelper.getMatlabLabelToCapability().get(params.getString(MATLAB_CFG_KEY)).getKey());
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
        this.capabilityListHelper.populateMatlabExecutables();
        context.put(MATLAB_LIST_CFG_KEY, capabilityListHelper.getMatlabExecutablesList());
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

