package main.models;

import java.time.LocalDate;
import java.util.UUID;

public class Filme extends Conteudo {

    private final int duracaoMinutos;
    private final String diretor;

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     */
    public Filme(UUID id, String titulo, LocalDate dataLanc, int duracao, String diretor) {
        super(id, titulo, dataLanc);
        this.duracaoMinutos = duracao;
        this.diretor = diretor;
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Filme(String titulo, LocalDate dataLanc, int duracao, String diretor) {
        super(titulo, dataLanc);
        this.duracaoMinutos = duracao;
        this.diretor = diretor;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public String getDiretor() {
        return diretor;
    }
}
