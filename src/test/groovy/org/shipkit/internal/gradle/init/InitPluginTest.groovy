package org.shipkit.internal.gradle.init

import org.gradle.testfixtures.ProjectBuilder
import org.shipkit.gradle.init.InitShipkitFileTask
import spock.lang.Specification

import static InitPlugin.INIT_TRAVIS_TASK

class InitPluginTest extends Specification {

    def project = new ProjectBuilder().build()

    def "initializes .travis.yml file"() {
        project.plugins.apply(InitPlugin)
        assert !project.file(".travis.yml").exists()

        when:
        project.tasks[INIT_TRAVIS_TASK].execute()

        then:
        project.file(".travis.yml").isFile()
    }

    def "does not initialize .travis.yml file when already present"() {
        project.plugins.apply(InitPlugin)
        project.file(".travis.yml") << "foo"

        when:
        project.tasks[INIT_TRAVIS_TASK].execute()

        then:
        project.file(".travis.yml").text == "foo"
    }

    def "initializes shipkit file task"() {
        when:
        project.plugins.apply(InitPlugin)

        then:
        InitShipkitFileTask task = project.tasks.findByName(InitPlugin.INIT_SHIPKIT_FILE_TASK)
        task.shipkitFile == project.file("gradle/shipkit.gradle")
    }
}
