[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/9TN0gSSC)
# TCP-20252-final-grupo-05
@Artur
@Bruna
@Elano
@Joao

# Estrutura do Projeto

TCP-20252-final-grupo-05
    │
    └── src
        │
        ├───── main  <-- Pacote principal
        │       │
        │       ├── ArigoFlixApp.java   <-- Classe principal (Main) que inicia o programa
        │       │
        │       ├── models              <-- (Classes que representam dados)
        │       │   ├── Pessoa.java
        │       │   ├── Critico.java
        │       │   ├── Arigo.java
        │       │   ├── Avaliador.java
        │       │   ├── Conteudo.java
        │       │   ├── Filme.java
        │       │   ├── Serie.java
        │       │   └── Avaliacao.java
        │       │
        │       ├── services            <-- Classes com lógica de negócio
        │       │   └── ServicoPromocao.java
        │       │
        │       └── ui (ou view)        <-- Para as classes da Interface com o Usuário
        │           ├── TelaLogin.java   
        │           ├── TelaInicial.java
        │           └── TelaDetalhes.java
        │
        ├───── resources
        │       └── data
        │           └── conteudos.csv       <-- (Exemplo: para carregar dados do RF-2)
        │
        └───── test
                └── PessoaTest.java
        