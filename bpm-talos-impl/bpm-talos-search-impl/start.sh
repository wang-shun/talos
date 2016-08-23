#!/bin/sh

TEAM="bpm.talos"
APP_ID="bpm.talos.search"
MAIN_CLASS="me.ele.core.container.Container"

SCRIPTS_DIR=`dirname "$0"`
PROJECT_DIR=`cd $SCRIPTS_DIR && pwd`
DT=`date +"%Y%m%d_%H%M%S"`

MEM_OPTS="-Xms512m -Xmx1g -Xmn512m"
GC_OPTS="$GC_OPTS -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSInitiatingOccupancyFraction=60 -XX:CMSTriggerRatio=70"
GC_OPTS="$GC_OPTS -Xloggc:${PROJECT_DIR}/logs/gc_${DT}.log"
GC_OPTS="$GC_OPTS -XX:+PrintGCDateStamps -XX:+PrintGCDetails"
GC_OPTS="$GC_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${PROJECT_DIR}/tmp/heapdump_${DT}.hprof"
START_OPTS="$START_OPTS -Djava.io.tmpdir=$PROJECT_DIR/tmp/"
START_OPTS="$START_OPTS -Duser.dir=$PROJECT_DIR"
START_OPTS="$START_OPTS -DAPPID=${APP_ID}"
START_OPTS="$START_OPTS -Dcom.sun.management.jmxremote"
START_OPTS="$START_OPTS -Dcom.sun.management.jmxremote.authenticate=false"
START_OPTS="$START_OPTS -Dcom.sun.management.jmxremote.ssl=false"
START_OPTS="$START_OPTS -Dcom.sun.management.jmxremote.port=8194"
CLASS_PATH="$PROJECT_DIR/conf:$PROJECT_DIR/lib/*:$CLASS_PATH"

#run java
mkdir -p "$PROJECT_DIR/tmp/"
mkdir -p "$PROJECT_DIR/logs/"
java -server $MEM_OPTS $GC_OPTS $JMX_OPTS $START_OPTS -classpath $CLASS_PATH $MAIN_CLASS

# END OF FILE
