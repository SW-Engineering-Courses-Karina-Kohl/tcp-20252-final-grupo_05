package test;

import main.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class SerieTest {

    private Arigo arigo;
    private Critico critico;

    public static final int PESO_AVALIACAO_ARIGO = 1;
    public static final int PESO_AVALIACAO_CRITICO = 2;

    @BeforeEach
    void setup() {
        arigo = new Arigo("João", LocalDate.of(1995, 1, 1), "j@j.com");
        critico = new Critico("Maria", LocalDate.of(1990, 5, 20), "m@m.com");
    }

    @Test
    void calculaMediaPonderadaSerieComTemporadaEpisodio() {
        Serie serie = new Serie("SerieTeste", LocalDate.now());

        Temporada t1 = new Temporada();
        Episodio e1 = new Episodio("Ep1", 45);
        Episodio e2 = new Episodio("Ep2", 50);

        t1.adicionarEpisodio(e1);
        t1.adicionarEpisodio(e2);
        serie.adicionarTemporada(t1);

        arigo.avaliar(serie, 3, "Razoável");
        critico.avaliar(serie, 4, "Bom");

        double somaPesos = PESO_AVALIACAO_ARIGO + PESO_AVALIACAO_CRITICO;
        double esperado = (3 * PESO_AVALIACAO_ARIGO + 4 * PESO_AVALIACAO_CRITICO) / somaPesos;
        assertEquals(esperado, serie.calcularMediaPonderada(), 0.0001);

        assertEquals(1, serie.getTotalTemporadas());
        assertEquals(2, serie.getTemporadas().get(0).getTotalEpisodios());
    }

    @Test
    void construtorLancaQuandoCamposNulos() {
        assertThrows(IllegalArgumentException.class, () -> new Serie(null, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Serie("T", null));
    }

    @Test
    void adicionarTemporadaNulaLanca() {
        Serie serie = new Serie("S", LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> serie.adicionarTemporada(null));
    }
}
