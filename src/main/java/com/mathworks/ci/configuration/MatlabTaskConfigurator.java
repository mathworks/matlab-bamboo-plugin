package com.mathworks.ci.configuration;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigSupport;
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

//TODO:
// Implement interface for adding requirements
public class MatlabTaskConfigurator extends AbstractTaskConfigurator {
    //private static final String UI_CONFIG_SUPPORT = "uiConfigSupport";
    private static final Logger LOGGER = LoggerFactory.getLogger(MATLABTaskConfigurator.class);
    private UIConfigSupport uiConfigSupport;
    private List<String> matlabExecutableList;

    @ComponentImport
    protected CapabilityContext capabilityContext;  //replacement Object for UIConfigSupport
    public String matlabRoot;


    public MatlabTaskConfigurator(CapabilityContext capabilityContext)
    {
        this.capabilityContext = capabilityContext;
        this.matlabExecutableList = new ArrayList<>();
    }


    //currently not working
    /**
     * Automatically called by Bamboo
     * @param uiConfigSupport
     */
    @SuppressWarnings("unused")
     public void setUiConfigSupport(UIConfigSupport uiconfigsupport) {
       this.uiConfigSupport = uiconfigsupport;
    }
    
    
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        // create map with values captured from Task interface
        return config;
    }


    //Function called by Bamboo to populate MATLAB Executable drop down for the first time
    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);

        
    }
    
    //Function called by Bamboo on edit to display Executable drop down with selection
    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);

    }


    //TODO:
    // Validate matlab executable path here
    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);

    }

} 

