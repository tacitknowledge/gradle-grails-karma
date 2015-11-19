package com.tacitknowledge.gradle.karma

class KarmaWatchTask extends KarmaTask
{
  KarmaWatchTask()
  {
    group = 'Karma'
    description = 'Watches for changes and reruns karma tests automatically'
  }

  @Override
  def getAdditionalArguments()
  {
    def args = ['--no-single-run', '--auto-watch']
    if (project.karma.watchBrowsers) {
      args << '--browsers'
      args << project.karma.watchBrowsers.join(',')
    }
    return args
  }
}
