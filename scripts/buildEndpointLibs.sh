#!/bin/bash
set -ev

APP_VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version|grep -Ev '(^\[|Download\w+:)')
TARGET="releases"

if [[ $APP_VERSION == *SNAPSHOT* ]]
then
    TARGET="snapshots"
    sed -i -e "s/drive-log.appspot.com/beta.drive-log.appspot.com/g" geotown.server-war/src/main/java/de/happycarl/geotown/server/GeoTownEndpoints.java
    mvn package
fi

mvn appengine:endpoints_get_client_lib

cd geotown.server-war/target/endpoints-client-libs/geotown

mvn org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=$APP_VERSION
mvn install

echo -e "Host github.com\n\tStrictHostKeyChecking no\n" >> ~/.ssh/config

git clone https://$GIT_TOKEN@github.com/HappyCarl/mvn-repo.git mvn-repo

mkdir -p mvn-repo/snapshots
mkdir -p mvn-repo/releases

REPO_STRING="release-repo::default::file:./mvn-repo/releases"
if [[ $APP_VERSION == *SNAPSHOT* ]]
then
    REPO_STRING="snapshot-repo::default::file:./mvn-repo/snapshots"
fi

mvn -DaltDeploymentRepository=$REPO_STRING clean deploy

cd mvn-repo
git config --global user.email "info@travis-ci.org"
git config --global user.name "Travis CI"

git add *
git commit -m "New GeoTown Java Clientlibs. Deploy ${TRAVIS_BUILD_NUMBER}."
git push -u origin master &> /dev/null

cd ../../../../../