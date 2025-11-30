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
     */
    public Critico(UUID id, String nome, LocalDate dataNasc, String email) {
        super(id, nome, dataNasc, email);
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Critico(String nome, LocalDate dataNasc, String email) {
        super(nome, dataNasc, email);
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
