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
* The Times Table App project must be under source control. For example, you can create a new repository for the project using your GitHub&reg; account. For more information, see [Use Source Control with Projects](https://www.mathworks.com/help/matlab/matlab_prog/use-source-control-with-projects.html).

## Create Plan to Run MATLAB Tests
Create a new build plan and configure it by following these steps:

1. In your build dashboard, from the top menu bar, select **Create > Create plan**. Then, on the **Configure plan** page, specify your build plan details.

![configure_plan_details](https://user-images.githubusercontent.com/48831250/200667816-08ba3b1a-418e-4b7c-83a6-fdbce623a2a1.png)

2. On the **Configure plan** page, in the **Link repository to new build plan** section, specify the repository for your build plan.
            
![configure_plan_repository](https://user-images.githubusercontent.com/48831250/200669112-d41d8b12-65cf-4d2a-8bf8-9bf49a1a2a6f.png)

3. To confirm your plan details, click **Configure plan**. A new page opens that lets you add tasks to the default job of your plan. By default, Bamboo includes the **Source Code Checkout** task, which is responsible for checking out code from your repository.

4. To run MATLAB tests as part of your build, add the **Run MATLAB Tests** task by clicking **Add task** and then selecting **Run MATLAB Tests** from the **Task types** interface. You can access the task by typing `MATLAB` in the text box located at the upper-right corner of the interface.

![add_task](https://user-images.githubusercontent.com/48831250/200669849-074b86a1-2459-4ed8-8c18-1e13cd36bb0a.png)

5. In the **Run MATLAB Tests** task configuration interface, from the **Executable** list, select the MATLAB release to use for the task. Then, specify the artifacts to generate in the working directory. In this example, the plugin uses MATLAB R2023a to run the tests and generate JUnit-style test results, a PDF test report, and an HTML code coverage report. For more information about the tasks provided by the plugin, see [Plugin Configuration Guide](../CONFIGDOC.md).

![run_matlab_tests](https://user-images.githubusercontent.com/48831250/230393980-2c59701f-7b39-4feb-84d3-70bf3b404dc3.png)

6. To publish the JUnit-style test results, add the [JUnit Parser](https://confluence.atlassian.com/bamboo/junit-parser-289277056.html) task to your job. To make sure that the task runs regardless of the build status, specify it as a final task. 

   Once you have added the required tasks for your build, click **Save and continue**.

7. Create artifact definitions for the artifacts you want to access from the build dashboard. To create artifact definitions, click **Default Job**, and select the **Artifacts** tab on the page that opens. Then, for each artifact, click **Create artifact** and specify the required values. 
  
![artifact_definitions](https://user-images.githubusercontent.com/48831250/200671311-70b084ca-d401-43a3-ba6e-b9d0cdf66695.png)

## Run Tests and Inspect Artifacts
Now that your plan configuration is complete, you can run your plan.

1. Click the link to your plan at the upper-left corner of the page. 
2. At the upper-right corner of the plan page, enable the plan by selecting **Actions > Enable plan**. 
3. Select **Run > Run plan**. 

Bamboo triggers a build, runs it, and displays a green or red bar to indicate its success or failure. In this example, the build succeeds because all of the tests in the Times Table App project pass.

To access the test and coverage artifacts, select the **Tests** and **Artifacts** tabs. For example, select the **Artifacts** tab to access the generated HTML code coverage and PDF test reports. 

![build_outcome](https://user-images.githubusercontent.com/48831250/200681811-1119e3a7-07c5-49c2-a6c8-31c2a1c02cd4.png)

## See Also
* [Plugin Configuration Guide](../CONFIGDOC.md)<br/>
* [Explore an Example Project (MATLAB)](https://www.mathworks.com/help/matlab/matlab_prog/explore-an-example-project.html)
