package com.mathworks.ci.builder;

import com.atlassian.bamboo.specs.api.builders.task.Task;
// import com.mathworks.ci.task.MatlabBuildTask;
import com.mathworks.ci.properties.MatlabBuildTaskProperties;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

// import static checkNotBlank;

public class MatlabBuildTaskBuilder extends Task<MatlabBuildTaskBuilder, MatlabBuildTaskProperties> {
    private String matlabExecutable;
    private String buildTasks;

    /**
     * Sets label (<em>not a path</em>) of command to be executed. This label must be first
     * defined in the GUI on the Administration/Executables page.
     */
    public MatlabBuildTaskBuilder matlabExecutable(@NotNull final String matlabExecutable) {
        // checkNotEmpty("matlabExecutable", matlabExecutable);
        this.matlabExecutable = matlabExecutable;
        return this;
    }

    /**
     * Sets MATLAB command to be passed when run command is executed.
     */
    public MatlabBuildTaskBuilder buildTasks(@NotNull final String buildTasks) {
        this.buildTasks = buildTasks;
        return this;
    }

    @NotNull
    @Override
    protected MatlabBuildTaskProperties build() {
        return new MatlabBuildTaskProperties(
                description,
                taskEnabled,
                matlabExecutable,
                buildTasks,
                requirements,
                conditions);
    }

    @Override
    public boolean equals(Object o){
        return (this == o);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), matlabExecutable, buildTasks);
    }
}
