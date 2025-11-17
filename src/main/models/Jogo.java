package main.models;

import java.time.LocalDate;
import java.util.List;

public class Jogo extends Conteudo {

    private final String genero;
    private final String desenvolvedora;
    private List<String> plataformas;

    public Jogo(String titulo, LocalDate dataLanc, String genero, String desenvolvedora) {
        super(titulo, dataLanc);
        this.genero = genero;
        this.desenvolvedora = desenvolvedora;
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
