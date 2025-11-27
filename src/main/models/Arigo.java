package main.models;

import java.time.LocalDate;

public class Arigo extends Pessoa implements Avaliador {

    private static final int PESO_PADRAO = 1;
    
    public Arigo(String nome, LocalDate dataNasc, String email){
        super(nome, dataNasc, email);
    }

    @Override
    public  Avaliacao avaliar(Conteudo conteudo, int nota, String comentario) {

        if(nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 5");
        }

        Avaliacao novaAvaliacao = new Avaliacao(nota, PESO_PADRAO, comentario, this.getId());

        conteudo.adicionarAvaliacao(novaAvaliacao);

        this.registrarAvaliacao(novaAvaliacao);

        return novaAvaliacao;
    }
}
