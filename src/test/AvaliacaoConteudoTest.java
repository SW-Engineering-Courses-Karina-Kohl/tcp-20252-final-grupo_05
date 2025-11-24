package test;

import main.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

public class AvaliacaoConteudoTest {

    @Test
    void deveCalcularMediaPonderadaCorretamente() {
        Filme filme = new Filme("Teste", LocalDate.now(), 120, "Diretor");

        Arigo arigo = new Arigo("João", LocalDate.of(1995, 1, 1), "j@j.com");
        Critico critico = new Critico("Maria", LocalDate.of(1990, 5, 20), "m@m.com");

        arigo.avaliar(filme, 8, "Bom");

        critico.avaliar(filme, 6, "Regular");

        double esperado = (8 * 1 + 6 * 2) / 3.0;

        assertEquals(esperado, filme.calcularMediaPonderada(), 0.0001);
    }

    @Test
    void deveRetornarZeroQuandoNaoHaAvaliacoes() {
        Filme filme = new Filme("Sem Avaliações", LocalDate.now(), 90, "Diretor");
        assertEquals(0.0, filme.calcularMediaPonderada(), 0.0001);
    }

    @Test
    void deveLancarExcecaoQuandoPesoTotalForZero() {
        Filme filme = new Filme("Erro de Peso", LocalDate.now(), 100, "Diretor");

        // Avaliação com peso zero proposital
        Avaliacao invalida = new Avaliacao(10, 0, "Invalida", UUID.randomUUID());
        filme.adicionarAvaliacao(invalida);

        assertThrows(IllegalStateException.class, filme::calcularMediaPonderada);
    }

    @Test
    void deveLancarExcecaoSeTituloForNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Filme(null, LocalDate.now(), 120, "Diretor");
        });
    }

    @Test
    void deveLancarExcecaoSeDataLancForNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Filme("Titulo", null, 120, "Diretor");
        });
    }
}