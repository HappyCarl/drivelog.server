#!/bin/bash
set -ev

APP_VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version|grep -Ev '(^\[|Download\w+:)')

ARGS=""
if [[ $APP_VERSION == *SNAPSHOT* ]]
then
    ARGS="-Dversion=beta"
fi

cd geotown.server-ear/
echo -e "$GOOGLE_PASSWORD" | mvn appengine:update $ARGS