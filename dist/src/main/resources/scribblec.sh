#!/bin/sh

# Directory containing Scribble jars
LIB=lib

# antlr 3.2 location (if no lib jar)
ANTLR=
  # e.g. '/cygdrive/c/Users/[User]/.m2/repository/org/antlr/antlr-runtime/3.2/antlr-runtime-3.2.jar'

PRG=`basename "$0"`
DIR=`dirname "$0"`   # Non Cygwin..
#DIR=`dirname "$0"`/.. # Cygwin
#BASEDIR=$(dirname $0)

usage() {
  echo Usage:  'scribblec.sh [option]... <SCRFILE> [option]...'
  cat <<EOF
  
 <SCRFILE>     Source Scribble module (.scr file) 
  
Options:
  -h, --help                 Show this info and exit$
  -V                         Scribble debug info$
  --verbose                  Echo the java command$

  -ip <path>                 Scribble module import path$

  -oldwf                     Use the simpler syntactic protocol well-formedness


  -project <simple global protocol name> <role>  Project protocol

  -fsm <simple global protocol name> <role>      Generate default Endpoint FSM
  -aut                                           Output as aut (instead of dot)
  -fsmpng <simple global protocol name> <role> <output file>
          Draw default Endpoint FSM as png (via dot)
  -vfsm, -ufsm <simple global proto name> <role>   
  -vfsmpng, -ufsmpng <simple global proto name> <role> <output file>
          Output the EFSM used in validation (or the "unfair" variant)
  -minlts
          Minimise EFSMs for dot output and API generation (but not validation)
          (Requires ltsconvert)


  -model <simple global protocol name>          Generate global model
  -modelpng <simple global protocol name> <role> <output file>
          Draw global model as png (requires dot)
  -fair                                         Assume fair output choices
  -umodel, -umodelpng (with appropriate args)   "Unfair" variant


  -api <simple global protocol name> <role>     Generate Java Endpoint API
  -d <path>                                     API output directory
  -sessapi <simple global protocol name>        Generate Session API only
  -chanapi <simple global protocol name> <role> Generate State Channel API only
  -subtypes                                     Enable subtypes for -chanapi
EOF
}

fixpath() {
  windows=0

  if [ `uname | grep -c CYGWIN` -ne 0 ]; then
    windows=1
  fi

  cp="$1"
  if [ "$windows" = 1 ]; then
      cygpath -pw "$cp"
  else
      echo "$cp"
  fi
}

ARGS=

CLASSPATH=$DIR'/modules/cli/target/classes/'
CLASSPATH=$CLASSPATH':'$DIR'/modules/core/target/classes'
CLASSPATH=$CLASSPATH':'$DIR'/modules/parser/target/classes'
CLASSPATH=$CLASSPATH':'$DIR'/modules/f17/target/classes'
CLASSPATH=$CLASSPATH':'$ANTLR
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/antlr.jar'
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/antlr-runtime.jar'
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/commons-io.jar'
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/scribble-cli.jar'
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/scribble-core.jar'
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/scribble-parser.jar'
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/scribble-f17.jar'
CLASSPATH=$CLASSPATH':'$DIR'/'$LIB'/stringtemplate.jar'
CLASSPATH="'"`fixpath "$CLASSPATH"`"'"

usage=0
verbose=0
dot=0
nondot=0

while true; do
    case "$1" in
        "")
            break
            ;;
        #-dot)
        #    # Should not be used in conjunction with other flags..
        #    # ..that output to stdout
        #    ARGS="$ARGS '-fsm'"
        #    shift
        #    ARGS="$ARGS '$1'"
        #    shift
        #    ARGS="$ARGS '$1'"
        #    shift
        #    dot=$1
        #    if [ "$dot" == '' ]; then
        #      echo '-dot missing output file name argument'
        #      exit 1
        #    fi
        #    shift
        #    ;;
        -h)
            usage=1
            break
            ;;
        --help)
            usage=1
            break
            ;;
        --verbose)
            verbose=1
            shift
            ;;
        #-ip)
        #    ARGS="$ARGS '$1'"
        #    shift
        #    ;;
        #-d)
        #    ARGS="$ARGS '$1'"
        #    shift
        #    ;;
        #-subtypes)
        #    ARGS="$ARGS '$1'"
        #    shift
        #    ;;
        #-*)
        #    nondot=1
        #    ARGS="$ARGS '$1'"
        #    shift
        #    ;;
        *)
            ARGS="$ARGS '$1'"
            shift
            ;;
    esac
done

#if [ "$dot" != 0 ]; then
#  if [ $nondot == 1 ]; then
#    echo '-dot cannot be used in conjunction with other flags that output to stdout: ' $ARGS
#    exit 1
#  fi
#  ARGS="$ARGS |"
#  ARGS="$ARGS dot"
#  ARGS="$ARGS '-Tpng'"
#  ARGS="$ARGS '-o'"
#  ARGS="$ARGS '$dot'"
#fi

if [ "$usage" = 1 ]; then
  usage
  exit 0
fi

CMD='java -cp '$CLASSPATH' org.scribble.cli.CommandLine'

scribblec() {
  eval $CMD "$@"
}

if [ "$verbose" = 1 ]; then
  echo $CMD "$ARGS"
fi

scribblec "$ARGS"

