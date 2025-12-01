@echo off
setlocal enabledelayedexpansion

:: sobe para a raiz do projeto
pushd %~dp0..

:: adicionamos "bin" ao classpath no lugar do "."
set CLASSPATH=lib\junit-platform-console-standalone-1.10.2.jar;lib\tinylog-api-2.7.0.jar;lib\tinylog-impl-2.7.0.jar;src\resources\data;src\resources;src\main;src\test;bin

set LOG_CONFIG=config\tinylog.properties

echo.
echo [1/3] Preparando Ambiente...

:: Cria pasta bin para os .class (para não sujar a raiz)
if not exist "bin" mkdir bin

:: Copia os CSVs de src\resources\data para a raiz
set "CSV_COPIADO=N"

if exist "src\resources\data\*.csv" (
    copy /Y "src\resources\data\*.csv" . >nul
    set "CSV_COPIADO=S"
) else (
    echo [AVISO] CSVs nao encontrados em src\resources\data.
)
echo.
echo [2/3] Compilando os Arquivos...
:: o parametro '-d bin' joga a sujeira (.class) para dentro da pasta bin
javac -cp "%CLASSPATH%" -d bin src\main\*.java src\main\ui\*.java src\main\models\*.java src\main\service\*.java src\main\service\autenticacao\*.java

if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilacao do Main.
    goto cleanup
)

if exist "src\test" (
    javac -cp "%CLASSPATH%" -d bin src\test\*.java
    if %errorlevel% neq 0 (
        echo [ERRO] Falha na compilacao dos Testes.
        goto cleanup
    )
)

echo.
echo [3/3] Executando Testes...
java -Dtinylog.configuration="%LOG_CONFIG%" -jar lib\junit-platform-console-standalone-1.10.2.jar execute --scan-classpath --classpath "%CLASSPATH%" --details=tree

:cleanup

:: apaga a pasta bin inteira
if exist "bin" (
    rmdir /s /q "bin"
)

:: apaga os CSVs copiados na raiz
if "!CSV_COPIADO!"=="S" (
    del *.csv >nul 2>&1
)

popd
pause
exit /b 0