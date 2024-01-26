# Generated from trgen 0.21.16
if (Test-Path -Path transformGrammar.py -PathType Leaf) {
    $(& python3 transformGrammar.py ) 2>&1 | Write-Host
}

$version = Select-String -Path "build.sh" -Pattern "version=" | ForEach-Object { $_.Line -split "=" | Select-Object -Last 1 }

$(& antlr4 -package it.univr.injectiontool.typescript -visitor -v $version TypeScriptLexer.g4 -encoding utf-8 -Dlanguage=Java   ; $compile_exit_code = $LASTEXITCODE) | Write-Host
if($compile_exit_code -ne 0){
    exit $compile_exit_code
}
$(& antlr4 -package it.univr.injectiontool.typescript -visitor -v $version TypeScriptParser.g4 -encoding utf-8 -Dlanguage=Java   ; $compile_exit_code = $LASTEXITCODE) | Write-Host
if($compile_exit_code -ne 0){
    exit $compile_exit_code
}


$JAR = python -c "import os; from pathlib import Path; print(os.path.join(Path.home(), '.m2', 'repository', 'org', 'antlr', 'antlr4', '$version', ('antlr4-' + '$version' + '-complete.jar')))"
$(& javac -d . -cp "${JAR};." .\*.java ; $compile_exit_code = $LASTEXITCODE) | Write-Host
exit $compile_exit_code
