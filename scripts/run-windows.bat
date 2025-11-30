@echo off
setlocal

:: vai para a raiz do projeto (um nivel acima da pasta scripts)
pushd %~dp0..

set CLASSPATH=lib\tinylog-api-2.7.0.jar;lib\tinylog-impl-2.7.0.jar;src\main;.

:: define configuração de Log
set LOG_CONFIG=config\tinylog.properties

echo.
echo [1/2] Compilando Aplicacao...
javac -cp "%CLASSPATH%" -d . src\main\*.java src\main\ui\*.java src\main\models\*.java src\main\service\*.java

if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilacao.
    pause
    popd
    exit /b 1
)

echo.
echo [2/2] Executando Main...
java -Dtinylog.configuration="%LOG_CONFIG%" -cp "%CLASSPATH%" main.Main

:: Restaura diretorio e sai
popd
pause