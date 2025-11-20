package main.models;

public class Episodio {

    private final String titulo;
    private final int duracaoMinutos;

    public Episodio(String titulo, int duracaoMinutos) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Erro na adição de título do episódio: valor nulo fornecido.");
        }
        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Erro na adição de duração do episódio: valor menor ou igual a zero fornecido.");
        }

        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }
}
