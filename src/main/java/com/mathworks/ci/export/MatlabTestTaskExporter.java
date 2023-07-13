/**
 * Copyright 2020-2022 The MathWorks, Inc.
 * 
 * Run MATLAB Test Task Invocation
 */

 package com.mathworks.ci.export;

 import com.atlassian.bamboo.task.export.TaskDefinitionExporter;
 import com.atlassian.bamboo.task.TaskContainer;
 import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
 import com.atlassian.bamboo.task.TaskDefinition;
 import com.atlassian.bamboo.specs.api.builders.task.Task;
 import com.atlassian.bamboo.task.export.TaskValidationContext;
 import com.atlassian.bamboo.specs.api.validators.common.ValidationContext;
 import com.atlassian.bamboo.specs.api.validators.common.ValidationProblem;
 import com.atlassian.bamboo.util.Narrow;
 import org.jetbrains.annotations.NotNull;
 import org.apache.commons.lang3.StringUtils;
 import java.util.Map;
 import java.util.HashMap;
 import java.util.List;
 import java.util.ArrayList;
 import com.mathworks.ci.properties.MatlabTestTaskProperties;
 import com.mathworks.ci.helper.MatlabBuilderConstants;
 import com.mathworks.ci.builder.MatlabTestTaskBuilder;
 
 public class MatlabTestTaskExporter implements TaskDefinitionExporter {
     private static final ValidationContext VALIDATION_CONTEXT = ValidationContext.of("MatlabTest task");
 
     @Override
     @NotNull
     public Map<String, String> toTaskConfiguration(@NotNull TaskContainer taskContainer, @NotNull final TaskProperties taskProperties) {
         final MatlabTestTaskProperties matlabTestTaskProperties = Narrow.downTo(taskProperties, MatlabTestTaskProperties.class);
 
         if (matlabTestTaskProperties != null) {
             final Map<String, String> cfg = new HashMap<>();
             cfg.put(MatlabBuilderConstants.MATLAB_CFG_KEY, matlabTestTaskProperties.getMatlabExecutable());
             cfg.put(MatlabBuilderConstants.SOURCE_FOLDER, matlabTestTaskProperties.getSrcfolder());
             cfg.put(MatlabBuilderConstants.TEST_FOLDERS, matlabTestTaskProperties.getTestFolders());
             cfg.put(MatlabBuilderConstants.TEST_TAG, matlabTestTaskProperties.getTestTag());
             cfg.put(MatlabBuilderConstants.JUNIT_FILE, matlabTestTaskProperties.getJunit());
             cfg.put(MatlabBuilderConstants.HTML_TEST_RESULTS_FOLDER, matlabTestTaskProperties.getHtmlTestResults());
             cfg.put(MatlabBuilderConstants.PDF_FILE, matlabTestTaskProperties.getPdf());
             cfg.put(MatlabBuilderConstants.STM_FILE, matlabTestTaskProperties.getStm());
             cfg.put(MatlabBuilderConstants.HTML_COVFOLDER, matlabTestTaskProperties.getHtml());
             cfg.put(MatlabBuilderConstants.HTML_MODELCOV_FOLDER, matlabTestTaskProperties.getHtmlModel());
             cfg.put(MatlabBuilderConstants.STRICT_CHX, matlabTestTaskProperties.getStrictChecked());
             cfg.put(MatlabBuilderConstants.USE_PARALLEL_CHX, matlabTestTaskProperties.getUseParallelChecked());
             cfg.put(MatlabBuilderConstants.OUTPUT_DETAIL_KEY, matlabTestTaskProperties.getOutputDetail());
             cfg.put(MatlabBuilderConstants.LOGGING_LEVEL_KEY, matlabTestTaskProperties.getLoggingLevel());
             return cfg;
         }
 
         throw new IllegalStateException("Unsupported import task of type: " + taskProperties.getClass().getName());
     }
 
     @NotNull
     @Override
     public MatlabTestTaskBuilder toSpecsEntity(final TaskDefinition taskDefinition) {
         final Map<String, String> configuration = taskDefinition.getConfiguration();
 
         // final MatlabTestTaskType matlabTestTaskType = new MatlabTestTaskType();
 
         final MatlabTestTaskBuilder task = new MatlabTestTaskBuilder()
                 .matlabExecutable(configuration.get(MatlabBuilderConstants.MATLAB_CFG_KEY))
                 .srcfolder(configuration.get(MatlabBuilderConstants.SOURCE_FOLDER))
                 .testFolders(configuration.get(MatlabBuilderConstants.TEST_FOLDERS))
                 .testTag(configuration.get(MatlabBuilderConstants.TEST_TAG))
                 .junit(configuration.get(MatlabBuilderConstants.JUNIT_FILE))
                 .htmlTestResults(configuration.get(MatlabBuilderConstants.HTML_TEST_RESULTS_FOLDER))
                 .pdf(configuration.get(MatlabBuilderConstants.PDF_FILE))
                 .stm(configuration.get(MatlabBuilderConstants.STM_FILE))
                 .html(configuration.get(MatlabBuilderConstants.HTML_COVFOLDER))
                 .htmlModel(configuration.get(MatlabBuilderConstants.HTML_MODELCOV_FOLDER))
                 .strictChecked(configuration.get(MatlabBuilderConstants.STRICT_CHX))
                 .useParallelChecked(configuration.get(MatlabBuilderConstants.USE_PARALLEL_CHX))
                 .outputDetail(configuration.get(MatlabBuilderConstants.OUTPUT_DETAIL_KEY))
                 .loggingLevel(configuration.get(MatlabBuilderConstants.LOGGING_LEVEL_KEY));
 
         return task;
     }
 
     @Override
     public List<ValidationProblem> validate(@NotNull TaskValidationContext taskValidationContext,
                                             @NotNull TaskProperties taskProperties) {
         final List<ValidationProblem> result = new ArrayList<>();
         final MatlabTestTaskProperties matlabTestTaskProperties = Narrow.downTo(taskProperties, MatlabTestTaskProperties.class);
 
         if(matlabTestTaskProperties == null){
             return result;
         }
 
         // final FastlaneTaskProperties properties = Narrow.downTo(taskProperties, FastlaneTaskProperties.class);
         // if (properties != null) {
         //     final String executableLabel = properties.getExecutableLabel();
         //     if (StringUtils.isEmpty(executableLabel)) {
         //         result.add(new ValidationProblem(FASTLANE_CONTEXT, "Executable can't be empty"));
         //     }
         //     if (StringUtils.isEmpty(properties.getLane())) {
         //         result.add(new ValidationProblem(FASTLANE_CONTEXT, "Lane can't be empty"));
         //     } else {
         //         final String capabilityKey = String.format("%s.%s", FastlaneCapabilityDefaultsHelper.FASTLANE_CAPABILITY_PREFIX, properties.getExecutableLabel());
         //         if (StringUtils.isBlank(capabilityContext.getCapabilityValue(capabilityKey))) {
         //             result.add(new ValidationProblem(FASTLANE_CONTEXT, "Executable " + properties.getExecutableLabel() + " doesn't exist."));
         //         }
         //     }
         // }
         return result;
     }
 }
 