[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="matlabExecutable"
extraUtility=addExecutableLink list=uiConfigSupport.getExecutableLabels('matlab') required='true'/]

[@ui.bambooSection titleKey='matlab.test.srcfolder']
    [@ww.checkbox labelKey='matlab.test.srcfolder.exists' name='srcFolderChecked'  toggle='true'/]
    [@ui.bambooSection dependsOn='srcFolderChecked' showOn=true]
        [@ww.textfield labelKey='matlab.tests.source.folder' cssClass="long-field" description="Specify the location of folders containing source code, relative to the working directory,<br>  as a colon-separated or a semicolon-separated list. <br> The specified folder and its subfolders are added to the top of the MATLAB search path." name='matlab.tests.source.folder' /]
        <small>To generate a coverage report, MATLAB uses only the source code in the specified folders and their subfolders.</small><br>
    [/@ui.bambooSection]
[/@ui.bambooSection]

[@ui.bambooSection titleKey='matlab.test.artifacts']
    [@ww.checkbox labelKey='matlab.test.results.exists' name='resultsChecked' toggle='true'/]
    [@ui.bambooSection dependsOn='resultsChecked' showOn=true]
        [@ww.textfield labelKey='matlab.tests.results.file' cssClass="long-field" description="Specify a path relative to the working directory." value="artifacts/junit/results.xml" name='matlab.tests.results.file' /]
    [/@ui.bambooSection]

    [@ww.checkbox labelKey='matlab.test.coverage.exists' name='coverageChecked' toggle='true'/]
    [@ui.bambooSection dependsOn='coverageChecked' showOn=true]
        [@ww.textfield labelKey='matlab.code.coverage.directory' cssClass="long-field" description="Specify a path relative to the working directory." value="artifacts/htmlcoverage" name='matlab.code.coverage.directory' /]
    [/@ui.bambooSection]
[/@ui.bambooSection]
