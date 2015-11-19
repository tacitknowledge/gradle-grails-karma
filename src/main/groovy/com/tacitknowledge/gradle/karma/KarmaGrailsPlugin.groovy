package com.tacitknowledge.gradle.karma

import com.moowork.gradle.node.NodePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class KarmaGrailsPlugin implements Plugin<Project>
{
  void apply(Project project)
  {
    project.plugins.apply(NodePlugin)

    project.extensions.create('karma', KarmaGrailsExtension)

    project.tasks.with {
      def installKarma = create(
              name: 'installKarma',
              type: InstallKarma)

      def karmaPrepare = create(
              name: 'karmaPrepare',
              type: KarmaPrepareTask,
              dependsOn: [installKarma])

      def karmaTest = create(
              name: 'karmaTest',
              type: KarmaTestTask,
              dependsOn: [karmaPrepare])

      def karmaWatch = create(
              name: 'karmaWatch',
              type: KarmaWatchTask,
              dependsOn: [karmaPrepare])

      findByName('test')?.dependsOn karmaTest
    }
  }
}
