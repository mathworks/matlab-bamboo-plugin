package com.mathworks.ci.builder;

import com.atlassian.bamboo.specs.api.builders.task.Task;
// import com.mathworks.ci.tasktype.MatlabCommandTaskType;
import com.mathworks.ci.properties.MatlabCommandTaskProperties;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

// import static checkNotBlank;

public class MatlabCommandTaskBuilder extends Task<MatlabCommandTaskBuilder, MatlabCommandTaskProperties> {
    private String matlabExecutable;
    private String matlabCommand;

    /**
     * Sets label (<em>not a path</em>) of command to be executed. This label must be first
     * defined in the GUI on the Administration/Executables page.
     */
    public MatlabCommandTaskBuilder matlabExecutable(@NotNull final String matlabExecutable) {
        // checkNotEmpty("matlabExecutable", matlabExecutable);
        this.matlabExecutable = matlabExecutable;
        return this;
    }

    /**
     * Sets MATLAB command to be passed when run command is executed.
     */
    public MatlabCommandTaskBuilder matlabCommand(@NotNull final String matlabCommand) {
        this.matlabCommand = matlabCommand;
        return this;
    }

    @NotNull
    @Override
    protected MatlabCommandTaskProperties build() {
        return new MatlabCommandTaskProperties(
                description,
                taskEnabled,
                matlabExecutable,
                matlabCommand,
                requirements,
                conditions);
    }

    @Override
    public boolean equals(Object o){
        return (this == o);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), matlabExecutable, matlabCommand);
    }
}
