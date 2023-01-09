[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="matlabExecutable"
extraUtility=addExecutableLink list=uiConfigSupport.getExecutableLabels('matlab') required='true'/]

[@ui.bambooSection]
    [@ww.checkbox labelKey='matlab.test.srcfolder.exists' name='srcFolderChecked'  toggle='true'/]
        [@ui.bambooSection dependsOn='srcFolderChecked' showOn=true]
            [@ww.textfield labelKey='matlab.tests.source.folder' cssClass="long-field" name="srcfolder"
                description="Specify the locations of folders containing source code, relative to the working
                directory,<br> as a colon-separated or semicolon-separated list.<br> "/]
        [/@ui.bambooSection]
[/@ui.bambooSection]

[@ui.bambooSection titleKey='matlab.test.selection']
    [@ww.checkbox labelKey='matlab.test.select.byfolder' name='byFolderChecked' toggle='true'/]
        [@ui.bambooSection dependsOn='byFolderChecked' showOn=true]
             [@ww.textfield labelKey='matlab.tests.select.folderNames' name="testFolders" cssClass="long-field"
             description="Specify the locations of folders relative to the working directory, as a colon-separated or <br> semicolon-separated list.<br>" /]
        [/@ui.bambooSection]

    [@ww.checkbox labelKey='matlab.test.select.bytag' name='byTagChecked' toggle='true'/]
        [@ui.bambooSection dependsOn='byTagChecked' showOn=true]
             [@ww.textfield labelKey='matlab.tests.select.tagName' name="testTag" cssClass="long-field"
             description="Specify the test tag used to select test suite elements." /]
        [/@ui.bambooSection]
[/@ui.bambooSection]

[@ui.bambooSection titleKey='matlab.test.customizations']
    [@ww.checkbox labelKey='matlab.test.strict' name='strictChecked' toggle='true'/]

    [@ww.checkbox labelKey='matlab.test.use.parallel' name='useParallelChecked' toggle='true'/]

    [@ww.select cssClass="builderSelectWidget" labelKey="matlab.test.output.detail" name="outputDetail"
    list=["Default", "None", "Terse", "Concise", "Detailed", "Verbose"]/]

    [@ww.select cssClass="builderSelectWidget" labelKey="matlab.test.logging.level" name="loggingLevel"
    list=["Default", "None", "Terse", "Concise", "Detailed", "Verbose"]/]

    [@ww.select cssClass="builderSelectWidget" labelKey="Sample" list=["a", "b"]/]
[/@ui.bambooSection]

[@ui.bambooSection titleKey='matlab.test.artifacts']
    [@ww.checkbox labelKey='matlab.test.results.junit' name='junitChecked' toggle='true'/]
        [@ui.bambooSection dependsOn='junitChecked' showOn=true]
            [@ww.textfield labelKey='matlab.tests.results.file' name="junit" cssClass="long-field"
            description="Specify a path relative to the working directory." /]
        [/@ui.bambooSection]

    [@ww.checkbox labelKey='matlab.test.results.pdf' name='pdfChecked' toggle='true'/]
        [@ui.bambooSection dependsOn='pdfChecked' showOn=true]
            [@ww.textfield labelKey='matlab.tests.results.file' name="pdf" cssClass="long-field"
            description="Specify a path relative to the working directory." /]
        [/@ui.bambooSection]

    [@ww.checkbox labelKey='matlab.test.results.stm' name='stmChecked' toggle='true'/]
        [@ui.bambooSection dependsOn='stmChecked' showOn=true]
            [@ww.textfield labelKey='matlab.tests.results.file' name="stm" cssClass="long-field"
            description="Specify a path relative to the working directory." /]
        [/@ui.bambooSection]
[/@ui.bambooSection]

[@ui.bambooSection titleKey='matlab.coverage.artifacts']
    [@ww.checkbox labelKey='matlab.test.coverage.exists' name='htmlCoverageChecked' toggle='true'/]
        [@ui.bambooSection dependsOn='htmlCoverageChecked' showOn=true]
            [@ww.textfield labelKey='matlab.code.coverage.directory' name="html" cssClass="long-field"
            description="Specify a path relative to the working directory." /]
        [/@ui.bambooSection]

    [@ww.checkbox labelKey='matlab.model.coverage.exists' name='htmlModelCoverageChecked' toggle='true'/]
        [@ui.bambooSection dependsOn='htmlModelCoverageChecked' showOn=true]
            [@ww.textfield labelKey='matlab.code.coverage.directory' name="htmlModel" cssClass="long-field"
            description="Specify a path relative to the working directory." /]
        [/@ui.bambooSection]
[/@ui.bambooSection]
