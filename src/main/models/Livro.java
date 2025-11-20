package main.models;

import java.time.LocalDate;

public class Livro extends Conteudo {

    private final String autor;
    private final String editora;
    private final int numeroPaginas;

    public Livro(String titulo, LocalDate dataLanc, String autor, String editora, int numeroPaginas) {
        super(titulo, dataLanc);

        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Erro na adição de autor: valor nulo fornecido.");
        }

        if (editora == null || editora.isBlank()) {
            throw new IllegalArgumentException("Erro na adição de editora: valor nulo fornecido.");
        }

        if (numeroPaginas <=0) {
            throw new IllegalArgumentException("Erro na adição de Número de páginas: valor menor ou igual a zero fornecido.");
        }

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
