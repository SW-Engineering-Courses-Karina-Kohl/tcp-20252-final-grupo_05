package main.models;

import java.time.LocalDate;
import java.util.UUID;

// Avaliacao e Conteudo ainda não criada, implementação baseada na UML
public class Critico extends Pessoa implements Avaliador {

    /*
    * constante peso em avaliacoes 
    * atende ao RF-5
    */
    private static final int PESO_AVALIACAO = 2; // ainda não decidido os valores de cada avaliacao

    public Critico(String nome, LocalDate dataNasc, String email) {
        super(nome, dataNasc, email);
    }

    /*
    * cria uma avaliação com peso personalizado e a adiciona ao conteúdo.
    */
    @Override
    public Avaliacao avaliar(Conteudo conteudo, int nota, String comentario) {
        Avaliacao novaAvaliacao = new Avaliacao(nota, PESO_AVALIACAO, comentario, this.getId());
        
        conteudo.adicionarAvaliacao(novaAvaliacao);

        return novaAvaliacao;
    }
}
