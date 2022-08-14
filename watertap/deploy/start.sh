#!/bin/bash
APPLICATION=../target/
SPRING_CONFIG=../src
MAX_MEMORY=2048m

function checkAapplicationExist() {
  if [ ! -f $APPLICATION ]; then
      echo "Cannot find $APPLICATION"
      echo "The file is absent or does not have execute permission"
      echo "This file is needed to run this program"
      exit 1
  fi
}

function checkJDKEnv() {
  if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
      echo "Neither the JAVA_HOME nor the JRE_HOME environment variable is defined"
      echo "At least one of these environment variable is needed to run this program"
      exit 1
  fi
}

function main() {
    checkAapplicationExist
    checkJDKEnv
    nohup java -Dspring.config.location=$SPRING_CONFIG \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n \
    -Dfile.encodeing=UTF-8 \
    -Xmx$MAX_MEMORY -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGcDateStamps \
    -Xloggc:gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 \
    -XX:GCLogFileSize=20M ${WITH_TRACE_AGENT} -jar $APPLICATION > test.log 2>&1 &
}

main