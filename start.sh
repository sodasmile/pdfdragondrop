#!/usr/bin/env bash

# Fail on missing variables
set -u

# Exit on error
set -e

if [ ! -d $JAVA_HOME ]; then
	echo "No JAVA_HOME set. Please do 'export JAVA_HOME=/path/to/my/java/installation' in order to alleviate this"
	exit 1
fi 

JAVA_EXE="$JAVA_HOME/bin/java"

if [ ! -x $JAVA_EXE ]; then
	echo "Cannot find executable $JAVA_EXE. Please make sure your JAVA_HOME is set to the correct setting it should be set to" 
	exit 1
fi

JFXRT="$JAVA_HOME/lib/jfxrt.jar"

if [ ! -e $JFXRT ]; then
	JFXRT="$JAVA_HOME/jre/lib/jfxrt.jar"
fi

if [ ! -e $JFXRT ]; then
	echo "Cannot find jar $JFXRT. Please make sure your JAVA_HOME points to a valid jre7 home with jfxrt.jar"
	exit 1
fi

if [ ! -d target ]; then
	echo "Cannot find target folder. Please make sure you've run mvn package or mvn install first"
	exit 1
fi


if [ "`find target -name 'pdfdragondrop*.jar' | wc -l`" -eq 0 ]; then
	echo "Cannot find packaged jar file, make sure you've run mvn package or mvn install first"
	exit 1
fi

DRAGFILE=`ls -tr target/pdfdragondrop*.jar | head -1`

$JAVA_EXE -cp "$JFXRT:$DRAGFILE" com.sodasmile.dragondrop.DropConvert
