package main.models;

import java.util.UUID;

public class Avaliacao extends ContextEntity {
    private static final long serialVersionUID = 1L;

    private int nota; 
    private int peso;
    private String comentario;
    private UUID idUsuario;
    private int likes;

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     */
    public Avaliacao(UUID id, int nota, int peso, String comentario, UUID idUsuario) {
        super(id);

        if (peso <= 0) {
            throw new IllegalArgumentException("O peso da avaliação deve ser maior que zero.");
        }

        this.nota = nota;
        this.peso = peso;
        this.comentario = comentario;
        this.idUsuario = idUsuario;
        this.likes = 0;

    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Avaliacao(int nota, int peso, String comentario, UUID idUsuario) {
        this(null, nota, peso, comentario, idUsuario);
    }

    public void adicionarLike() {
        this.likes++;
    }

    /* 
    * Getters
    */

    public int getLikes() {
        return this.likes;
    }

    public int getNota() {
        return nota;
    }

    public int getPeso() {
        return peso;
    }

    public String getComentario() {
        return comentario;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    /* 
    * Setters
    */

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

}
