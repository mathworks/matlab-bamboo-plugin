/**
 * Copyright 2020-2022 The MathWorks, Inc.
 */

package com.mathworks.ci.configuration;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.mathworks.ci.helper.MatlabBuilderConstants;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MatlabTestTaskConfigurator extends MatlabTaskConfigurator {
    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);

        config.put(MatlabBuilderConstants.SRC_FLDR_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.SRC_FLDR_CHX)));
        config.put(MatlabBuilderConstants.SOURCE_FOLDER, params.getString(MatlabBuilderConstants.SOURCE_FOLDER));

        config.put(MatlabBuilderConstants.FILTER_BY_FOLDER_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.FILTER_BY_FOLDER_CHX)));
        config.put(MatlabBuilderConstants.TEST_FOLDERS, params.getString(MatlabBuilderConstants.TEST_FOLDERS));

        config.put(MatlabBuilderConstants.FILTER_BY_TAG_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.FILTER_BY_TAG_CHX)));
        config.put(MatlabBuilderConstants.TEST_TAG, params.getString(MatlabBuilderConstants.TEST_TAG));

        config.put(MatlabBuilderConstants.PDF_RESULTS_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.PDF_RESULTS_CHX)));
        config.put(MatlabBuilderConstants.PDF_FILE, params.getString(MatlabBuilderConstants.PDF_FILE));

        config.put(MatlabBuilderConstants.STM_RESULTS_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.STM_RESULTS_CHX)));
        config.put(MatlabBuilderConstants.STM_FILE, params.getString(MatlabBuilderConstants.STM_FILE));

        config.put(MatlabBuilderConstants.HTML_MODELCOV_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.HTML_MODELCOV_CHX)));
        config.put(MatlabBuilderConstants.HTML_MODELCOV_FOLDER, params.getString(MatlabBuilderConstants.HTML_MODELCOV_FOLDER));

        config.put(MatlabBuilderConstants.HTML_CODECOV_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.HTML_CODECOV_CHX)));
        config.put(MatlabBuilderConstants.HTML_COVFOLDER, params.getString(MatlabBuilderConstants.HTML_COVFOLDER));

        config.put(MatlabBuilderConstants.JUNIT_RESULTS_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.JUNIT_RESULTS_CHX)));
        config.put(MatlabBuilderConstants.JUNIT_FILE, params.getString(MatlabBuilderConstants.JUNIT_FILE));

        config.put(MatlabBuilderConstants.HTML_RESULTS_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.HTML_RESULTS_CHX)));
        config.put(MatlabBuilderConstants.HTML_FILE, params.getString(MatlabBuilderConstants.HTML_FILE));

        config.put(MatlabBuilderConstants.STRICT_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.STRICT_CHX)));

        config.put(MatlabBuilderConstants.USE_PARALLEL_CHX, String.valueOf(params.getBoolean(MatlabBuilderConstants.USE_PARALLEL_CHX)));

        config.put(MatlabBuilderConstants.OUTPUT_DETAIL_KEY, params.getString(MatlabBuilderConstants.OUTPUT_DETAIL_KEY));

        config.put(MatlabBuilderConstants.LOGGING_LEVEL_KEY, params.getString(MatlabBuilderConstants.LOGGING_LEVEL_KEY));

        return config;
    }

    //Populate default values for artifacts
    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
        context.put(MatlabBuilderConstants.JUNIT_FILE, MatlabBuilderConstants.JUNIT_DEFAULT_FILE);
        context.put(MatlabBuilderConstants.HTML_FILE, MatlabBuilderConstants.HTML_DEFAULT_FILE);
        context.put(MatlabBuilderConstants.HTML_COVFOLDER, MatlabBuilderConstants.HTML_CODECOV_DEFAULT_DIR);
        context.put(MatlabBuilderConstants.STM_FILE, MatlabBuilderConstants.STM_DEFAULT_FILE);
        context.put(MatlabBuilderConstants.PDF_FILE, MatlabBuilderConstants.PDF_DEFAULT_FILE);
        context.put(MatlabBuilderConstants.HTML_MODELCOV_FOLDER, MatlabBuilderConstants.HTML_MODELCOV_DEFAULT_DIR);
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        context.put(MatlabBuilderConstants.SOURCE_FOLDER, taskDefinition.getConfiguration().get(MatlabBuilderConstants.SOURCE_FOLDER));
        context.put(MatlabBuilderConstants.SRC_FLDR_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.SRC_FLDR_CHX));

        context.put(MatlabBuilderConstants.TEST_FOLDERS, taskDefinition.getConfiguration().get(MatlabBuilderConstants.TEST_FOLDERS));
        context.put(MatlabBuilderConstants.FILTER_BY_FOLDER_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.FILTER_BY_FOLDER_CHX));

        context.put(MatlabBuilderConstants.TEST_TAG, taskDefinition.getConfiguration().get(MatlabBuilderConstants.TEST_TAG));
        context.put(MatlabBuilderConstants.FILTER_BY_TAG_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.FILTER_BY_TAG_CHX));

        context.put(MatlabBuilderConstants.JUNIT_FILE, taskDefinition.getConfiguration().get(MatlabBuilderConstants.JUNIT_FILE));
        context.put(MatlabBuilderConstants.JUNIT_RESULTS_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.JUNIT_RESULTS_CHX));

        context.put(MatlabBuilderConstants.HTML_FILE, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_FILE));
        context.put(MatlabBuilderConstants.HTML_RESULTS_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_RESULTS_CHX));

        context.put(MatlabBuilderConstants.PDF_FILE, taskDefinition.getConfiguration().get(MatlabBuilderConstants.PDF_FILE));
        context.put(MatlabBuilderConstants.PDF_RESULTS_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.PDF_RESULTS_CHX));

        context.put(MatlabBuilderConstants.STM_FILE, taskDefinition.getConfiguration().get(MatlabBuilderConstants.STM_FILE));
        context.put(MatlabBuilderConstants.STM_RESULTS_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.STM_RESULTS_CHX));

        context.put(MatlabBuilderConstants.HTML_COVFOLDER, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_COVFOLDER));
        context.put(MatlabBuilderConstants.HTML_CODECOV_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_CODECOV_CHX));

        context.put(MatlabBuilderConstants.HTML_MODELCOV_FOLDER, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_MODELCOV_FOLDER));
        context.put(MatlabBuilderConstants.HTML_MODELCOV_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.HTML_MODELCOV_CHX));

        context.put(MatlabBuilderConstants.STRICT_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.STRICT_CHX));

        context.put(MatlabBuilderConstants.USE_PARALLEL_CHX, taskDefinition.getConfiguration().get(MatlabBuilderConstants.USE_PARALLEL_CHX));

        context.put(MatlabBuilderConstants.OUTPUT_DETAIL_KEY, taskDefinition.getConfiguration().get(MatlabBuilderConstants.OUTPUT_DETAIL_KEY));

        context.put(MatlabBuilderConstants.LOGGING_LEVEL_KEY, taskDefinition.getConfiguration().get(MatlabBuilderConstants.LOGGING_LEVEL_KEY));

    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);
        if (params.getBoolean(MatlabBuilderConstants.SRC_FLDR_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.SOURCE_FOLDER)))) {
            errorCollection.addError(MatlabBuilderConstants.SOURCE_FOLDER, "Specify a valid path to the source folder.");
        }
        if (params.getBoolean(MatlabBuilderConstants.FILTER_BY_FOLDER_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.TEST_FOLDERS)))) {
            errorCollection.addError(MatlabBuilderConstants.TEST_FOLDERS, "Specify a valid path to the test folder.");
        }
        if (params.getBoolean(MatlabBuilderConstants.FILTER_BY_TAG_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.TEST_TAG)))) {
            errorCollection.addError(MatlabBuilderConstants.TEST_TAG, "Specify a valid tag.");
        }
        if (params.getBoolean(MatlabBuilderConstants.JUNIT_RESULTS_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.JUNIT_FILE)))) {
            errorCollection.addError(MatlabBuilderConstants.JUNIT_FILE, "Specify a valid location for the JUnit-style test results.");
        }
        if (params.getBoolean(MatlabBuilderConstants.HTML_RESULTS_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.HTML_FILE)))) {
            errorCollection.addError(MatlabBuilderConstants.HTML_FILE, "Specify a valid location for the HTML test results.");
        }
        if (params.getBoolean(MatlabBuilderConstants.STM_RESULTS_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.STM_FILE)))) {
            errorCollection.addError(MatlabBuilderConstants.STM_FILE, "Specify a valid location for the Simulink Test Manager results.");
        }
        if (params.getBoolean(MatlabBuilderConstants.PDF_RESULTS_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.PDF_FILE)))) {
            errorCollection.addError(MatlabBuilderConstants.PDF_FILE, "Specify a valid location for the PDF test report.");
        }
        if (params.getBoolean(MatlabBuilderConstants.HTML_CODECOV_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.HTML_COVFOLDER)))) {
            errorCollection.addError(MatlabBuilderConstants.HTML_COVFOLDER, "Specify a valid location for the HTML code coverage report.");
        }
        if (params.getBoolean(MatlabBuilderConstants.HTML_MODELCOV_CHX) && (StringUtils.isBlank(params.getString(MatlabBuilderConstants.HTML_MODELCOV_FOLDER)))) {
            errorCollection.addError(MatlabBuilderConstants.HTML_MODELCOV_FOLDER, "Specify a valid location for the HTML model coverage report.");
        }
    }
}
