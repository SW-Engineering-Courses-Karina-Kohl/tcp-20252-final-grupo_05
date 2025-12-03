package test;

import main.models.Arigo;
import main.models.Critico;
import main.models.Pessoa;
import main.service.Context;
import main.service.autenticacao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoAutenticacaoTest {

    private Context context;
    private ServicoAutenticacao servicoAuth;

    @BeforeEach
    void setup() {
        context = new Context(); // Repositório vazio em memória
        servicoAuth = new ServicoAutenticacao(context);
    }

    @Test
    void deveRegistrarNovoUsuarioComSucesso() throws EmailJaCadastradoException {
        servicoAuth.registrar("João", "joao@email.com", "senha123");

        Pessoa usuario = context.pessoas.getById(servicoAuth.getPessoaAutenticada().getId());
        assertNotNull(usuario);
        assertEquals("João", usuario.getNome());
        assertTrue(usuario instanceof Arigo, "Por padrão deve ser Arigó");
    }

    @Test
    void deveRegistrarCriticoQuandoSolicitado() throws EmailJaCadastradoException {
        servicoAuth.registrar("Maria", "maria@email.com", "senha123", "Crítico");

        Pessoa usuario = context.pessoas.getById(servicoAuth.getPessoaAutenticada().getId());
        assertTrue(usuario instanceof Critico, "Deveria ter criado um Crítico");
    }

    @Test
    void naoDevePermitirEmailDuplicado() throws EmailJaCadastradoException {
        // Primeiro registro
        servicoAuth.registrar("João", "teste@email.com", "123");

        // Tentativa de segundo registro com mesmo email
        assertThrows(EmailJaCadastradoException.class, () -> {
            servicoAuth.registrar("Pedro", "teste@email.com", "456");
        });
    }

    @Test
    void autenticacaoComSucesso() throws EmailJaCadastradoException, CredenciaisInvalidasException {
        servicoAuth.registrar("LoginUser", "login@email.com", "senhaSecret");
        
        // Simula logout
        servicoAuth.sair();
        assertFalse(servicoAuth.estaAutenticado());

        // Login
        servicoAuth.autenticar("login@email.com", "senhaSecret");
        assertTrue(servicoAuth.estaAutenticado());
        assertEquals("LoginUser", servicoAuth.getPessoaAutenticada().getNome());
    }

    @Test
    void autenticacaoFalhaComSenhaErrada() throws EmailJaCadastradoException {
        servicoAuth.registrar("User", "user@email.com", "senhaCerta");
        servicoAuth.sair();

        assertThrows(CredenciaisInvalidasException.class, () -> {
            servicoAuth.autenticar("user@email.com", "senhaErrada");
        });
    }
}