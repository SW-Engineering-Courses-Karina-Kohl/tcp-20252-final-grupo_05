package main.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Conteudo {

    private String titulo;
    private LocalDate dataLanc;
    private List<Avaliacao> avaliacoes;

    public Conteudo(String titulo, LocalDate dataLanc) {
        this.titulo = titulo;
        this.dataLanc = dataLanc;
        this.avaliacoes = new ArrayList<>();
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
    }

    /* 
    * Getters
    */

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
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

    /**
    * método (exemplo) 
    * Requisito Funcional RF-5.
    * @return média ponderada das notas.
    
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

        return somaNotasPonderadas / somaPesos;
    }
    */

}
