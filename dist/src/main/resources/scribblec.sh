#!/bin/sh

# TODO: antlr 3.2 jar location
ANTLR=
# e.g. '/cygdrive/c/Users/[User]/.m2/repository/org/antlr/antlr-runtime/3.2/antlr-runtime-3.2.jar'

PRG=`basename "$0"`
DIR=`dirname "$0"`/..
#BASEDIR=$(dirname $0)

usage() {
  echo scribblec:
  cat <<EOF
  -h  --help                                     display this information
  --verbose                                      echo the python command
  -ip [path]                                     Scribble import path
  -project [simple global protocol name] [role]  project protocol
  -fsm [simple global protocol name] [role]      generate FSM
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

CLASSPATH=$DIR'/modules/cli/target/classes/:'$DIR'/modules/core/target/classes:'$DIR'/modules/parser/target/classes:'$ANTLR
CLASSPATH="'"`fixpath "$CLASSPATH"`"'"

usage=0
verbose=0

while true; do
    case "$1" in
        "")
            break
            ;;
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
        *)
            ARGS="$ARGS '$1'"
            shift
            ;;
    esac
done

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
