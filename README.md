[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/9TN0gSSC)

# TCP-20252-final-grupo-05 (ArigóFlix)

Projeto final da disciplina de **INF01120 - Técnicas de Construção de Programas I**. O projeto é uma plataforma de avaliação de conteúdo chamada "ArigóFlix".

---

## 👥 Integrantes (Grupo 05)

* **@Artur** - Artur Webber de Oliveira
* **@Bruna** - Bruna Rosa Bragança de Lima
* **@Elano** - Elano Tavares do Nascimento
* **@Joao** - João Victor Serpa

---

## 📁 Estrutura do Projeto

A estrutura do projeto segue as convenções do Java, separando as responsabilidades em pacotes (`models`, `services`, `ui`) para garantir a modularidade e manutenibilidade.

```bash
TCP-20252-final-grupo-05
    │
    ├── config                   <-- Arquivos de configuração (ex: tinylog.properties)
    ├── lib                      <-- Dependências externas (Tinylog, JUnit)
    ├── scripts                  <-- Scripts de execução automatizada (Windows/Linux)
    │
    └── src
        │
        ├───── main  <-- Pacote principal
        │       │
        │       ├── Main.java    <-- Classe principal que inicia o programa
        │       │
        │       ├── models               <-- (Classes que representam dados/entidades)
        │       │   ├── Pessoa.java      (abstrata)
        │       │   ├── Critico.java
        │       │   ├── Arigo.java
        │       │   ├── Avaliador.java   (interface)
        │       │   ├── Conteudo.java    (abstrata)
        │       │   ├── Filme.java
        │       │   ├── Serie.java
        │       │   ├── Livro.java
        │       │   ├── Jogo.java
        │       │   ├── Avaliacao.java
        │       │   ├── Temporada.java
        │       │   └── Episodio.java
        │       │
        │       ├── services             <-- (Classes com lógica de negócio)
        │       │   └── ServicoPromocao.java
        │       │
        │       └── ui (ou view)         <-- (Classes da Interface com o Usuário)
        │           ├── TelaLogin.java   
        │           ├── TelaInicial.java
        │           └── TelaDetalhes.java
        │
        ├───── resources
        │       └── data                 <-- (Arquivos de persistência de dados)    
        │
        └───── test                       <-- (Testes unitários)
```

## 🚀 Como Compilar e Executar

### Compilação

Este projeto utiliza scripts automatizados localizados na pasta scripts/. Eles gerenciam automaticamente:

1. A inclusão das bibliotecas (lib/) no Classpath.

2. A cópia temporária dos arquivos de dados (.csv) para que o programa consiga lê-los.

3. A compilação em uma pasta temporária (bin/) para manter a raiz do projeto limpa.

4. A limpeza dos arquivos temporários após a execução.

### No Windows
Abra o terminal (CMD ou PowerShell) na raiz do projeto e execute:

**Para rodar a Aplicação:**

```bash
.\scripts\run-windows.bat
```

**Para rodar os Testes (JUnit):**

```bash
.\scripts\test-windows.bat
```

### No Linux / macOS
Primeiro, dê permissão de execução aos scripts (necessário apenas na primeira vez):

```bash
chmod +x scripts/run-macos-linux.sh
chmod +x scripts/test-macos-linux.sh
```

**Para rodar a Aplicação:**

```bash
./scripts/run-macos-linux.sh
```

**Para rodar os Testes (JUnit):**

```bash
./scripts/test-macos-linux.sh
```

## 🔄 Fluxo de Trabalho

### Antes de Começar a Trabalhar

1. **Faça um pull** para garantir que está com a versão mais recente:
   ```bash
   git pull origin develop
   ```

2. **Faça merge de develop** para a branch que estiver trabalhando:
   ```bash
   git checkout sua-branch
   git merge develop
   ```

### Desenvolvimento

1. **Crie uma branch separada** para sua feature/correção:
   ```bash
   git checkout -b nome-da-sua-feature
   ```
   Ou, se já estiver em uma branch:
   ```bash
   git checkout sua-branch
   ```

2. **Desenvolva** suas alterações normalmente, fazendo commits frequentes e descritivos:
   ```bash
   git add .
   git commit -m "Descrição clara do que foi feito"
   ```

### Ao Finalizar o Trabalho

1. **Certifique-se** de que está tudo commitado:
   ```bash
   git status
   ```

2. **Faça push** da sua branch:
   ```bash
   git push origin nome-da-sua-feature
   ```

3. **Abra um Pull Request (PR)** para a branch `develop` no GitHub
   - Descreva claramente o que foi implementado
   - Referencie issues relacionadas, se houver
   - Aguarde revisão antes de fazer merge



## Logs do Sistema

- **Nome e versão da biblioteca:**  
  tinylog 2.7.0 (`tinylog-api` e `tinylog-impl`).

- **Link da documentação oficial:**  
  https://tinylog.org/v2/

- **Exemplos de uso no código**

  O código utiliza chamadas como `Logger.info(...)`, `Logger.warn(...)` e `Logger.error(e, ...)` em classes de domínio, serviços e telas. Alguns exemplos reais:

  - Exemplo de `INFO` (classe `ServicoPromocao`):  
    `Logger.info("Iniciando tentativa de promoção para o usuário com ID {}.", arigo.getId());`

  - Exemplo de `ERROR` (classe `Conteudo`):  
    `Logger.error("Título inválido ao criar conteúdo: '{}'.", titulo);`

  Esses logs registram tanto o fluxo normal da aplicação quanto situações de erro que ajudam na depuração e na manutenção.

- **Propósito do logging no sistema:**  
  O logging foi adicionado para tornar o comportamento da aplicação mais observável: registrar eventos relevantes, detectar rapidamente problemas de dados ou configuração e apoiar a depuração durante desenvolvimento e testes, sem depender apenas da interface gráfica ou de mensagens de exceção.
