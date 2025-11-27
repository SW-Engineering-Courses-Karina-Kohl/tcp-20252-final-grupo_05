package test;

import main.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TemporadaTest {

    @Test
    void adicionarEpisodioNuloLanca() {
        Temporada temporada = new Temporada();
        assertThrows(IllegalArgumentException.class, () -> temporada.adicionarEpisodio(null));
    }

    @Test
    void totalEpisodiosContador() {
        Temporada temporada = new Temporada();
        temporada.adicionarEpisodio(new Episodio("Ep1", 20));
        temporada.adicionarEpisodio(new Episodio("Ep2", 25));
        assertEquals(2, temporada.getTotalEpisodios());
    }
}
