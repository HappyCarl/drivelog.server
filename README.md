DriveLog
=============================

Please use git flow. It works. Release-branch is "master". For Development, create a feature/ branch originating from develop. It's great.

We've got some pretty sweet Maven Plugins which makes this a lot simpler (and also automatically increments the version numbers!):

Start a Feature: `mvn jgitflow:feature-start`
Finish a Feature: `mvn jgitflow:feature-finish`
Start a Release: `mvn jgitflow:release-start`
Finish a Release: `mvn jgitflow:release-finish`
Start a Hotfix: `mvn jgitflow:hotfix-start`
Finish a Hotfix: `mvn jgitflow:hotfix-finish`

To make a release, you have to update `geotown.server-ear/src/main/application/META-INF/application.xml` to point to the newest version.
