@echo off
setlocal

:: sobe para a raiz
pushd %~dp0..

:: cria a pasta bin se não existir
if not exist "bin" mkdir bin

:: classpath apontando para 'bin'
set CLASSPATH=lib\tinylog-api-2.7.0.jar;lib\tinylog-impl-2.7.0.jar;src\main;bin

set LOG_CONFIG=config\tinylog.properties

echo.
echo [1/3] Compilando para pasta temporaria 'bin'...
javac -cp "%CLASSPATH%" -d bin src\main\*.java src\main\ui\*.java src\main\models\*.java src\main\service\*.java src\main\service\autenticacao\*.java

if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilacao.
    rmdir /s /q bin
    pause
    popd
    exit /b 1
)

echo.
echo [2/3] Executando Aplicacao...
:: precisa copiar os CSVs aqui tambem para a aplicação principal usa-los
if exist "src\resources\data\*.csv" copy /Y "src\resources\data\*.csv" . >nul

java -Dtinylog.configuration="%LOG_CONFIG%" -cp "%CLASSPATH%" main.Main

echo.
echo [3/3] Limpando arquivos temporarios...
if exist "bin" rmdir /s /q bin
del *.csv >nul 2>&1

popd
endlocal