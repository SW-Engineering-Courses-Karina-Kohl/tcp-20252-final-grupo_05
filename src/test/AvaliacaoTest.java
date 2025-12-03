package test;

import main.models.Avaliacao;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class AvaliacaoTest {

    @Test
    void deveCriarAvaliacaoComDadosValidos() {
        UUID idUser = UUID.randomUUID();
        Avaliacao avaliacao = new Avaliacao(5, 2, "Ótimo", idUser);
        
        assertEquals(5, avaliacao.getNota());
        assertEquals(2, avaliacao.getPeso());
        assertEquals("Ótimo", avaliacao.getComentario());
        assertEquals(0, avaliacao.getLikes());
    }

    @Test
    void naoDevePermitirPesoZeroOuNegativo() {
        UUID idUser = UUID.randomUUID();
        
        // Teste Peso Zero
        assertThrows(IllegalArgumentException.class, () -> {
            new Avaliacao(5, 0, "Inválido", idUser);
        });

        // Teste Peso Negativo
        assertThrows(IllegalArgumentException.class, () -> {
            new Avaliacao(5, -1, "Inválido", idUser);
        });
    }

    @Test
    void deveIncrementarLikes() {
        Avaliacao avaliacao = new Avaliacao(5, 1, "Bom", UUID.randomUUID());
        assertEquals(0, avaliacao.getLikes());
        
        avaliacao.adicionarLike();
        assertEquals(1, avaliacao.getLikes());
        
        avaliacao.adicionarLike();
        assertEquals(2, avaliacao.getLikes());
    }
}