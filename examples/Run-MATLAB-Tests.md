# Run MATLAB Tests on Bamboo CI Server

This example shows how to run a suite of MATLAB&reg; unit tests with Bamboo&reg;. The example demonstrates how to:

* Create a build plan to access MATLAB tests hosted in a remote repository.
* Define a task in the plan to run the tests and generate test and coverage artifacts.
* Build the plan and examine the test results and the generated artifacts.

The build runs the tests in the Times Table App MATLAB project (which requires R2019a or later). You can create a working copy of the project files and open the project in MATLAB by running this statement in the Command Window.

```
matlab.project.example.timesTable
```

For more information about the Times Table App example project, see [Explore an Example Project](https://www.mathworks.com/help/matlab/matlab_prog/explore-an-example-project.html).

## Prerequisites
To follow the steps in this example:

* MATLAB and the plugin for MATLAB must be installed on your Bamboo Continuous Integration (CI) server. For information on how to install a plugin in Bamboo, see [Installing a plugin](https://confluence.atlassian.com/bamboo/installing-a-plugin-289277265.html).
* The Times Table App project must be under Git&trade; source control. For example, you can create a new repository for the project using your GitHub&reg; account. For more information, see [Use Source Control with Projects](https://www.mathworks.com/help/matlab/matlab_prog/use-source-control-with-projects.html).

## Create a Plan to Run MATLAB Tests
Create a new build plan and configure it by following these steps:

1. In your build dashboard, from the top menu bar, select **Create > Create plan**. Then, on the **Configure plan** page, specify your build plan details.

![configure_plan_1](https://user-images.githubusercontent.com/48831250/116616217-6ee1f000-a90a-11eb-9963-32ac53585fbf.png)

2. On the **Configure plan** page, in the **Link repository to new build plan** section, select **Link new repository**. Then, select **Git** from the drop-down list. Specify the name you want Bamboo to display for your repository in the **Display name** box, and paste the repository URL in the **Repository URL** box. 
   
![configure_plan_2](https://user-images.githubusercontent.com/48831250/116616281-87eaa100-a90a-11eb-8dae-28fbdf29df4b.png)
         
3. To confirm your plan details, click **Configure plan**. A new page opens that lets you add tasks to the default job of your plan. By default, Bamboo includes the **Source Code Checkout** task, which is responsible for checking out code into your working directory.

   To run MATLAB tests as part of your build, you need to add the **Run MATLAB Tests** task. To do so, click **Add Task** and then select the desired task from the **Task types** interface. You can access the task by typing MATLAB in the text box located at the top-right of the interface.

![find_task](https://user-images.githubusercontent.com/48831250/116619932-224ce380-a90f-11eb-9720-e3b67309b272.png)

4. In the **Run MATLAB Tests** task configuration interface, from the **Executable** list, select the MATLAB version that should run the tests. Then, specify the artifacts to be generated in the working directory. In this example, the plugin uses MATLAB R2021a to run the tests and generate a JUnit-style test results report, a PDF test report, and an HTML code coverage report. For more information about the tasks provided by the plugin, see [Plugin Configuration Guide](../CONFIGDOC.md).

![run_matlab_tests_task](https://user-images.githubusercontent.com/48831250/116717476-e6fdf380-a9a6-11eb-9813-56be6ed47604.png)

5. To publish the JUnit-style test results, add the [JUnit Parser](https://confluence.atlassian.com/bamboo/junit-parser-289277056.html) task to your job. To make sure that the task always runs irrespestive of the build status, specify it as a final task.

![junit_parser](https://user-images.githubusercontent.com/48831250/116721715-a2c12200-a9ab-11eb-9ac0-0ebbf2a34343.png)

6. Once you have added the required tasks for your build, create artifact definitions for the artifacts you want to access from the build dashboard. To create an artifact definition, select the **Artifacts** tab on the **Plan configuration** page. Then, click **Create artifact** and specify the required values for the artifact. 
  
![artifact_definition](https://user-images.githubusercontent.com/48831250/116756222-c0a57b80-a9d9-11eb-9499-0bf281661271.png)

## Run Tests and Inspect Artifacts
To build your plan, click **Run** on the **Plan configuration** page. Bamboo triggers a build, assigns it a number, and runs the build. It displays a green or red bar to indicate the success or failure of the build. In this example, the build succeeds because all of the tests in the Times Table App project pass.

To access the test and coverage artifacts, select the **Test** and **Artifacts** tabs. For example, select the **Artifacts** tab to access the generated HTML code coverage and PDF test reports. 

![artifact](https://user-images.githubusercontent.com/48831250/116756169-aa97bb00-a9d9-11eb-9e9d-88fe39056675.png)

## See Also
* [Plugin Configuration Guide](../CONFIGDOC.md)<br/>
* [Explore an Example Project (MATLAB)](https://www.mathworks.com/help/matlab/matlab_prog/explore-an-example-project.html)
