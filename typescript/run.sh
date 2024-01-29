#
version=`grep "version=" build.sh | awk -F= '{print $2}'`
JAR=`python -c "import os; from pathlib import Path; print(os.path.join(Path.home() , '.m2',  'repository', 'org', 'antlr', 'antlr4', '$version', 'antlr4-$version-complete.jar'))"`
CLASSPATH="$JAR\;."
java -cp "$CLASSPATH" it.univr.injectiontool.typescript.Test "$@"
