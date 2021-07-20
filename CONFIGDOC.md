# Plugin Configuration Guide

This plugin enables you to run MATLAB&reg; scripts, functions, and statements as part of your build. You also can run MATLAB and Simulink&reg; tests, and generate test and coverage artifacts.

-  [Define MATLAB as an Executable Capability](#define-matlab-as-an-executable-capability)
-  [Configure Tasks](#configure-tasks)
      -  [Run MATLAB Command](#run-matlab-command)
      -  [Run MATLAB Tests](#run-matlab-tests)
         - [Specify Source Folder](#specify-source-folder)
         - [Filter Tests](#filter-tests)
         - [Generate Test and Coverage Artifacts](#generate-test-and-coverage-artifacts)

## Define MATLAB as an Executable Capability
To run MATLAB code and Simulink models, define MATLAB as an executable capability in your Bamboo&reg; system. Once you have defined MATLAB as an executable capability, you can invoke it in your tasks. 

> :information_source: **Note:** If you do not define MATLAB as an executable capability in your Bamboo system, you still can add MATLAB as an executable within your tasks.

To define MATLAB as an agent-specific executable capability, local server executable capability, or shared remote executable capability, navigate to the appropriate capability definition interface and follow these steps:

1. From the **Capability type** list, select **Executable**.
2. From the **Type** list, select **MATLAB**.
3. In the **Executable label** box, specify a name for the executable. Bamboo displays this name in the **Executable** list of your tasks. 
4. In the **Path** box, type the full path to the desired MATLAB root folder.

   You can use the [`matlabroot`](https://www.mathworks.com/help/matlab/ref/matlabroot.html) function to return the full path to your MATLAB root folder. The path depends on the platform, MATLAB version, and installation location. This table shows examples of the root folder path on different platforms. 

   | Platform     | Path to MATLAB Root Folder      |
   |--------------|---------------------------------|
   | Windows&reg; | C:\Program Files\MATLAB\R2021a  |
   | Linux&reg;   | /usr/local/MATLAB/R2021a        |
   | macOS        | /Applications/MATLAB_R2021a.app |

This figure shows an example of how to define MATLAB R2021a as an agent-specific executable capability for a Windows agent.

![executable_capability](https://user-images.githubusercontent.com/48831250/116311171-f26bd780-a778-11eb-8273-5d936e73f913.png)

For more information on how to define executables in Bamboo, see [Defining a new executable capability](https://confluence.atlassian.com/bamboo/defining-a-new-executable-capability-289277164.html).

## Configure Tasks
The plugin provides you with two tasks: **Run MATLAB Command** and **Run MATLAB Tests**. You must specify a MATLAB executable for each task.

### Run MATLAB Command
The **Run MATLAB Command** task enables you to run MATLAB scripts, functions, and statements. You can use this task to flexibly customize your test run or add a MATLAB related step to your build.

When you create or configure the **Run MATLAB Command** task, you must:
* Specify the MATLAB executable to use for the task. If your desired MATLAB version is already defined as an executable capability, select it from the **Executable** list in the task configuration interface. Otherwise, click the **Add new executable** link to specify an executable label and the full path to the MATLAB root folder.
* Specify the MATLAB script, function, or statement you want to execute in the **Command** box. If you specify more than one script, function, or statement, use a comma or semicolon to separate them. If you want to run a script or function, do not specify the file extension. 

For example, use MATLAB R2021a to run a script named `myscript.m` in the root of your repository.

![run_matlab_command](https://user-images.githubusercontent.com/48831250/116311766-a0778180-a779-11eb-96da-1fec6a2b4f65.png)

MATLAB exits with exit code 0 if the specified script, function, or statement executes successfully without error. Otherwise, MATLAB terminates with a nonzero exit code, which causes the build to fail. To ensure that the build fails in certain conditions, use the [`assert`](https://www.mathworks.com/help/matlab/ref/assert.html) or [`error`](https://www.mathworks.com/help/matlab/ref/error.html) functions.

When you use this task, all of the required files must be on the MATLAB search path. If your script or function is not in the root of your repository, you can use the [`addpath`](https://www.mathworks.com/help/matlab/ref/addpath.html), [`cd`](https://www.mathworks.com/help/matlab/ref/cd.html), or [`run`](https://www.mathworks.com/help/matlab/ref/run.html) functions to ensure that it is on the path when invoked. For example, to run `myscript.m` in a folder named `myfolder` and located in the root of the repository, you can specify the contents of the **Command** box like this:

`addpath('myfolder'), myscript`

### Run MATLAB Tests
The **Run MATLAB Tests** task enables you to run MATLAB and Simulink tests and generate artifacts such as JUnit-style test results and HTML coverage reports. By default, the plugin includes any test files in your [MATLAB project](https://www.mathworks.com/help/matlab/projects.html) that have a `Test` label. If your plan does not use a MATLAB project, or if it uses a MATLAB release before R2019a, then the plugin includes all tests in the root of your repository or in any of its subfolders. The build fails if any of the included tests fails.

To run the tests in your repository, specify the MATLAB executable to use for the task. If your desired MATLAB version is already defined as an executable capability, select it from the **Executable** list in the task configuration interface. Otherwise, click the **Add new executable** link to specify an executable label and the full path to the MATLAB root folder. For example, use MATLAB R2021a to run the tests in your MATLAB project.

![run_matlab_tests](https://user-images.githubusercontent.com/48831250/126394185-67296d9b-0444-422d-a82e-a11c48ce92d3.png)

The **Run MATLAB Tests** task lets you customize your test run using options in the task configuration interface. For example, you can add folders to the MATLAB search path, control which tests to run, and generate various artifacts.

#### Specify Source Folder
To specify the location of a folder containing source code, select **Specify source folder**. Specify the location relative to the project root folder. The plugin adds the specified folder and its subfolders to the top of the MATLAB search path. If you specify a source folder and then generate a coverage report, the plugin uses only the source code in the specified folder and its subfolders to generate the report. 

If you specify more than one folder in the **Folder path** box, use a colon or semicolon to separate them.

![specify_source_folder](https://user-images.githubusercontent.com/48831250/116313830-49bf7700-a77c-11eb-870e-fd6b53371ede.png)

#### Filter Tests
By default, the **Run MATLAB Tests** task creates a test suite from all the tests in your MATLAB project. To create a filtered test suite, select **By folder**, **By tag**, or both:

* To specify the location of a folder containing test files, select **By folder**. Specify the location relative to the project root folder. The plugin creates a test suite using only the tests in the specified folder and its subfolders.

  If you specify more than one folder in the **Folder path** box, use a colon or semicolon to separate them.

* To select test suite elements using a test tag, select **By tag**. When you specify a test tag, the plugin creates a test suite using only the test elements with the specified tag.

![filter_tests](https://user-images.githubusercontent.com/48831250/116313716-1b419c00-a77c-11eb-9088-08a2a67887b4.png)

#### Generate Test and Coverage Artifacts
To generate test and coverage artifacts, select check boxes in the **Generate test artifacts** and **Generate coverage artifacts** sections. By default, the plugin assigns each specified artifact a path relative to the working directory. However, you can override the default values:

* If you select a test artifact check box, you can specify the path to store the artifact in the **File path** box.
* If you select a coverage artifact check box, you can specify the path to store the artifact in the **Folder path** box.

For example, run your tests, and generate a JUnit-style test results report and an HTML code coverage report at the specified locations in your working directory.

![generate_artifacts](https://user-images.githubusercontent.com/48831250/126394704-eb5bd664-0e1d-4daf-a76e-dc0551de1d43.png)

Artifacts to generate with the plugin are subject to these restrictions: 
* Producing a PDF test report is not currently supported on macOS platforms.
* Exporting Simulink Test&trade; Manager results requires a Simulink Test license and is supported in MATLAB R2019a or later.
* Producing an HTML model coverage report requires a Simulink Coverage&trade; license and is supported in MATLAB R2018b or later.

## See Also
* [Run MATLAB Tests on Bamboo CI Server](./examples/Run-MATLAB-Tests.md)<br/>
* [Continuous Integration with MATLAB and Simulink](https://www.mathworks.com/solutions/continuous-integration.html)
