package main.models;

import java.time.LocalDate;
import java.util.UUID;
import org.tinylog.Logger;

public class Arigo extends Pessoa implements Avaliador {

    private static final int PESO_AVALIACAO = 1;
    
    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     */
    public Arigo(UUID id, String nome, LocalDate dataNasc, String email){
        super(id, nome, dataNasc, email);
    }
    
    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Arigo(String nome, LocalDate dataNasc, String email){
        super(nome, dataNasc, email);
    }

    @Override
    public Avaliacao avaliar(Conteudo conteudo, int nota, String comentario) {

        if (nota < 1 || nota > 5) {
            Logger.warn("Usuário {} tentou avaliar conteúdo {} com nota inválida: {}.", this.getId(),
                    conteudo != null ? conteudo.getTitulo() : "<conteúdo nulo>", nota);
            throw new IllegalArgumentException("A nota deve ser entre 1 e 5");
        }

        Avaliacao novaAvaliacao = new Avaliacao(nota, PESO_AVALIACAO, comentario, this.getId());

        Logger.info("Usuário {} avaliou conteúdo {} com nota {}.", this.getId(), conteudo.getTitulo(), nota);

        conteudo.adicionarAvaliacao(novaAvaliacao);
        this.registrarAvaliacao(novaAvaliacao);

        return novaAvaliacao;
    }
}
