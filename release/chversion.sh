#!/bin/bash
# Snapshot to release: first parameter is snapshot version, second is the release version

perl -pi -e "s/$1/$2/g" `find . -name pom.xml -or -name MANIFEST.MF`
