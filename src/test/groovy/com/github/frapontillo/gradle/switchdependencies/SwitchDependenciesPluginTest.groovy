/*
 * Copyright 2015 Francesco Pontillo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.frapontillo.gradle.switchdependencies

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class SwitchDependenciesPluginTest extends GroovyTestCase {

    private static Project beforeEach() {
        Project project = ProjectBuilder.builder().build()
        project.getPluginManager().apply 'java'
        project.getPluginManager().apply 'com.github.frapontillo.switchdependencies'
        return project
    }

    public void testPluginAddsExtension() {
        Project project = beforeEach()
        project.evaluate()
        assert project.extensions.switchDependencies instanceof NamedDomainObjectContainer
    }

    public void testNonExistingProperties() {
        Project project = beforeEach()
        assert !SwitchDependenciesPlugin.isTruthy(project, "red")
        assert !SwitchDependenciesPlugin.isTruthy(project, "green")
        assert !SwitchDependenciesPlugin.isTruthy(project, "yellow")
    }

    public void testTruthyValues() {
        Project project = beforeEach()
        project.ext.red = true
        project.ext.green = "true"
        project.ext.yellow = 1
        project.ext.banana = 1337
        assert SwitchDependenciesPlugin.isTruthy(project, "red")
        assert SwitchDependenciesPlugin.isTruthy(project, "green")
        assert SwitchDependenciesPlugin.isTruthy(project, "yellow")
        assert SwitchDependenciesPlugin.isTruthy(project, "banana")
    }

    public void testFalsyValues() {
        Project project = beforeEach()
        project.ext.red = false
        project.ext.green = "false"
        project.ext.green = "anything here actually"
        project.ext.yellow = 0
        assert !SwitchDependenciesPlugin.isTruthy(project, "red")
        assert !SwitchDependenciesPlugin.isTruthy(project, "green")
        assert !SwitchDependenciesPlugin.isTruthy(project, "yellow")
    }
}
