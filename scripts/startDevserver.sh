#!/bin/sh

export MAVEN_OPTS="-Djava.awt.headless=true"
cd geotown.server-war
mvn appengine:devserver