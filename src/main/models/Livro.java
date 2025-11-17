package main.models;

import java.time.LocalDate;

public class Livro extends Conteudo {

    private final String autor;
    private final String editora;
    private final int numeroPaginas;

    public Livro(String titulo, LocalDate dataLanc, String autor, String editora, int numeroPaginas) {
        super(titulo, dataLanc);
        this.autor = autor;
        this.editora = editora;
        this.numeroPaginas = numeroPaginas;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }
}
