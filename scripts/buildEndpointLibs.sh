#!/bin/bash
set -ev

mvn appengine:endpoints_get_client_lib
cd geotown.server-war/target/endpoints-client-libs/geotown
mvn install
echo -e "Host github.com\n\tStrictHostKeyChecking no\n" >> ~/.ssh/config
git clone https://$GIT_TOKEN@github.com/HappyCarl/mvn-repo.git mvn-repo
mkdir -p mvn-repo/snapshots
mvn -DaltDeploymentRepository=snapshot-repo::default::file:./mvn-repo/snapshots clean deploy
cd mvn-repo
git config --global user.email "info@travis-ci.org"
git config --global user.name "Travis CI"
git add *
git commit -m "New GeoTown Java Clientlibs. Deploy ${TRAVIS_BUILD_NUMBER}."
git push -u origin master &> /dev/null
cd ../
cd ../../../../