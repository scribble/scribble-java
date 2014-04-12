CP=$CLASSPATH:../lib/scribble-core.jar:../lib/scribble-cli.jar:../lib/scribble-parser.jar:../lib/scribble-validation.jar:../lib/scribble-projection.jar:../lib/scribble-trace.jar:../lib/scribble-monitor.jar:../lib/jackson-core-asl.jar:../lib/jackson-mapper-asl.jar:../lib/antlr-runtime.jar

java -classpath $CP org.scribble.cli.CommandLine $*
