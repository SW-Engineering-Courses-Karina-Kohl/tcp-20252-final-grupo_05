package test;

import main.models.*;
import main.service.Context;
import main.service.ServicoPromocao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoPromocaoTest {

    private Context context;
    private ServicoPromocao servicoPromocao;
    private Arigo arigo;
    private Filme filme;

    @BeforeEach
    void setup() {
        // Setup do ambiente isolado (sem carregar do disco)
        context = new Context();
        servicoPromocao = new ServicoPromocao();

        // Criar usuário e conteúdo
        arigo = new Arigo("João Arigó", LocalDate.now(), "joao@email.com");
        filme = new Filme("Filme Teste", LocalDate.now(), 120, "Diretor");

        // Adicionar ao contexto (banco de dados em memória)
        context.pessoas.add(arigo);
        context.filmes.add(filme);
    }

    @Test
    void naoPromoveComMenosDeDezLikes() {
        // Cenário: Usuário faz uma avaliação e ganha 9 likes
        Avaliacao avaliacao = arigo.avaliar(filme, 5, "Bom filme");
        for (int i = 0; i < 9; i++) {
            avaliacao.adicionarLike();
        }

        servicoPromocao.tentarPromover(context, arigo.getId());

        // Deve continuar sendo Arigó
        Pessoa usuarioPosVerificacao = context.pessoas.getById(arigo.getId());
        assertTrue(usuarioPosVerificacao instanceof Arigo, "Usuário deveria continuar sendo Arigó com 9 likes");
    }

    @Test
    void promoveParaCriticoComDezLikes() {
        // Cenário: Usuário faz uma avaliação e ganha 10 likes
        Avaliacao avaliacao = arigo.avaliar(filme, 5, "Ótimo filme");
        for (int i = 0; i < 10; i++) {
            avaliacao.adicionarLike();
        }

        servicoPromocao.tentarPromover(context, arigo.getId());

        // Deve ter se tornado Crítico
        Pessoa usuarioPromovido = context.pessoas.getById(arigo.getId());
        
        //verifica se mudou de classe
        assertTrue(usuarioPromovido instanceof Critico, "Usuário deveria ter virado Crítico");
        
        // Verifica se manteve os dados antigos
        assertEquals(arigo.getNome(), usuarioPromovido.getNome());
        assertEquals(arigo.getEmail(), usuarioPromovido.getEmail());
        assertEquals(arigo.getId(), usuarioPromovido.getId());
        
        //verifica se as avaliações foram migradas
        assertEquals(1, usuarioPromovido.getListaAvaliacoes().size());
    }
}