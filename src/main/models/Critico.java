package main.models;

import java.time.LocalDate;
import java.util.UUID;

public class Critico extends Pessoa implements Avaliador {

    /*
    * constante peso em avaliacoes 
    * atende ao RF-5
    */
    private static final int PESO_AVALIACAO = 2; 

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     * Define senha padrão se não fornecida.
     */
    public Critico(UUID id, String nome, LocalDate dataNasc, String email) {
        super(id, nome, dataNasc, email);
    }

    /**
     * Construtor com ID e data de criação. Define senha padrão.
     */
    public Critico(UUID id, LocalDate dataCriacao, String nome, LocalDate dataNasc, String email) {
        super(id, dataCriacao, nome, dataNasc, email, Pessoa.SENHA_PADRAO);
    }

    /**
     * Construtor com ID e senha.
     */
    public Critico(UUID id, String nome, LocalDate dataNasc, String email, String senha) {
        super(id, nome, dataNasc, email, senha);
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     * Define senha padrão se não fornecida.
     */
    public Critico(String nome, LocalDate dataNasc, String email) {
        super(nome, dataNasc, email);
    }

    /**
     * Construtor sem ID mas com senha.
     */
    public Critico(String nome, LocalDate dataNasc, String email, String senha) {
        super(nome, dataNasc, email, senha);
    }

    /*
    * cria uma avaliação com peso personalizado e a adiciona ao conteúdo.
    */
    @Override
    public Avaliacao avaliar(Conteudo conteudo, int nota, String comentario) {
       
        if(nota < 1 || nota > 5){
            throw new  IllegalArgumentException("A nota deve ser entre 1 e 5");
        }
       
        Avaliacao novaAvaliacao = new Avaliacao(nota, PESO_AVALIACAO, comentario, this.getId());
        
        conteudo.adicionarAvaliacao(novaAvaliacao);

        this.registrarAvaliacao(novaAvaliacao);

        return novaAvaliacao;
    }
}
