package test;

import main.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class JogoTest {

    private Arigo arigo;
    private Critico critico;

    @BeforeEach
    void setup() {
        arigo = new Arigo("João", LocalDate.of(1995, 1, 1), "j@j.com");
        critico = new Critico("Maria", LocalDate.of(1990, 5, 20), "m@m.com");
    }

    @Test
    void calculaMediaPonderadaJogo() {
        Jogo jogo = new Jogo("JogoTeste", LocalDate.now(), "Ação", "DevCo");
        arigo.avaliar(jogo, 6, "Ok");
        critico.avaliar(jogo, 8, "Bom");

        double esperado = (6 * 1 + 8 * 2) / 3.0;
        assertEquals(esperado, jogo.calcularMediaPonderada(), 0.0001);
    }

    @Test
    void construtorLancaQuandoCamposNulos() {
        assertThrows(IllegalArgumentException.class, () -> new Jogo(null, LocalDate.now(), "G", "D"));
        assertThrows(IllegalArgumentException.class, () -> new Jogo("T", null, "G", "D"));
    }
}
