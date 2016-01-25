## scribble-java: Core components for the Scribble Java tools

#### Build with Maven

    $ mvn clean install

#### Run Scribble command-line tool

    $ mvn dependency:build-classpath -Dmdep.outputFile=classpath
    $ java -cp $(cat dist/classpath) org.scribble.cli.CommandLine [args] protocol.scr

Alternatively, build with [Eclipse](http://eclipse.org)
