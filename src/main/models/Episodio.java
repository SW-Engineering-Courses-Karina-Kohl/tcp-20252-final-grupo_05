package main.models;

public class Episodio {

    private final String titulo;
    private final int duracaoMinutos;

    public Episodio(String titulo, int duracaoMinutos) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título de episódio inválido.");
        }
        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Duração deve ser maior que zero.");
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
