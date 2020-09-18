package com.mathworks.ci.configuration;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
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


public class MATLABCommandTaskConfigurator extends MATLABTaskConfigurator {
    private TextProvider textProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger(MATLABCommandTaskConfigurator.class);
    
    @ComponentImport
    private CapabilityContext capabilityContext;

    public MATLABCommandTaskConfigurator(){}

    
    public MATLABCommandTaskConfigurator(CapabilityContext capabilityContext)
    {
        super(capabilityContext);
        this.capabilityContext=capabilityContext;

    }

    @NotNull
    @Override
    // Function called by Bamboo on save
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params,  final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        // create map with values captured from Task interface
        return config;
    }


    @Override
    //Function called by Bamboo to create Task
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
    }

    @Override
    //Function called on edit
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);

    }


    @Override
    //validate command field values here
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);

       
    }

}
