# Run MATLAB Tests on Bamboo CI Server

This example shows how to run a suite of MATLAB&reg; unit tests with Bamboo&reg;. The example demonstrates how to:

* Create a build plan to access MATLAB tests hosted in a remote repository.
* Define a task in the plan to run the tests and generate test and coverage artifacts.
* Build the plan and examine the test results and the generated artifacts.

The build runs the tests in the Times Table App MATLAB project (which requires R2019a or later). You can create a working copy of the project files and open the project in MATLAB by running a statement in the Command Window. The statement to run depends on your MATLAB release:

R2023a and Earlier                 | Starting in R2023b
-----------------------------------| ------------------------------------------------
`matlab.project.example.timesTable`| `openExample("matlab/TimesTableProjectExample")`

For more information about the Times Table App project, see [Explore an Example Project](https://www.mathworks.com/help/matlab/matlab_prog/explore-an-example-project.html).

## Prerequisites
To follow the steps in this example:

* MATLAB and the plugin for MATLAB must be installed on your Bamboo Continuous Integration (CI) server. For information on how to install a plugin in Bamboo, see [Installing a plugin](https://confluence.atlassian.com/bamboo/installing-a-plugin-289277265.html).
* The Times Table App project must be under source control. For example, you can create a new repository for the project using your GitHub&reg; account. For more information, see [Use Source Control with Projects](https://www.mathworks.com/help/matlab/matlab_prog/use-source-control-with-projects.html).

## Create Plan to Run MATLAB Tests
Create a new build plan and configure it by following these steps:

1. In your build dashboard, from the top menu bar, select **Create > Create plan**. Then, on the **Configure plan** page, specify your build plan details.

![configure_plan_details](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/b49c77e2-bfef-4f67-9742-ec7a1731709a)

2. On the **Configure plan** page, in the **Link repository to new build plan** section, specify the repository for your build plan.
            
![configure_plan_repository](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/aadb6635-d309-47b8-a77e-1dbc6b36ab95)

3. To confirm your plan details, click **Configure plan**. A new page opens that lets you add tasks to the default job of your plan. By default, Bamboo includes the **Source Code Checkout** task, which is responsible for checking out code from your repository.

4. To run MATLAB tests as part of your build, add the **Run MATLAB Tests** task by clicking **Add task** and then selecting **Run MATLAB Tests** from the **Task types** interface. You can access the task by typing `MATLAB` in the text box located at the upper-right corner of the interface.

![add_task](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/8e8f8822-5214-4232-95ad-96305a93f240)

5. In the **Run MATLAB Tests** task configuration interface, from the **Executable** list, select the MATLAB release to use for the task. Then, specify the artifacts to generate in the working directory. In this example, the plugin uses MATLAB R2023b to run the tests and generate JUnit-style test results, a PDF test report, and an HTML code coverage report. For more information about the tasks provided by the plugin, see [Plugin Configuration Guide](../CONFIGDOC.md).

![run_matlab_tests](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/ee004c22-f76b-40a4-95a4-ebd2ae53147c)

6. To publish the JUnit-style test results, add the [JUnit Parser](https://confluence.atlassian.com/bamboo/junit-parser-289277056.html) task to your job. To make sure that the task runs regardless of the build status, specify it as a final task. 

7. Create artifact definitions for the artifacts you want to access from the build dashboard. To create artifact definitions, select the **Artifacts** tab. Then, for each artifact, click **Create artifact** and specify the required values. 
  
![artifact_definitions](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/101ea68c-f58c-42d4-bf9e-72b7d2f7df90)

## Run Tests and Inspect Artifacts
Now that your plan configuration is complete, you can run your plan.

1. Click the link to your plan at the upper-left corner of the page. 
2. At the upper-right corner of the plan page, enable the plan by selecting **Actions > Enable plan**. 
3. Select **Run > Run plan**. 

Bamboo triggers a build, runs it, and displays a green or red bar to indicate its success or failure. In this example, the build succeeds because all of the tests in the Times Table App project pass.

To access the test and coverage artifacts, select the **Tests** and **Artifacts** tabs. For example, select the **Artifacts** tab to access the generated HTML code coverage and PDF test reports. 

![build_outcome](https://github.com/mw-hrastega/Times-Table-App/assets/48831250/1ad34ab5-ff51-424f-bed6-17ea7d681c87)

## See Also
* [Plugin Configuration Guide](../CONFIGDOC.md)<br/>
* [Explore an Example Project (MATLAB)](https://www.mathworks.com/help/matlab/matlab_prog/explore-an-example-project.html)
