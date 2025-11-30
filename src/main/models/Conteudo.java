package main.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.tinylog.Logger;

public abstract class Conteudo extends ContextEntity {
    private static final long serialVersionUID = 1L;

    private String titulo;
    private LocalDate dataLanc;
    private List<Avaliacao> avaliacoes;

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     */
    public Conteudo(UUID id, String titulo, LocalDate dataLanc) {
        super(id);

        if (titulo == null || titulo.isBlank()) {
            Logger.error("Falha ao criar Conteudo: título inválido (nulo ou em branco).");
            throw new IllegalArgumentException("Título não pode ser nulo");
        }

        if (dataLanc == null) {
            Logger.error("Falha ao criar Conteudo '{}': data de lançamento nula.", titulo);
            throw new IllegalArgumentException("Data de lançamento não pode ser nula");
        }

        this.titulo = titulo;
        this.dataLanc = dataLanc;
        this.avaliacoes = new ArrayList<>();
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Conteudo(String titulo, LocalDate dataLanc) {
        this(null, titulo, dataLanc);
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

    /**
     * Calcula a média simples das notas das avaliações.
     * 
     * @return A média das notas (0.0 se não houver avaliações)
     */
    public double calcularMedia() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        double somaNotas = 0.0;
        for (Avaliacao aval : avaliacoes) {
            somaNotas += aval.getNota();
        }

        return somaNotas / avaliacoes.size();
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
            Logger.error("Erro ao calcular média ponderada para o conteúdo '{}': soma de pesos igual a zero.",
                    this.titulo);
            throw new IllegalStateException("Não é possível calcular a média: soma de pesos igual a zero.");
        }

        double mediaPonderada = somaNotasPonderadas / somaPesos;

        return mediaPonderada;
    }


}
