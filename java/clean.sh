# Generated from trgen 0.21.16
rm -f *.interp
files=()
files+=( `java -jar "C:/Users/Rober/.m2/antlr4-4.12.0-complete.jar" -depend -encoding utf-8 -Dlanguage=Java   JavaLexer.g4 | awk '{print $1}' | grep -v ':'` )
files+=( `java -jar "C:/Users/Rober/.m2/antlr4-4.12.0-complete.jar" -depend -encoding utf-8 -Dlanguage=Java   JavaParser.g4 | awk '{print $1}' | grep -v ':'` )

for i in ${files[*]}
do
    rm -f $i
done
rm -f *.class
exit 0
