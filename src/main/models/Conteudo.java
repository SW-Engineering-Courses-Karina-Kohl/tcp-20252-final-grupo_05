package main.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Conteudo {

    private String titulo;
    private LocalDate dataLanc;
    private List<Avaliacao> avaliacoes;

    public Conteudo(String titulo, LocalDate dataLanc) {

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo");
        }

        if (dataLanc == null) {
            throw new IllegalArgumentException("Data de lançamento não pode ser nula");
        }

        this.titulo = titulo;
        this.dataLanc = dataLanc;
        this.avaliacoes = new ArrayList<>();
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao == null) {
            throw new IllegalArgumentException("Avaliação não pode ser nula");
        }

        this.avaliacoes.add(avaliacao);
    }

    /* 
    * Getters
    */

    public List<Avaliacao> getAvaliacoes() {
        return List.copyOf(this.avaliacoes);
    }

    public String getTitulo() {
        return titulo;
    }
    
    public LocalDate getDataLanc() {
        return dataLanc;
    }

    /* 
    * Setters
    */ 

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDataLanc(LocalDate dataLanc) {
        this.dataLanc = dataLanc;
    }

    public double calcularMediaPonderada() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        double somaNotasPonderadas = 0.0;
        int somaPesos = 0;

        for (Avaliacao aval : avaliacoes) {
            somaNotasPonderadas += aval.getNota() * aval.getPeso();
            somaPesos += aval.getPeso();
        }

        if (somaPesos == 0) {
            throw new IllegalStateException("Não é possível calcular a média: soma de pesos igual a zero.");
        }

        double mediaPonderada = somaNotasPonderadas / somaPesos;

        return mediaPonderada;
    }


}
