package main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Temporada implements Serializable {
    private static final long serialVersionUID = 1L;

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

        if (episodio == null){
            throw new IllegalArgumentException("Erro na adição de episódio: valor nulo fornecido.");
        } 
        
        episodios.add(episodio);
    }
}
