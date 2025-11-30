#!/usr/bin/env bash
# Script para compilar e executar a aplicacao no macOS/Linux
set -e

# garante que estamos na raiz do projeto (um nivel acima da pasta scripts)
cd "$(dirname "$0")/.."

# caminho para configuracao do Log
LOG_CONFIG="config/tinylog.properties"

CLASSPATH="lib/tinylog-api-2.7.0.jar:lib/tinylog-impl-2.7.0.jar:src/main:."

echo "Compilando classes principais..."

# aqui tinha um erro:
# tinha que adicionar "-d ." para gerar os .class na raiz
javac -cp "$CLASSPATH" -d . src/main/*.java src/main/ui/*.java src/main/models/*.java src/main/service/*.java

echo "Executando aplicacao..."
java -Dtinylog.configuration="$LOG_CONFIG" -cp "$CLASSPATH" main.Main