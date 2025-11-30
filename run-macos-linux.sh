#!/usr/bin/env bash
# Script para compilar e executar a aplicação com tinylog em macOS/Linux

set -e

CLASSPATH="lib/tinylog-api-2.7.0.jar:lib/tinylog-impl-2.7.0.jar:src/main:."

echo "Compilando classes principais..."
javac -cp "$CLASSPATH" src/main/main/Main.java

echo "Executando aplicação..."
java -cp "$CLASSPATH" main.Main

