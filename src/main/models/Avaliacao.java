package main.models;

import java.util.UUID;

public class Avaliacao {

    private int nota; 
    private int peso;
    private String comentario;
    private UUID idUsuario;
    private int likes;

    public Avaliacao(int nota, int peso, String comentario, UUID idUsuario) {
        this.nota = nota;
        this.peso = peso;
        this.comentario = comentario;
        this.idUsuario = idUsuario;
        this.likes = 0;
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
