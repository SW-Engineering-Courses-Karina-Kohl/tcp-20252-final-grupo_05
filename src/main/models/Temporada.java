package main.models;

import java.util.ArrayList;
import java.util.List;

public class Temporada {

    private final List<Episodio> episodios;

    public Temporada() {
        this.episodios = new ArrayList<>();
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public int getTotalEpisodios() {
        return episodios.size();
    }

    public void adicionarEpisodio(Episodio episodio) {
        episodios.add(episodio);
    }
}
