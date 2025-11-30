package test;

import main.models.*;
import main.service.CarregadorDeDados;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CarregadorDeDadosTest {

    private List<Pessoa> usuarios;
    private List<Conteudo> catalogo;
    private Serie breakingBad;


    @BeforeEach
    void setup() {
        CarregadorDeDados carregador = new CarregadorDeDados();
        usuarios = carregador.carregarUsuarios();
        this.catalogo = carregador.carregarTodosConteudos();

        for (Conteudo conteudo : this.catalogo) {
            if (conteudo instanceof Serie) {
                if (conteudo.getTitulo().equals("Breaking Bad")) {
                    this.breakingBad = (Serie) conteudo;
                    break; 
                }
            }
        }
    }

    @Test
    void carregarArigoDoArquivo() {
        assertFalse(usuarios.isEmpty(), "A lista de usuários está vazia. ");

        boolean arigoEncontrado = false;

        for(Pessoa pessoa : usuarios) {
            if(pessoa instanceof Arigo) {
                arigoEncontrado = true;   
            }
        }

        assertTrue(arigoEncontrado, "Nenhum Arigo foi carregado do arquivo.");
    }

    @Test
    void carregarCriticoDoArquivo() {
        assertFalse(usuarios.isEmpty(), "A lista de usuários está vazia. ");

        boolean criticoEncontrado = false;

        for(Pessoa pessoa : usuarios) {
            if(pessoa instanceof Critico) {
                criticoEncontrado = true;   
            }
        }

        assertTrue(criticoEncontrado, "Nenhum Crítico foi carregado do arquivo.");
    }

    @Test
    void encontrarSeriePeloTitulo() {
    
        assertNotNull(this.breakingBad, "Série 'Breaking Bad' não foi carregada.");
    }

    @Test
    void deveCriarTemporadasCorretamente() {

        assertFalse(this.breakingBad.getTemporadas().isEmpty(), "A série 'Breaking Bad' não possui temporadas carregadas.");
        assertEquals(2, this.breakingBad.getTotalTemporadas(), "A série 'Breaking Bad' deveria ter temporadas.");
    }

    @Test
    void deveCarregarDadosDeEpisodios() {

        Temporada primeiraTemporada = breakingBad.getTemporadas().get(0);
        Episodio piloto = primeiraTemporada.getEpisodios().get(0);

        assertEquals("Piloto", piloto.getTitulo());
        assertEquals(58, piloto.getDuracaoMinutos());

    }
}