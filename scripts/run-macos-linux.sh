#!/usr/bin/env bash
# script para compilar e executar a aplicacao no macOS/Linux
set -e

# garante que estamos na raiz do projeto
cd "$(dirname "$0")/.."

# --- FUNÇÃO DE LIMPEZA (roda automaticamente ao sair) ---
function cleanup {
    echo "" 
    echo "[Limpeza] Removendo arquivos temporarios (bin/ e csv)..."
    rm -rf bin
    ls *.csv >/dev/null 2>&1 && rm *.csv
}
# registra a funcao cleanup para rodar sempre que o script terminar
trap cleanup EXIT

# configurações
LOG_CONFIG="config/tinylog.properties"

CLASSPATH="lib/tinylog-api-2.7.0.jar:lib/tinylog-impl-2.7.0.jar:src/main:bin"

echo ""
echo "[1/3] Preparando Ambiente..."
mkdir -p bin

# copia CSVs se existirem
if [ -d "src/resources/data" ]; then
    cp src/resources/data/*.csv . 2>/dev/null || true
    echo "Arquivos CSV copiados para a raiz."
fi

echo ""
echo "[2/3] Compilando para pasta temporaria 'bin'..."
# -d bin joga os .class para dentro da pasta bin
javac -cp "$CLASSPATH" -d bin src/main/*.java src/main/ui/*.java src/main/models/*.java src/main/service/*.java

echo ""
echo "[3/3] Executando Aplicacao..."
java -Dtinylog.configuration="$LOG_CONFIG" -cp "$CLASSPATH" main.Main

# a funcao cleanup roda automaticamente aqui