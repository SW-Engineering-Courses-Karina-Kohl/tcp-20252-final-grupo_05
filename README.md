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
    └── src
        │
        ├───── main  <-- Pacote principal
        │       │
        │       ├── ArigoFlixApp.java    <-- Classe principal (Main) que inicia o programa
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
        │       └── data
        │           └── conteudos.csv    <-- (Exemplo: para carregar dados do RF-2)
        │
        └───── test
                └── PessoaTest.java      <-- (Testes unitários)
```

## 🚀 Como Compilar e Executar

### Compilação

Para compilar o projeto, execute o seguinte comando na raiz do projeto:

```bash
javac -d build -sourcepath src src/main/**/*.java
```

### Execução

Após compilar, execute o projeto com:

```bash
java -cp build main.Main
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
