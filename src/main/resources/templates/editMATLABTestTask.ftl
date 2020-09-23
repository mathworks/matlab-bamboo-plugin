[#assign addExecutableLink][@ui.displayAddExecutableInline executableKey='matlab'/][/#assign]
[@ww.select cssClass="builderSelectWidget" labelKey="executable.type" name="MATLABVersion"
extraUtility=addExecutableLink  required='true'/]

[@ww.checkbox labelKey='matlab.test.results.exists' name='resultsChecked' toggle='true'/]
   [@ui.bambooSection dependsOn='resultsChecked' showOn=true]
       [@ww.textfield labelKey='matlab.tests.results.file' name='matlab.tests.results.file' /]
   [/@ui.bambooSection]

[@ww.checkbox labelKey='matlab.test.coverage.exists' name='coverageChecked' toggle='true'/]
  [@ui.bambooSection dependsOn='coverageChecked' showOn=true]
      [@ww.textfield labelKey='matlab.code.coverage.directory' name='matlab.code.coverage.directory' /]
  [/@ui.bambooSection]

