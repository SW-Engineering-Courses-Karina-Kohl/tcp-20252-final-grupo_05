package test;

import main.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class LivroTest {

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
    void calculaMediaPonderadaLivro() {
        Livro livro = new Livro("LivroTeste", LocalDate.now(), "Autor", "Editora", 200);
        arigo.avaliar(livro, 4, "Bom");
        critico.avaliar(livro, 5, "Ótimo");

        double somaPesos = PESO_AVALIACAO_ARIGO + PESO_AVALIACAO_CRITICO;
        double esperado = (4 * PESO_AVALIACAO_ARIGO + 5 * PESO_AVALIACAO_CRITICO) / somaPesos;
        assertEquals(esperado, livro.calcularMediaPonderada(), 0.0001);
    }

    @Test
    void construtorLancaQuandoCamposNulos() {
        assertThrows(IllegalArgumentException.class, () -> new Livro(null, LocalDate.now(), "A", "E", 10));
        assertThrows(IllegalArgumentException.class, () -> new Livro("T", null, "A", "E", 10));
    }
}
