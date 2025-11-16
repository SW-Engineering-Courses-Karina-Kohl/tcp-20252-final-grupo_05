package main.models;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Pessoa {

    private UUID id;
    private String nome;
    private LocalDate dataNasc;
    private String email;

    public Pessoa(String nome, LocalDate dataNasc, String email){
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.email = email;

        this.id = UUID.randomUUID(); //gera id randomico para cada nova instancia
    }

    // GETTERS E SETTERS

     public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
  
}
