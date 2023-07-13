/**
 * Copyright 2020-2022 The MathWorks, Inc.
 * 
 * Run MATLAB Build Task Invocation
 */

 package com.mathworks.ci.properties;

 import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
 import com.atlassian.bamboo.specs.api.codegen.annotations.Builder;
 import com.mathworks.ci.builder.MatlabBuildTaskBuilder;
 import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
 import com.atlassian.bamboo.specs.api.model.AtlassianModuleProperties;
 import com.atlassian.bamboo.specs.api.validators.common.ValidationContext;
 import com.atlassian.bamboo.specs.api.model.plan.requirement.RequirementProperties;
 import com.atlassian.bamboo.specs.api.model.plan.condition.ConditionProperties;
 import org.apache.commons.lang3.StringUtils;
 import javax.annotation.concurrent.Immutable;
 import java.util.Objects;
 import java.util.List;
 import java.util.ArrayList;
 import org.jetbrains.annotations.Nullable;
 import org.jetbrains.annotations.NotNull;
 
 // import static checkThat;
 
 @Immutable
 @Builder(MatlabBuildTaskBuilder.class)
 public class MatlabBuildTaskProperties extends TaskProperties {
     private static final AtlassianModuleProperties ATLASSIAN_PLUGIN =
             new AtlassianModuleProperties("com.mathworks.ci.matlab-bamboo-plugin:runMATLABBuild");
     private static final ValidationContext VALIDATION_CONTEXT = ValidationContext.of("MatlabBuild task");
 
     // @NotNull private final String matlabExecutable;
     private final String matlabExecutable;
     private final String buildTasks;
 
     // for importing
     private MatlabBuildTaskProperties() {
         this.matlabExecutable = null;
         this.buildTasks = null;
     }
 
     public MatlabBuildTaskProperties(@Nullable String description,
                                  boolean enabled,
                                  @NotNull String matlabExecutable,
                                  @NotNull String buildTasks,
                                  @NotNull List<RequirementProperties> requirements,
                                  @NotNull List<? extends ConditionProperties> conditions) throws PropertiesValidationException {
         super(description, enabled, requirements, conditions);
         // super();
 
         this.matlabExecutable = matlabExecutable;
         this.buildTasks = buildTasks;
 
         validate();
     }
 
     @Override
     public void validate() {
         super.validate();
 
         // checkThat(VALIDATION_CONTEXT, StringUtils.isNotBlank(matlabExecutable), "Executable is not defined");
     }
 
     @Override
     public boolean equals(Object o){
         return (this == o);
     }
 
     @Override
     public int hashCode(){
         return Objects.hash(super.hashCode(), getMatlabExecutable(), getBuildTasks());
     }
 
     @NotNull
     @Override
     public AtlassianModuleProperties getAtlassianPlugin() {
         return ATLASSIAN_PLUGIN;
     }
 
     public String getMatlabExecutable() {
         return matlabExecutable;
     }
 
     public String getBuildTasks() {
         return buildTasks;
     }
 }
