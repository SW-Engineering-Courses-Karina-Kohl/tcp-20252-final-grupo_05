package test;

import main.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PessoaTest {
    
    @Test
    public void naoPermitirNotaMaiorQueCinco() {

        Arigo usuario = new Arigo("Teste", LocalDate.now(), "teste@.com");
        Filme filme = new Filme("Filme Teste", LocalDate.now(), 120, "Diretor");
        
        assertThrows(IllegalArgumentException.class, () -> {
            usuario.avaliar(filme, 6, "Nota Maior que 5");
        });

    }

    @Test
    public void naoPermitirNotaMenorQueUm() {
        Arigo usuario = new Arigo("Teste", LocalDate.now(), "teste@.com");
        Filme filme = new Filme("Filme Teste", LocalDate.now(), 120, "Diretor");

        assertThrows(IllegalArgumentException.class, () -> {
            usuario.avaliar(filme, 0, "Nota Menor que 1");
        });
    }


    @Test
    public void verificaAvaliacaoFoiAdicionada() {

        Arigo usuario = new Arigo("Arigo", LocalDate.of(2000, 1, 1), "Arigo@.com");
        Filme filme = new Filme("Teste", LocalDate.of(2010, 2, 2), 90, "Diretor");

        Avaliacao avaliacao = usuario.avaliar(filme, 5, "Bom");

        assertTrue(filme.getAvaliacoes().contains(avaliacao));
    }
}
