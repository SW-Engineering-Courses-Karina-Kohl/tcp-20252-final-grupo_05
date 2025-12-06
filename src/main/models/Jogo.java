package main.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Jogo extends Conteudo {

    private final String genero;
    private final String desenvolvedora;
    private List<String> plataformas;

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     */
    public Jogo(UUID id, String titulo, LocalDate dataLanc, String genero, String desenvolvedora) {
        super(id, titulo, dataLanc);
        
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("Gênero não pode ser vazio!");
        }

        if (desenvolvedora == null || desenvolvedora.isBlank()) {
            throw new IllegalArgumentException("Desenvolvedora não pode ser vazio!");
        }

        this.genero = genero;
        this.desenvolvedora = desenvolvedora;
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Jogo(String titulo, LocalDate dataLanc, String genero, String desenvolvedora) {
        this(null, titulo, dataLanc, genero, desenvolvedora);
    }

    public String getGenero() {
        return genero;
    }

    public String getDesenvolvedora() {
        return desenvolvedora;
    }

    public List<String> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<String> plataformas) {
        this.plataformas = plataformas;
    }
}
