package main.models;

import java.time.LocalDate;

public class Filme extends Conteudo {

    private final int duracaoMinutos;
    private final String diretor;

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
