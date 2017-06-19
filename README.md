[![Build Status][ci-img]][ci] [![Released Version][maven-img]][maven]

# Java Tooling for Scribble

This project provides Java tooling/libraries for the Scribble multi-party protocol definition language.


## Building from source

First step is to clone this git repository locally. Once available, run the following maven command to build
the project:

    mvn [clean] install

The distribution will be available from the folder _dist/target_. The contents of the zip is:

- lib            jars needed to run the scribble-java tool
- scribblec.sh   script for running the command line tool


## Command line usage:

> List command line options.

    ./scribblec.sh --help


> Check well-formedness of global protocols in, e.g., Test.scr.

    ./scribblec.sh Test.scr

Notes:
- Use the -oldwf command line flag to use the simpler syntactic protocol
  well-formedness from the previous version of Scribble.
- Use the -V command line flag to obtain full traces for errors according
  to the new protocol validation (and other details).


> Project local protocol for role "C" of protocol "Proto" in Test.scr

    ./scribblec.sh Test.scr -project Proto C


> Print dot representation of Endpoint FSM for role "C" of protocol "Proto"
  in Test.scr

    ./scribblec.sh Test.scr -fsm Proto C


> Generate Java Endpoint API for role "C" of protocol "Proto"
  in Test.scr

    ./scribblec.sh -d . Test.scr -api Proto C


## Examples:

> To make an HelloWorld test:

  echo 'module Test; global protocol Proto(role C, role S) {
      Hello() from C to S; }' > Test.scr


Further examples can be found in:

  https://github.com/scribble/scribble-java/tree/master/modules/demos/scrib

The distribution zip does not include these examples.  They can be obtained
as part of the source repository, or separately via the above link.

> E.g. To generate the Java Endpoint API for role "C" of the "Adder"
  protocol from http://www.doc.ic.ac.uk/~rhu/scribble/fase16.pdf

    ./scribblec.sh -d modules/demos/scrib/fase16/src
        modules/demos/scrib/fase16/src/fase16/adder/Adder.scr -api Adder C


## Alternative command line usage:

To run the Scribble tool directly via java:

  try  scribblec.sh  with the  --verbose  flag

to see the underlying java command with main class, classpath and other args.

Or try (from Nick Ng):

  $ mvn dependency:build-classpath -Dmdep.outputFile=classpath
  $ java -cp $(cat dist/classpath)
        org.scribble.cli.CommandLine [args] MyModule.scr


## Issue reporting

Bugs and issues can be reported via the github Issues facility.

Or email  rhu1234 [at] doc.ic.ac.uk  excluding the 1234.


  [ci-img]: https://travis-ci.org/scribble/scribble-java.svg?branch=master
  [ci]: https://travis-ci.org/scribble/scribble-java
  [cov-img]: https://coveralls.io/repos/github/scribble/scribble-java/badge.svg?branch=master
  [cov]: https://coveralls.io/github/scribble/scribble-java?branch=master
  [maven-img]: https://img.shields.io/maven-central/v/org.scribble/scribble-core.svg?maxAge=2592000
  [maven]: http://search.maven.org/#search%7Cga%7C1%7Cscribble-core
