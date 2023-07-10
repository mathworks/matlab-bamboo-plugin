/**
 * Copyright 2020-2022 The MathWorks, Inc.
 * 
 * Run MATLAB Test Task Invocation
 */

 package com.mathworks.ci.properties;

 import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
 import com.atlassian.bamboo.specs.api.codegen.annotations.Builder;
 import com.mathworks.ci.builder.MatlabTestTaskBuilder;
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
 @Builder(MatlabTestTaskBuilder.class)
 public class MatlabTestTaskProperties extends TaskProperties {
     private static final AtlassianModuleProperties ATLASSIAN_PLUGIN =
             new AtlassianModuleProperties("com.mathworks.ci.matlab-bamboo-plugin:runMATLABTest");
     private static final ValidationContext VALIDATION_CONTEXT = ValidationContext.of("MatlabTest task");
 
     // @NotNull private final String matlabExecutable;
 
     private final String matlabExecutable;
 
     private final String srcfolder;
 
     private final String testFolders;
     private final String testTag;
 
     private final String junit;
     private final String htmlTestResults;
     private final String pdf;
     private final String stm;
     private final String html;
     private final String htmlModel;
 
     private final String strictChecked;
     private final String useParallelChecked;
     private final String outputDetail;
     private final String loggingLevel;
 
     // for importing
     private MatlabTestTaskProperties() {
         this.matlabExecutable = null;
         this.srcfolder = null;
 
         this.testFolders = null;
         this.testTag = null;
 
         this.junit = null;
         this.htmlTestResults = null;
         this.pdf = null;
         this.stm = null;
         this.html = null;
         this.htmlModel = null;
 
         this.strictChecked = null;
         this.useParallelChecked = null;
         this.outputDetail = null;
         this.loggingLevel = null;
     }
 
     public MatlabTestTaskProperties(@Nullable String description,
                                  boolean enabled,
                                  @NotNull String matlabExecutable,
                                  @NotNull String srcfolder,
                                  @NotNull String testFolders,
                                  @NotNull String testTag,
                                  @NotNull String junit,
                                  @NotNull String htmlTestResults,
                                  @NotNull String pdf,
                                  @NotNull String stm,
                                  @NotNull String html,
                                  @NotNull String htmlModel,
                                  @NotNull String strictChecked,
                                  @NotNull String useParallelChecked,
                                  @NotNull String outputDetail,
                                  @NotNull String loggingLevel,
                                  @NotNull List<RequirementProperties> requirements,
                                  @NotNull List<? extends ConditionProperties> conditions) throws PropertiesValidationException {
         super(description, enabled, requirements, conditions);
         // super();
 
         this.matlabExecutable = matlabExecutable;
 
         this.srcfolder = srcfolder;
 
         this.testFolders = testFolders;
         this.testTag = testTag;
 
         this.junit = junit;
         this.htmlTestResults = htmlTestResults;
         this.pdf = pdf;
         this.stm = stm;
         this.html = html;
         this.htmlModel = htmlModel;
 
         this.strictChecked = strictChecked;
         this.useParallelChecked = useParallelChecked;
         this.outputDetail = outputDetail;
         this.loggingLevel = loggingLevel;
 
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
         return Objects.hash(super.hashCode(), getMatlabExecutable(), getSrcfolder(), getTestFolders(), getTestTag(), getJunit(), getHtmlTestResults(), getPdf(), getStm(), getHtml(), getHtmlModel(), getStrictChecked(), getUseParallelChecked(), getOutputDetail(), getLoggingLevel());
     }
 
     @NotNull
     @Override
     public AtlassianModuleProperties getAtlassianPlugin() {
         return ATLASSIAN_PLUGIN;
     }
 
     public String getMatlabExecutable() {
         return matlabExecutable;
     }
 
     public String getSrcfolder() {
         return srcfolder;
     }
 
     public String getTestFolders() {
         return testFolders;
     }
 
     public String getTestTag() {
         return testTag;
     }
 
     public String getJunit() {
         return junit;
     }
 
     public String getHtmlTestResults() {
         return htmlTestResults;
     }
 
     public String getPdf() {
         return pdf;
     }
 
     public String getStm() {
         return stm;
     }
 
     public String getHtml() {
         return html;
     }
 
     public String getHtmlModel() {
         return htmlModel;
     }
 
     public String getStrictChecked() {
         return strictChecked;
     }
 
     public String getUseParallelChecked() {
         return useParallelChecked;
     }
 
     public String getOutputDetail() {
         return outputDetail;
     }
 
     public String getLoggingLevel() {
         return loggingLevel;
     }
 }
