package com.tacitknowledge.gradle.karma

import com.moowork.gradle.node.task.NodeTask

abstract class KarmaTask extends NodeTask
{
  @Override
  void exec()
  {
    workingDir = project.projectDir
    script = new File(project.node.nodeModulesDir, 'node_modules/karma/bin/karma')

    args = ['start', new File(project.buildDir, 'karma.conf.js')] + additionalArguments
    super.exec()
  }

  abstract def getAdditionalArguments()
}
