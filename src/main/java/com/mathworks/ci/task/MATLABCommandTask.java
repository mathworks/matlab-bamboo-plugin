import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.utils.process.ExternalProcess;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class MATLABCommandTask extends BaseMATLABTaskType
{

    @NotNull
    @Override
    public abstract TaskResult execute(@NotNull TaskContext taskContext) throws TaskException;
   /** {
        TaskResultBuilder builder = TaskResultBuilder.create(taskContext);
        final String MATLABCommand = taskContext.getConfigurationMap().get("matlabcommand");
        final String MATLABRoot = taskContext.getConfigurationMap().get("builder.MATLAB.executable");

        // TODO
        // logic with using Run MATLAB Command
        // 
    }**/

}
