package test;

import main.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

public class FilmeTest {

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
    void calculaMediaPonderadaCorreta() {
        Filme filme = new Filme("Teste", LocalDate.now(), 120, "Diretor");
        arigo.avaliar(filme, 4, "Bom");
        critico.avaliar(filme, 5, "Excelente");

        double somaPesos = PESO_AVALIACAO_ARIGO + PESO_AVALIACAO_CRITICO;
        double esperado = (4 * PESO_AVALIACAO_ARIGO + 5 * PESO_AVALIACAO_CRITICO) / somaPesos;
        assertEquals(esperado, filme.calcularMediaPonderada(), 0.0001);
    }

    @Test
    void retornaZeroQuandoNaoHaAvaliacoes() {
        Filme filme = new Filme("Sem Avaliações", LocalDate.now(), 90, "Diretor");
        assertEquals(0.0, filme.calcularMediaPonderada(), 0.0001);
    }

    @Test
    void construtorLancaQuandoTituloNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Filme(null, LocalDate.now(), 120, "Diretor"));
    }

    @Test
    void construtorLancaQuandoDataLancNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new Filme("Titulo", null, 120, "Diretor"));
    }
}
