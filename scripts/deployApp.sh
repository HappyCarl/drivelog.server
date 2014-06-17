#!/bin/bash
set -ev
cd geotown.server-ear/
echo -e "$GOOGLE_PASSWORD" | mvn appengine:update