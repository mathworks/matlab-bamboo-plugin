[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="matlabExecutable"
extraUtility=addExecutableLink  list="matlabExecutableList" required='true'/]
[@ww.textfield labelKey='matlab.plugin.command' name='matlabCommand' cssClass="long-field" description="You can add multiple commands separated by comma or semi-colon"
required='true'/]
