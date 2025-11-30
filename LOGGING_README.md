## Logging com tinylog

Este documento descreve como o logging foi implementado no projeto usando a biblioteca **tinylog 2.x**, bem como como rodar a aplicação apenas com Java.

### Biblioteca escolhida

- **Biblioteca**: `tinylog` 2.7.0 (`tinylog-api` e `tinylog-impl`).
- **Motivos da escolha**:
	- Configuração simples, sem necessidade de arquivos XML complexos.
	- API direta (`org.tinylog.Logger.info/warn/error/debug`).
	- Adequada para um projeto que é mais demonstrativo do que real.

### Integração e configuração

- Os JARs necessários estão versionados no repositório em `lib/`:
	- `lib/tinylog-api-2.7.0.jar`
	- `lib/tinylog-impl-2.7.0.jar`
- O arquivo de configuração do tinylog está na raiz do projeto: `tinylog.properties`.
- Configuração atual (`tinylog.properties`):

- Com essa configuração:
	- Todos os logs são enviados para o **console**.
	- Uma cópia idêntica é gravada no arquivo `logs/app.log`.

### Níveis de log utilizados

- `INFO`: eventos normais e importantes
	- Inicialização e carregamento de interface (`main.Main`).
	- Ações de usuário relevantes (login, cadastro) nas telas.
	- Avaliações válidas (`Arigo`) e promoções bem-sucedidas (`ServicoPromocao`).
- `WARN`: situações anormais, porém esperadas
	- Tentativas de avaliação com nota inválida.
	- Usuário que não atinge o limite mínimo de likes para promoção.
- `ERROR`: erros mais graves ou estados inválidos
	- Criação de `Conteudo` com dados inválidos (título nulo, data nula).
	- Soma de pesos zero ao calcular média ponderada.
	- Falha crítica ao exibir a interface inicial (capturada em `Main`).
- `DEBUG`: informações de navegação entre telas (úteis para depuração).

### Como rodar o projeto com logs habilitados

Pré-requisito: ter **Java (JDK)** instalado e disponível no `PATH`.

#### Windows (PowerShell ou prompt)

Você pode usar o script `run-windows.bat` na raiz do projeto, ou executar os comandos manualmente.

**Usando o script**:

```bat
run-windows.bat
```

**Comandos equivalentes**:

```powershell
cd tcp-20252-final-grupo_05

javac -cp "lib\tinylog-api-2.7.0.jar;lib\tinylog-impl-2.7.0.jar;src\main" src\main\main\Main.java

java -cp "lib\tinylog-api-2.7.0.jar;lib\tinylog-impl-2.7.0.jar;src\main;." main.Main
```

#### macOS / Linux (bash/zsh)

Você pode usar o script `run-macos-linux.sh` ou executar os comandos manualmente.

**Usando o script**:

```bash
chmod +x run-macos-linux.sh
./run-macos-linux.sh
```

**Comandos equivalentes**:

```bash
cd tcp-20252-final-grupo_05

javac -cp "lib/tinylog-api-2.7.0.jar:lib/tinylog-impl-2.7.0.jar:src/main" src/main/main/Main.java

java -cp "lib/tinylog-api-2.7.0.jar:lib/tinylog-impl-2.7.0.jar:src/main:." main.Main
```