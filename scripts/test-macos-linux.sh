#!/usr/bin/env bash
set -e

# garante que estamos na raiz do projeto
cd "$(dirname "$0")/.."

# --- FUNÇÃO DE LIMPEZA ---
function cleanup {
    echo ""
    echo "[Limpeza] Removendo arquivos temporarios..."
    rm -rf bin
    ls *.csv >/dev/null 2>&1 && rm *.csv
}
trap cleanup EXIT

# configurações
LOG_CONFIG="config/tinylog.properties"

CLASSPATH="lib/junit-platform-console-standalone-1.10.2.jar:lib/tinylog-api-2.7.0.jar:lib/tinylog-impl-2.7.0.jar:src/resources/data:src/resources:src/main:src/test:bin"

echo ""
echo "[1/4] Preparando Ambiente..."
mkdir -p bin

if [ -d "src/resources/data" ]; then
    cp src/resources/data/*.csv . 2>/dev/null || true
    echo "Arquivos CSV copiados."
else
    echo "[AVISO] Pasta src/resources/data nao encontrada."
fi

echo ""
echo "[2/4] Compilando Codigo Fonte..."
javac -cp "$CLASSPATH" -d bin src/main/*.java src/main/ui/*.java src/main/models/*.java src/main/service/*.java

echo ""
echo "[3/4] Compilando Testes..."
if [ -d "src/test" ]; then
    javac -cp "$CLASSPATH" -d bin src/test/*.java
else
    echo "[ERRO] Pasta src/test nao encontrada."
    exit 1
fi

echo ""
echo "[4/4] Executando JUnit..."

java -Dtinylog.configuration="$LOG_CONFIG" -jar lib/junit-platform-console-standalone-1.10.2.jar execute --scan-classpath --classpath "$CLASSPATH" --details=tree

# a funcao cleanup roda automaticamente aqui