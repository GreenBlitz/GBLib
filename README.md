# GBLib
General robot code, usable in essentially every season

## Usage
This project relies upon [jitpack](https://jitpack.io/) in order to implement itself inside other projects.

In order to add this project you will need to use [gradle](https://gradle.org/). In your `build.gradle` file you should add the section:

    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
        mavenCentral()
    }

And alongside that, in your `dependencies` section add the line:

    compile 'com.github.GreenBlitz:GBLib:master-SNAPSHOT'

This should add the most recent release of the software into your project. In case this results
in issues, try to replace the `master-SNAPSHOT` part with the hash the the most recent commit (If you
do it that way, you will need to update the hash manually).
