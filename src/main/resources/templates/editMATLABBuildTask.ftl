[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="matlabExecutable"
extraUtility=addExecutableLink  list=uiConfigSupport.getExecutableLabels('matlab') required='true'/]
[@ww.textfield labelKey='matlab.build.tasks' name='buildTasks' cssClass="long-field" description="Specify the MATLAB build tasks to execute." required='false'/]
