[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="MATLABVersion"
extraUtility=addExecutableLink required='true'/]
[@ww.textfield labelKey='matlab.plugin.command' name='cbommand' required='true'/]
