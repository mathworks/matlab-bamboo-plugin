# Plugin Configuration Guide

This plugin enables you to build and test your MATLAB&reg; project as part of your Bamboo&reg; build. For example, you can automatically identify any code issues in your project, run tests and generate test and coverage artifacts, and package your files into a toolbox.

-  [Define MATLAB as Executable Capability](#define-matlab-as-executable-capability)
-  [Configure Tasks](#configure-tasks)
      -  [Run MATLAB Build](#run-matlab-build)
         - [Specify Build Options](#specify-build-options)
      -  [Run MATLAB Tests](#run-matlab-tests)
         - [Specify Source Folder](#specify-source-folder)
         - [Filter Tests](#filter-tests)
         - [Customize Test Run](#customize-test-run)
         - [Generate Test and Coverage Artifacts](#generate-test-and-coverage-artifacts)
      -  [Run MATLAB Command](#run-matlab-command)

## Define MATLAB as Executable Capability
To run MATLAB code and Simulink&reg; models, define MATLAB as an executable capability in your Bamboo system. Once you have defined MATLAB as an executable capability, you can invoke it in your tasks. 

> :information_source: **Note:** If you do not define MATLAB as an executable capability in your Bamboo system, you still can add MATLAB as an executable within your tasks.

To define MATLAB as an agent-specific executable capability, local server executable capability, or shared remote executable capability, navigate to the appropriate capability definition interface and follow these steps:

1. From the **Capability type** list, select **Executable**.
2. From the **Type** list, select **MATLAB**.
3. In the **Executable label** box, specify a name for the executable. Bamboo displays this name in the **Executable** list of your tasks. 
4. In the **Path** box, type the full path to the preferred MATLAB root folder.

   You can use the [`matlabroot`](https://www.mathworks.com/help/matlab/ref/matlabroot.html) function to return the full path to your MATLAB root folder. The path depends on the platform, MATLAB version, and installation location. This table shows examples of the root folder path on different platforms. 

   | Platform     | Path to MATLAB Root Folder      |
   |--------------|---------------------------------|
   | Windows&reg; | C:\Program Files\MATLAB\R2023b  |
   | Linux&reg;   | /usr/local/MATLAB/R2023b        |
   | macOS        | /Applications/MATLAB_R2023b.app |

This figure shows an example of how to define MATLAB R2023b as an agent-specific executable capability for a Windows agent.

![executable_capability](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/86b42194-bb3e-44f7-aad2-b75f2ecd0c91)


For more information on how to define executables in Bamboo, see [Defining a new executable capability](https://confluence.atlassian.com/bamboo/defining-a-new-executable-capability-289277164.html).

## Configure Tasks
The plugin provides you with three tasks: 

* To run a MATLAB build, use the [Run MATLAB Build](#run-matlab-build) task.
* To run MATLAB and Simulink tests and generate artifacts, use the [Run MATLAB Tests](#run-matlab-tests) task.
* To run a MATLAB script, function, or statement, use the [Run MATLAB Command](#run-matlab-command) task.

When you add a task to your plan, you must specify a MATLAB executable for it. To specify a MATLAB executable for a task, if your preferred MATLAB version is already defined as an executable capability, select it from the **Executable** list in the task configuration interface. Otherwise, click the **Add new executable** link to specify an executable label and the full path to the MATLAB root folder.

You can specify optional startup options for a MATLAB executable by first selecting **Specify startup options**  and then populating the box that appears in the task configuration interface. For example, specify `-nojvm` to start MATLAB without the JVM&trade; software. If you specify more than one startup option, use a space to separate them (for example, `-nojvm -logfile "output.log"`). For more information about MATLAB startup options, see [Commonly Used Startup Options](https://www.mathworks.com/help/matlab/matlab_env/commonly-used-startup-options.html).

> :information_source: **Note:** Using the **Options** box to specify the `-batch` or `-r` option is not supported.

### Run MATLAB Build
The **Run MATLAB Build** task enables you to run a build using the [MATLAB build tool](https://www.mathworks.com/help/matlab/matlab_prog/overview-of-matlab-build-tool.html). You can use this task to run the MATLAB build tasks specified in a file named `buildfile.m` in the root of your repository. To use the **Run MATLAB Build** task, you need MATLAB R2022b or a later release.

To configure the **Run MATLAB Build** task, first specify the MATLAB executable and optional startup options to use for the task. Then, specify your MATLAB build tasks and build options. If you specify more than one task in the **Tasks** box, use a space to separate them. If you do not specify any tasks, the plugin runs the default tasks in `buildfile.m` as well as all the tasks on which they depend. For example, use MATLAB R2023b to run a task named `mytask` as well as all the tasks on which it depends.

<img width="940" alt="run_matlab_build" src="https://github.com/mathworks/matlab-bamboo-plugin/assets/48831250/97a03376-4c15-4ddb-af17-2a0c3ff133b1">

MATLAB exits with exit code 0 if the build runs successfully. Otherwise, MATLAB terminates with a nonzero exit code, which causes the Bamboo build to fail.

#### Specify Build Options
To specify build options for your MATLAB build, first select **Specify build options**  and then populate the box that appears in the task configuration interface. For example, specify `-continueOnFailure` to continue running the MATLAB build upon a build environment setup or task failure. The plugin supports the same [options](https://www.mathworks.com/help/matlab/ref/buildtool.html#mw_50c0f35e-93df-4579-963d-f59f2fba1dba) that you can pass to the `buildtool` command when running a MATLAB build.

If you specify more than one build option, use a space to separate them.

<img width="940" alt="specify_build_options" src="https://github.com/mathworks/matlab-bamboo-plugin/assets/48831250/77766bae-284d-4bc7-b30d-daa84f8d02c5">

### Run MATLAB Tests
The **Run MATLAB Tests** task enables you to run MATLAB and Simulink tests and generate artifacts such as JUnit-style test results and HTML coverage reports. By default, the plugin includes any test files in your [MATLAB project](https://www.mathworks.com/help/matlab/projects.html) that have a `Test` label. If your plan does not use a MATLAB project, or if it uses a MATLAB release before R2019a, then the plugin includes all tests in the root of your repository and in any of its subfolders. The Bamboo build fails if any of the included tests fails.

To configure the **Run MATLAB Tests** task, specify the MATLAB executable and optional startup options to use for the task. For example, use MATLAB R2023b to run the tests in your MATLAB project.

![run_matlab_tests](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/2cf0f227-9aa0-4851-bdff-8a6c59d0a846)

You can customize the **Run MATLAB Tests** task by selecting options in the task configuration interface. For example, you can add source folders to the MATLAB search path, control which tests to run, and generate various test and coverage artifacts. If you do not select any of the existing options, all the tests in your project run, and any test failure causes the build to fail.

#### Specify Source Folder
To specify the location of a folder containing source code, select **Specify source folder**. Specify the location relative to the project root folder. The plugin adds the specified folder and its subfolders to the top of the MATLAB search path. If you specify a source folder and then generate a coverage report, the plugin uses only the source code in the specified folder and its subfolders to generate the report. 

If you specify more than one folder in the **Folder path** box, use a colon or semicolon to separate them.

![specify_source_folder](https://user-images.githubusercontent.com/48831250/217961712-a4488cf6-c240-4030-8bed-4408623ad181.png)

#### Filter Tests
By default, the **Run MATLAB Tests** task creates a test suite from all the tests in your MATLAB project. To create a filtered test suite, select **By folder**, **By tag**, or both:

* To specify the location of a folder containing test files, select **By folder**. Specify the location relative to the project root folder. The plugin creates a test suite using only the tests in the specified folder and its subfolders.

  If you specify more than one folder in the **Folder path** box, use a colon or semicolon to separate them.

* To select test suite elements using a test tag, select **By tag**. When you specify a test tag, the plugin creates a test suite using only the test elements with the specified tag.

![filter_tests](https://user-images.githubusercontent.com/48831250/217961944-4c3183d0-5c37-4ac9-a13c-8f2cf465ec51.png)

#### Customize Test Run
To customize your test run, select options in the **Customize test run** section:

* To apply strict checks when running the tests, select **Strict**. If you select this option, the plugin generates a qualification failure whenever a test issues a warning. Selecting **Strict** is the same as specifying the `Strict` name-value argument of the [`runtests`](https://www.mathworks.com/help/matlab/ref/runtests.html) function as `true`.
* To run tests in parallel, select **Use parallel**. Selecting **Use parallel** is the same as specifying the `UseParallel` name-value argument of `runtests` as `true`. You must have Parallel Computing Toolbox&trade; installed to use this option. If other selected options are not compatible with running tests in parallel, the plugin runs the tests in serial regardless of your selection.
* To control the amount of output detail displayed for your test run, select a value from the **Output detail** list. Selecting a value for this option is the same as specifying the `OutputDetail` name-value argument of `runtests` as that value. By default, the plugin displays failing and logged events at the `Detailed` level and test run progress at the `Concise` level.
* To include diagnostics logged by the [`log (TestCase)`](https://www.mathworks.com/help/matlab/ref/matlab.unittest.testcase.log.html) and [`log (Fixture)`](https://www.mathworks.com/help/matlab/ref/matlab.unittest.fixtures.fixture.log.html) methods at a specified verbosity level, select a value from the **Logging level** list. Selecting a value for this option is the same as specifying the `LoggingLevel` name-value argument of `runtests` as that value. By default, the plugin includes diagnostics logged at the `Terse` level. 

![customize_test_run](https://user-images.githubusercontent.com/48831250/217962218-7a47fa54-6fdc-484a-8ce9-e803815afcd2.png)

#### Generate Test and Coverage Artifacts
To generate test and coverage artifacts, select check boxes in the **Generate test artifacts** and **Generate coverage artifacts** sections. By default, the plugin assigns to each specified artifact a path relative to the working directory. However, you can override the default values:

* If you select a check box to generate an HTML report, you can specify the path to store the artifact in the **Folder path** box.
* If you select a check box to generate an artifact other than an HTML report, you can specify the path to store the artifact in the **File path** box.

For example, run your tests, and generate test results in JUnit XML format and a code coverage report in HTML format at the specified locations in your working directory.

![generate_artifacts](https://user-images.githubusercontent.com/48831250/230390939-dd8955e1-7aa1-41e5-a62e-750805d13fb2.png)

Artifacts to generate with the plugin are subject to these restrictions: 
* Exporting Simulink Test&trade; Manager results requires a Simulink Test license and is supported in MATLAB R2019a and later.
* Producing an HTML model coverage report requires a Simulink Coverage&trade; license and is supported in MATLAB R2018b and later.

### Run MATLAB Command
The **Run MATLAB Command** task enables you to run MATLAB scripts, functions, and statements. You can use this task to customize your test run or add a step in MATLAB to your plan.

To configure the **Run MATLAB Command** task, first specify the MATLAB executable and optional startup options to use for the task. Then, specify the MATLAB script, function, or statement you want to execute in the **Command** box. If you specify more than one script, function, or statement, use a comma or semicolon to separate them. If you want to run a script or function, do not specify the file extension. For example, use MATLAB R2023b to run a script named `myscript.m` located in the root of your repository.

![run_matlab_command](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/8d371668-2e45-467d-98ad-b9310df244c0)

MATLAB exits with exit code 0 if the specified script, function, or statement executes successfully without error. Otherwise, MATLAB terminates with a nonzero exit code, which causes the Bamboo build to fail. To fail the build in certain conditions, use the [`assert`](https://www.mathworks.com/help/matlab/ref/assert.html) or [`error`](https://www.mathworks.com/help/matlab/ref/error.html) function.

When you use this task, all of the required files must be on the MATLAB search path. If your script or function is not in the root of your repository, you can use the [`addpath`](https://www.mathworks.com/help/matlab/ref/addpath.html), [`cd`](https://www.mathworks.com/help/matlab/ref/cd.html), or [`run`](https://www.mathworks.com/help/matlab/ref/run.html) function to ensure that it is on the path when invoked. For example, to run `myscript.m` in a folder named `myfolder` located in the root of the repository, you can specify the contents of the **Command** box like this:

`addpath("myfolder"), myscript`

## See Also
* [Run MATLAB Tests on Bamboo CI Server](./examples/Run-MATLAB-Tests.md)<br/>
* [Continuous Integration with MATLAB and Simulink](https://www.mathworks.com/solutions/continuous-integration.html)
* [Continuous Integration with MATLAB on CI Platforms](https://www.mathworks.com/help/matlab/matlab_prog/continuous-integration-with-matlab-on-ci-platforms.html)
