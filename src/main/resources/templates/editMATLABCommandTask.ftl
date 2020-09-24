[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="matlabExecutable"
extraUtility=addExecutableLink  list="matlabExecutableList" required='true'/]
[@ww.textfield labelKey='matlab.plugin.command' name='matlabCommand' cssClass="long-field" description="Specify the MATLAB Script, function, or statement to execute" required='true'/]
