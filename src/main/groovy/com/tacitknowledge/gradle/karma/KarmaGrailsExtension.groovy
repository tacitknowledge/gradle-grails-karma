package com.tacitknowledge.gradle.karma

class KarmaGrailsExtension
{
  String version
  Map<String, String> additionalNodeDependencies

  Closure config

  List<String> headlessBrowsers
  List<String> watchBrowsers
}
