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
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies

public class SwitchDependenciesPlugin implements Plugin<Project> {

    static boolean isTruthy(def project, def prop) {
        def isTruthy = project.hasProperty(prop) ? project.property(prop) : false
        // if isTruthy is not a boolean, convert it from truthy/falsy
        if (isTruthy instanceof String) {
            isTruthy = (isTruthy as String).equals("true")
        } else if (isTruthy instanceof Integer) {
            isTruthy = (isTruthy as Integer != 0)
        }
        return isTruthy
    }

    void apply(Project project) {
        // create the extension property
        final NamedDomainObjectContainer<SwitchDependencyGroup> groups =
                project.container(SwitchDependencyGroup.class)
        project.extensions.switchDependencies = groups

        project.getGradle().addListener(new DependencyResolutionListener() {
            @Override
            void beforeResolve(ResolvableDependencies resolvableDependencies) {
                project.switchDependencies.each { conditionalDeps ->
                    def group = conditionalDeps as SwitchDependencyGroup
                    def isTruthy = isTruthy(project, group.name)
                    // apply the proper closure
                    if (isTruthy) {
                        project.dependencies group.truthy
                    } else {
                        project.dependencies group.falsy
                    }
                }
                project.getGradle().removeListener(this)
            }

            @Override
            void afterResolve(ResolvableDependencies resolvableDependencies) {
            }
        })
    }
}