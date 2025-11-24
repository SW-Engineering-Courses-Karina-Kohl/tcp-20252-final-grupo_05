package main.models;

import java.time.LocalDate;

public class Arigo extends Pessoa implements Avaliador {

    private static final int PESO_AVALIACAO = 1;
    
    public Arigo(String nome, LocalDate dataNasc, String email){
        super(nome, dataNasc, email);
    }

    @Override
    public Avaliacao avaliar(Conteudo conteudo, int nota, String comentario) {
        Avaliacao novaAvaliacao =new Avaliacao(nota, PESO_AVALIACAO, comentario, this.getId());

        conteudo.adicionarAvaliacao(novaAvaliacao);

        return novaAvaliacao;
    }
}