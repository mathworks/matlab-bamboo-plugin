package com.mathworks.ci.task;

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
import com.atlassian.bamboo.task.TaskException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;
import java.lang.ClassLoader;
import java.lang.String;

public abstract class BaseMatlabTaskType implements TaskType {

    //TODO:
    // 
    public abstract TaskResult execute(@NotNull TaskContext context) throws TaskException;

}
