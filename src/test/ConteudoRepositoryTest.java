package test;

import main.models.Filme;
import main.service.ConteudoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConteudoRepositoryTest {

    private ConteudoRepository<Filme> repository;

    @BeforeEach
    void setup() {
        // Cria uma lista de filmes para testar o filtro
        List<Filme> filmesIniciais = new ArrayList<>();
        filmesIniciais.add(new Filme("Matrix", LocalDate.now(), 136, "Wachowskis"));
        filmesIniciais.add(new Filme("Matrix Reloaded", LocalDate.now(), 138, "Wachowskis"));
        filmesIniciais.add(new Filme("O Poderoso Chefão", LocalDate.now(), 175, "Coppola"));
        filmesIniciais.add(new Filme("Barbie", LocalDate.now(), 114, "Gerwig"));

        repository = new ConteudoRepository<>(filmesIniciais);
    }

    @Test
    void buscaRetornaTodosSeTermoVazio() {
        List<Filme> resultado = repository.findAll("");
        assertEquals(4, resultado.size());
        
        resultado = repository.findAll(null);
        assertEquals(4, resultado.size());
    }

    @Test
    void buscaPorTermoExato() {
        List<Filme> resultado = repository.findAll("Barbie");
        assertEquals(1, resultado.size());
        assertEquals("Barbie", resultado.get(0).getTitulo());
    }

    @Test
    void buscaIgnoraMaiusculasEMinusculas() {
        // "mAtRiX" deve achar "Matrix" e "Matrix Reloaded"
        List<Filme> resultado = repository.findAll("mAtRiX");
        assertEquals(2, resultado.size());
    }

    @Test
    void buscaParcialEncontraResultados() {
        // "Chefão" deve achar "O Poderoso Chefão"
        List<Filme> resultado = repository.findAll("Chefão");
        assertEquals(1, resultado.size());
        assertEquals("O Poderoso Chefão", resultado.get(0).getTitulo());
    }
    
    @Test
    void buscaSemResultadosRetornaListaVazia() {
        List<Filme> resultado = repository.findAll("FilmeQueNaoExiste");
        assertTrue(resultado.isEmpty());
    }
}