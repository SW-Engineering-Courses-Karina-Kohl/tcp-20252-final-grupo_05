@echo off
REM Script para compilar e executar a aplicação com tinylog no Windows

setlocal

set CLASSPATH=lib\tinylog-api-2.7.0.jar;lib\tinylog-impl-2.7.0.jar;src\main;.

echo Compilando classes principais...
javac -cp "%CLASSPATH%" src\main\main\Main.java

if errorlevel 1 (
	echo Erro na compilacao. Verifique as mensagens acima.
	exit /b 1
)

echo Executando aplicacao...
java -cp "%CLASSPATH%" main.Main

endlocal

