package com.tacitknowledge.gradle.karma

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class KarmaPrepareTask extends DefaultTask
{
  KarmaPrepareTask()
  {
    group = 'Karma'
    description = 'Prepares karma configuration file'
  }

  @TaskAction
  void prepare()
  {
    def json = new JsonSlurper().parseText(new JsonBuilder(project.karma.config).toString())
    fillInDefaults(json)

    new File(project.buildDir, 'karma.conf.js').text =
"""
module.exports = function(config) {
  config.set(${JsonOutput.toJson(json)});
};
"""
  }

  private void fillInDefaults(config) {
    config.basePath = config.basePath ?: project.projectDir.path
    config.port = config.port ?: 9876
  }
}
