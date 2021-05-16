#!/bin/sh
#
# Copyright 2008 The Scribble Authors
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
# in compliance with the License. You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software distributed under the License
# is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
# or implied. See the License for the specific language governing permissions and limitations under
# the License.
#


##
# Config:
#
# $SCRIBBLE_HOME
#   Set this to the `scribble-java` root directory.
#   i.e., the directory of the `parent` mvn module (that contains the
#   `scribble-ast`, `scribble-cli`, etc. mvn submodules),
#   or the directory that contains the `lib` directory containing the generated
#   distribution jars.
if [ -z "${SCRIBBLE_HOME}" ]; then
    SCRIBHOME=$(dirname "$0")
else
    SCRIBHOME=${SCRIBBLE_HOME}
fi

# ANTLR 3 runtime jar location.
# Set this to the location of the ANTLR 3 runtime jar, or place the jar in:
# `$SCRIBHOME/lib`.  (This script looks for the ANTLR jar in those locations.)
ANTLR_RUNTIME_JAR=$SCRIBHOME'/scribble-parser/lib/antlr-3.5.2-complete.jar'
  # e.g., '~/.m2/repository/org/antlr/antlr-runtime/3.4/antlr-runtime-3.4.jar'
  #    or '/cygdrive/c/Users/[User]/.m2/repository/org/antlr/antlr-runtime/3.4/antlr-runtime-3.4.jar'
  #        (i.e., the Maven install location)

# Local env.
JAVA='java'  # Java executable
WSL=0        # Set to 1 for ';' classpath separator and wslpath formatting
CYGWIN=0     # Set to 1 for ';' classpath separator and cygpath formatting

#if [ "$(uname | grep -c CYGWIN)" -ne 0 ]; then
#    CYGWIN=1
#fi
#
##


usage() {
  echo 'Set $SCRIBBLE_HOME to the scribble-java root directory (default: current dir).'
  cat <<EOF
i.e., the directory that contains the scribble-java modules and/or lib directory.
Look inside the script for other settings.

Usage:  'scribblec.sh [option]... <SCRFILE> [option]...'

 <SCRFILE>     Source Scribble module (.scr file)

Options:
  -h, --help                 Show this info and exit
  -v                         Scribble debug info
  --verbose                  Echo the java command

  -ip <path>                 Scribble module import path

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
  -modelpng <simple global protocol name> <output file>
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


SEP=':'  # Classpath separator
if [ "$WSL" = 1 ] || [ "$CYGWIN" = 1 ]; then
  SEP=';'
fi

fixpath() {
    p="$1"
    if [ "$WSL" = 1 ]; then
        p=$(wslpath -w "$p" | sed 's/\\/\\\\/g')
    elif [ "$CYGWIN" = 1 ]; then
        p=$(cygpath -pw "$p")
    fi
    printf "%s" "$p"
}

CLASSPATH=
# If first module dir present, assume all are...
if [ -d "$SCRIBHOME"'/scribble-ast/target/classes' ]; then
    CLASSPATH=$(fixpath "$SCRIBHOME"'/scribble-ast/target/classes')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/scribble-cli/target/classes')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/scribble-codegen/target/classes')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/scribble-core/target/classes')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/scribble-main/target/classes')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/scribble-parser/target/classes')
fi
# If first module jar present, assume all are...
if [ -f "$SCRIBHOME"'/lib/scribble-ast.jar' ]; then
    if [ ! -z "$CLASSPATH" ]; then
      CLASSPATH="$CLASSPATH$SEP"
    fi
    CLASSPATH="$CLASSPATH"$(fixpath "$SCRIBHOME"'/lib/scribble-ast.jar')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/lib/scribble-cli.jar')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/lib/scribble-codegen.jar')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/lib/scribble-core.jar')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/lib/scribble-main.jar')
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/lib/scribble-parser.jar')
fi
# Below assumes CLASSPATH non-empty
if [ -f "$ANTLR_RUNTIME_JAR" ]; then
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$ANTLR_RUNTIME_JAR")
fi
if [ -f "$SCRIBHOME"'/lib/antlr.jar' ]; then
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/lib/antlr.jar')
fi
if [ -f "$SCRIBHOME"'/lib/antlr-runtime.jar' ]; then
    CLASSPATH="$CLASSPATH$SEP"$(fixpath "$SCRIBHOME"'/lib/antlr-runtime.jar')
fi
#if [ -f '/lib/commons-io.jar' ]; then
#    CLASSPATH=$CLASSPATH$SEP$(fixpath $SCRIBHOME'/lib/commons-io.jar')
#fi
#CLASSPATH=$CLASSPATH':'$SCRIBHOME'/lib/stringtemplate.jar'
##CLASSPATH=\'"$(fixpath "$CLASSPATH")"\'


usage=0
verbose=0
ARGS=

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

if [ "$usage" = 1 ] || [ -z "$ARGS" ]; then

    usage
    exit 0
fi


CMD="$JAVA"' -cp '\'"$CLASSPATH"\'' org.scribble.cli.CommandLine'

scribblec() {
    eval "$CMD" "$@"
}

if [ "$verbose" = 1 ]; then
    echo "$CMD" "$ARGS"
fi

scribblec "$ARGS"

