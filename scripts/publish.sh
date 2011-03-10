#!/bin/bash
# Publish the bundles to nexus

if [ "$RELEASE" == "No" ]; then
	mvn clean deploy
fi
