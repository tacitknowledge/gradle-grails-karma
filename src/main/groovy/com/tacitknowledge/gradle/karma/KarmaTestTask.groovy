package com.tacitknowledge.gradle.karma

class KarmaTestTask extends KarmaTask
{
  KarmaTestTask()
  {
    group = 'Karma'
    description = 'Runs karma tests once'
  }

  @Override
  def getAdditionalArguments()
  {
    def args = ['--single-run', '--no-auto-watch']
    if (project.karma.headlessBrowsers) {
      args << '--browsers'
      args << project.karma.headlessBrowsers.join(',')
    }
    return args
  }
}
