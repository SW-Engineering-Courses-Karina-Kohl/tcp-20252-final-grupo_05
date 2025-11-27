package test;

import main.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpisodioTest {

    @Test
    void construtorLancaQuandoTituloNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Episodio(null, 30));
    }

    @Test
    void construtorLancaQuandoDuracaoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Episodio("Ep", 0));
        assertThrows(IllegalArgumentException.class, () -> new Episodio("Ep", -5));
    }

    @Test
    void gettersRetornamValores() {
        Episodio e = new Episodio("Ep1", 42);
        assertEquals("Ep1", e.getTitulo());
        assertEquals(42, e.getDuracaoMinutos());
    }
}
