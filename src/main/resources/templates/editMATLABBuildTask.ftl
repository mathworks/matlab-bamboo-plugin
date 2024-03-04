[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="matlabExecutable"
extraUtility=addExecutableLink  list=uiConfigSupport.getExecutableLabels('matlab') required='true'/]

[@ui.bambooSection]
    [@ww.checkbox labelKey='matlab.options' name='optionsChecked'  toggle='true'/]
        [@ui.bambooSection dependsOn='optionsChecked' showOn=true]
            [@ww.textfield cssClass="long-field" name="matlabOptions"
                description="Specify MATLAB startup options as a space-separated list."/]
        [/@ui.bambooSection]
[/@ui.bambooSection]

[@ui.bambooSection]
    [@ww.textfield labelKey='matlab.build.tasks' name='buildTasks' cssClass="long-field" description="Specify the MATLAB build tasks to execute." required='false'/]
    [@ww.checkbox labelKey='matlab.build.options' name='buildOptionsChecked'  toggle='true'/]
        [@ui.bambooSection dependsOn='buildOptionsChecked' showOn=true]
            [@ww.textfield cssClass="long-field" name="buildOptions"
                description="Specify MATLAB build options as a space-separated list."/]
        [/@ui.bambooSection]
[/@ui.bambooSection]