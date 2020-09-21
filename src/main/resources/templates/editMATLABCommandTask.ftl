[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="MATLABVersion"
extraUtility=addExecutableLink  required='true'/]
[@ww.textfield labelKey='matlab.plugin.command' name='matlabcommand' cssClass="long-field" description="You can add multiple commands separated by comma or semi-colon"
required='true'/]
