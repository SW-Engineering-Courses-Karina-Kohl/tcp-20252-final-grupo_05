package main.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Serie extends Conteudo {

    private final List<Temporada> temporadas;

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     */
    public Serie(UUID id, String titulo, LocalDate dataLanc) {
        super(id, titulo, dataLanc);
        this.temporadas = new ArrayList<>();
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     */
    public Serie(String titulo, LocalDate dataLanc) {
        super(titulo, dataLanc);
        this.temporadas = new ArrayList<>();
    }

    public void adicionarTemporada(Temporada temporada) {

        if (temporada == null){
            throw new IllegalArgumentException("Erro na adição de temporada: valor nulo fornecido.");
        } 

        temporadas.add(temporada);
    }

    public int getTotalTemporadas() {
        return temporadas.size();
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }
}
