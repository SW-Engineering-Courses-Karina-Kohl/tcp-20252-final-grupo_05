package main.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Serie extends Conteudo {

    private final List<Temporada> temporadas;

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
