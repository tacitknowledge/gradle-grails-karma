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

  @Override
  void exec() {
    executeExclusively {
      super.exec()
    }
  }

  void executeExclusively(Closure closure) {
    def npmUserFolder = new File(System.properties['user.home'], '.npm')
    npmUserFolder.mkdirs()
    def random = new RandomAccessFile(new File(npmUserFolder, 'node_modules_lock'), 'rw')
    def lock = null

    for (int i = 0; i < 3; i++) {
      try {
        logger.info("Trying to acquire lock to install karma. Attampt nr $i.")
        lock = random.channel.tryLock()
      } catch (ignore) { /*noop*/ }
      if(lock) {
        break
      } else {
        logger.warn('Sleeping before the next attempt')
        sleep(60000)
      }
    }

    if(!lock) {
      throw new IllegalStateException("Can't acquire exclusive lock to setup karma")
    }

    try{
      closure.run()
    } finally {
      if(lock) {
        lock.release()
        random.close()
      }
    }
  }
}
