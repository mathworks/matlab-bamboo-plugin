package com.mathworks.ci.builder;

import com.atlassian.bamboo.specs.api.builders.task.Task;
// import com.mathworks.ci.task.MatlabTestTask;
import com.mathworks.ci.properties.MatlabTestTaskProperties;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

// import static checkNotBlank;

public class MatlabTestTaskBuilder extends Task<MatlabTestTaskBuilder, MatlabTestTaskProperties> {
    private String matlabExecutable;

    private String srcfolder;

    private String testFolders;
    private String testTag;

    private String junit;
    private String htmlTestResults;
    private String pdf;
    private String stm;
    private String html;
    private String htmlModel;

    private String strictChecked;
    private String useParallelChecked;
    private String outputDetail;
    private String loggingLevel;

    /**
     * Sets label (<em>not a path</em>) of command to be executed. This label must be first
     * defined in the GUI on the Administration/Executables page.
     */
    public MatlabTestTaskBuilder matlabExecutable(@NotNull final String matlabExecutable) {
        // checkNotEmpty("matlabExecutable", matlabExecutable);
        this.matlabExecutable = matlabExecutable;
        return this;
    }

    public MatlabTestTaskBuilder srcfolder(@NotNull final String srcfolder) {
        this.srcfolder = srcfolder;
        return this;
    }

    public MatlabTestTaskBuilder testFolders(@NotNull final String testFolders) {
        this.testFolders = testFolders;
        return this;
    }

    public MatlabTestTaskBuilder testTag(@NotNull final String testTag) {
        this.testTag = testTag;
        return this;
    }

    public MatlabTestTaskBuilder junit(@NotNull final String junit) {
        this.junit = junit;
        return this;
    }

    public MatlabTestTaskBuilder htmlTestResults(@NotNull final String htmlTestResults) {
        this.htmlTestResults = htmlTestResults;
        return this;
    }

    public MatlabTestTaskBuilder pdf(@NotNull final String pdf) {
        this.pdf = pdf;
        return this;
    }

    public MatlabTestTaskBuilder stm(@NotNull final String stm) {
        this.stm = stm;
        return this;
    }

    public MatlabTestTaskBuilder html(@NotNull final String html) {
        this.html = html;
        return this;
    }

    public MatlabTestTaskBuilder htmlModel(@NotNull final String htmlModel) {
        this.htmlModel = htmlModel;
        return this;
    }

    public MatlabTestTaskBuilder strictChecked(@NotNull final String strictChecked) {
        this.strictChecked = strictChecked;
        return this;
    }

    public MatlabTestTaskBuilder useParallelChecked(@NotNull final String useParallelChecked) {
        this.useParallelChecked = useParallelChecked;
        return this;
    }

    public MatlabTestTaskBuilder outputDetail(@NotNull final String outputDetail) {
        this.outputDetail = outputDetail;
        return this;
    }

    public MatlabTestTaskBuilder loggingLevel(@NotNull final String loggingLevel) {
        this.loggingLevel = loggingLevel;
        return this;
    }

    @NotNull
    @Override
    protected MatlabTestTaskProperties build() {
        return new MatlabTestTaskProperties(
                description,
                taskEnabled,
                matlabExecutable,
                srcfolder,
                testFolders,
                testTag,
                junit,
                htmlTestResults,
                pdf,
                stm,
                html,
                htmlModel,
                strictChecked,
                useParallelChecked,
                outputDetail,
                loggingLevel,
                requirements,
                conditions);
    }

    @Override
    public boolean equals(Object o){
        return (this == o);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), matlabExecutable, srcfolder, testFolders, testTag, junit, htmlTestResults, pdf, stm, html, htmlModel, strictChecked, useParallelChecked, outputDetail, loggingLevel);
    }
}
