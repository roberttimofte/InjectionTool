# Generated from trgen 0.21.16
set -e
if [ -f transformGrammar.py ]; then python3 transformGrammar.py ; fi

# Because there is no integrated build script for Dart targets, we need
# to manually look at the version in pubspec.yaml and extract the
# version number. We can then use this with antlr4 to generate the
# parser and lexer.
version=4.13.1

antlr4 -package it.univr.injectiontool.javascript -visitor -v $version -encoding utf-8 -Dlanguage=Java   JavaScriptLexer.g4
antlr4 -package it.univr.injectiontool.javascript -visitor -v $version -encoding utf-8 -Dlanguage=Java   JavaScriptParser.g4


JAR=`python -c "import os; from pathlib import Path; print(os.path.join(Path.home() , '.m2',  'repository', 'org', 'antlr', 'antlr4', '$version', 'antlr4-$version-complete.jar'))"`
CLASSPATH="$JAR\;."
javac -d . -cp "$CLASSPATH" *.java

exit 0
