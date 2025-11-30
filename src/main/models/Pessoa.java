package main.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public abstract class Pessoa {

    private UUID id;
    private String nome;
    private LocalDate dataNasc;
    private String email;

    private List<Avaliacao> listaAvaliacoes;

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     */
    public Pessoa(UUID id, String nome, LocalDate dataNasc, String email){
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.email = email;
        
        this.id = (id != null) ? id : UUID.randomUUID();
        this.listaAvaliacoes = new ArrayList<>();
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Pessoa(String nome, LocalDate dataNasc, String email){
        this(null, nome, dataNasc, email);
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
  
    protected void registrarAvaliacao(Avaliacao avaliacao) {
        this.listaAvaliacoes.add(avaliacao);
    }

    public List<Avaliacao> getListaAvaliacoes() {
        return listaAvaliacoes;
    }
}
