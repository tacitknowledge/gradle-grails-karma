package com.tacitknowledge.gradle.karma

import com.moowork.gradle.node.task.NpmTask

class InstallKarma extends NpmTask
{
  InstallKarma()
  {
    group = 'Karma'
    description = 'Installs karma bin (node) into project'

    project.afterEvaluate{
      workingDir = project.node.nodeModulesDir
      
      def pkgName = project.karma.version ? "karma@${project.karma.version}" : 'karma'
      args = ['install', pkgName]
      args += project.karma.additionalNodeDependencies?.collect { k,v -> "$k@$v" } ?: [:]

      (['karma'] + project.karma.additionalNodeDependencies?.collect {k,v->k}).each {
        outputs.dir new File(project.node.nodeModulesDir, "node_modules/$it")
      }
    }
  }
}
