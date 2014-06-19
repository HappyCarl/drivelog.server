#!/bin/sh

export MAVEN_OPTS="-Djava.awt.headless=true"
fswatch -o geotown.server-war/src | xargs -n1 -I{} mvn package
