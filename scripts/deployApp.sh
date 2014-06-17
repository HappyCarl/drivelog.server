#!/bin/bash
set -ev

APP_VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version|grep -Ev '(^\[|Download\w+:)')

ARGS=""
if [[ $APP_VERSION == *SNAPSHOT* ]]
then
    ARGS="-Dapp.version=beta"
fi

cd geotown.server-ear/
mvn ear:generate-application-xml
echo -e "$GOOGLE_PASSWORD" | mvn appengine:update $ARGS