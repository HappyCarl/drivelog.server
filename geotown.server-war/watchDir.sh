#!/bin/sh
fswatch -o src | xargs -n1 -I{} mvn package
