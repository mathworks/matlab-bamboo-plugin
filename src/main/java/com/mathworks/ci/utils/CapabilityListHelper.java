package com.mathworks.ci.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.Set;

import com.atlassian.bamboo.v2.build.agent.capability.Capability;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.bamboo.task.TaskDefinition;

public class CapabilityListHelper {
    private List<String> matlabExecutablesList;
    private Map<String, Capability> matlabLabelToCapability;
    protected CapabilityContext capabilityContext;

    public CapabilityListHelper(CapabilityContext capabilityContext) {
        this.capabilityContext = capabilityContext;
        this.matlabExecutablesList = new ArrayList<>();
        this.matlabLabelToCapability = new HashMap<>();
        populateMatlabExecutables();
    }

    public List<String> getMatlabExecutablesList() {
        return matlabExecutablesList;
    }

    public void setMatlabExecutablesList(List<String> matlabExecutablesList) {
        this.matlabExecutablesList = matlabExecutablesList;
    }

    public Map<String, Capability> getMatlabLabelToCapability() {
        return matlabLabelToCapability;
    }

    public void setMatlabLabelToCapability(Map<String, Capability> matlabLabelToCapability) {
        this.matlabLabelToCapability = matlabLabelToCapability;
    }

    public void populateMatlabExecutables() {
        Set<Capability> setOfCapabilities = Objects.requireNonNull(capabilityContext.getCapabilitySet()).getCapabilities();

        List<String> matlabExecutables = new ArrayList<>();
        Map<String, Capability> matlabCapabilityMap = new HashMap<>();

        for (Capability capability : setOfCapabilities) {
            String key = capability.getKey();
            if (key.contains("system.builder.matlab.")) {
                String Label = key.substring(key.lastIndexOf('.') + 1);
                matlabCapabilityMap.put(Label, capability);
                matlabExecutables.add(Label);
            }
        }
        this.setMatlabExecutablesList(matlabExecutables);
        this.setMatlabLabelToCapability(matlabCapabilityMap);
    }
}
