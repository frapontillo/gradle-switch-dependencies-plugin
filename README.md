gradle-switchdependencies-plugin
================================

[![Apache License][license-image]][license-url]

_Gradle Plugin to conditionally switch dependencies._

--------------------------------

## Include the plugin

```gradle
plugins {
    id "com.github.frapontillo.switchdependencies" version "0.0.1"
}
```

## Use the plugin

The plugin uses project extension variables to conditionally apply dependencies.

In the following example, the `banana` property is defined on the project as being true, so the 
`banana.truthy` dependencies closure will be honored and the `banana` project will be included:

```gradle
project.ext.banana = true

switchDependencies {
    banana {
        truthy = {
            compile project(':banana')
        }
        falsy = {
            compile project(':avocado')
        }
    }
}
```

Truthy values are:

* the `true` boolean value
* the `"true"` string
* any integer other than `0`

Falsy values are:

* the `false` boolean value
* any string other than `"true"`
* the integer `0`

Both `truthy` and `falsy` closures are optional, declare them only if you need them.

## License

```
   Copyright 2015 Francesco Pontillo

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

[license-image]: http://img.shields.io/badge/license-Apache_2.0-blue.svg?style=flat
[license-url]: LICENSE
