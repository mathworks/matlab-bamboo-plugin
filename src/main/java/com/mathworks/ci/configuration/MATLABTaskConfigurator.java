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

//TODO:
// Implement interface for adding requirements
public class MATLABTaskConfigurator extends AbstractTaskConfigurator {
    //private static final String UI_CONFIG_SUPPORT = "uiConfigSupport";
    private static final Logger LOGGER = LoggerFactory.getLogger(MATLABTaskConfigurator.class);

    private List<String> MATLABExecutableList;

    public CapabilityContext capabilityContext;
    public String MATLABroot;

     public MATLABTaskConfigurator(){}

    public MATLABTaskConfigurator(CapabilityContext capabilityContext)
    {
        this.capabilityContext = capabilityContext;
        this.MATLABRootList = new ArrayList();
        this.createCustomCapabilityList();
    }


    private void createCustomCapabilityList() {
        Set<Capability> setOfCapabilities = ((ReadOnlyCapabilitySet)Objects.requireNonNull(this.capabilityContext.getCapabilitySet())).getCapabilities();
        Iterator itr = setOfCapabilities.iterator();

        while(itr.hasNext()) {
            Capability capability = (Capability)itr.next();
            String key = capability.getKey();
            System.out.println(key);
            if (key.matches("system\\.builder\\.matlab\\.")) {
                String MATLABExecutableLabel = key.substring(key.lastIndexOf('.') + 1);
                System.out.println(MATLABExecutableLabel);
                this.MATLABExecutableList.add(MATLABExecutableLabel);
            }
        }

    }


    //private UIConfigSupport uiConfigSupport;

    //currently not working
    /**
     * Automatically called by Bamboo
     * @param uiConfigSupport
     */
    //@SuppressWarnings("unused")
    //public void setUiConfigSupport(UIConfigSupport uiConfigSupport) {
    //    this.uiConfigSupport = uiConfigSupport;
    //}
    
    @notNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        // create map with values captured from Task interface
        return config;
    }


    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
        context.put("MATLABExecutableList",MATLABExecutableList);
        
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        context.put("MATLABExecutableList", MATLABExecutableList);

    }


    //TODO:
    // Validate matlab executable path here
    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);

    }

} 

