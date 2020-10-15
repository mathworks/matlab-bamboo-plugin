[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="matlabExecutable"
extraUtility=addExecutableLink list=uiConfigSupport.getExecutableLabels('matlab') required='true'/]

[@ui.bambooSection]
    [@ww.checkbox labelKey='matlab.test.srcfolder.exists' name='srcFolderChecked'  toggle='true'/]
    [@ui.bambooSection dependsOn='srcFolderChecked' showOn=true]
        [@ww.textfield labelKey='matlab.tests.source.folder' cssClass="long-field" name="srcfolder" description="Specify the location of folders containing source code, relative to the working directory,<br>  as a colon-separated or a semicolon-separated list.<br>
         The specified folder and its subfolders are added to the top of the MATLAB search path."/]
        <small>To generate a coverage report, MATLAB uses only the source code in the specified folders and their subfolders.</small><br>
    [/@ui.bambooSection]
[/@ui.bambooSection]

[@ui.bambooSection titleKey='matlab.test.artifacts']
    [@ww.checkbox labelKey='matlab.test.results.exists' name='junitChecked' toggle='true'/]
    [@ui.bambooSection dependsOn='junitChecked' showOn=true]
        [@ww.textfield labelKey='matlab.tests.results.file' name="junit" cssClass="long-field" description="Specify a path relative to the working directory." /]
    [/@ui.bambooSection]

    [@ww.checkbox labelKey='matlab.test.coverage.exists' name='htmlCoverageChecked' toggle='true'/]
    [@ui.bambooSection dependsOn='htmlCoverageChecked' showOn=true]
        [@ww.textfield labelKey='matlab.code.coverage.directory' name="html" cssClass="long-field" description="Specify a path relative to the working directory." /]
    [/@ui.bambooSection]
[/@ui.bambooSection]
