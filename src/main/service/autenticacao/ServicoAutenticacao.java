package main.service.autenticacao;

import main.models.Arigo;
import main.models.Critico;
import main.models.Pessoa;
import main.service.Context;
import org.tinylog.Logger;

import java.time.LocalDate;

/**
 * Implementação do serviço de autenticação.
 * Gerencia o registro e autenticação de usuários no sistema.
 */
public class ServicoAutenticacao implements Autenticacao {
    private final Context context;
    private Pessoa pessoaAutenticada;

    /**
     * Construtor que recebe o contexto da aplicação.
     * 
     * @param context O contexto contendo os repositórios
     */
    public ServicoAutenticacao(Context context) {
        this(context, false);
    }

    /**
     * Construtor que recebe o contexto da aplicação e flag de debug.
     * Se debug for true, autentica automaticamente o usuário mais recente.
     * 
     * @param context O contexto contendo os repositórios
     * @param debug Se true, autentica automaticamente o usuário mais recente
     */
    public ServicoAutenticacao(Context context, boolean debug) {
        if (context == null) {
            throw new IllegalArgumentException("Context não pode ser nulo");
        }
        this.context = context;
        this.pessoaAutenticada = null;
        
        if (debug) {
            Pessoa maisRecente = context.pessoas.getMaisRecente();
            if (maisRecente != null) {
                this.pessoaAutenticada = maisRecente;
                Logger.info("Modo debug ativado. Usuário mais recente autenticado automaticamente: {}", maisRecente.getEmail());
            } else {
                Logger.warn("Modo debug ativado, mas nenhum usuário encontrado no repositório.");
            }
        }
    }

    @Override
    public void registrar(String email, String senha) throws EmailJaCadastradoException {
        registrar("Usuário", email, senha, "Arigó");
    }

    /**
     * Registra um novo usuário no sistema com nome.
     * Por padrão, cria um Arigó.
     * 
     * @param nome O nome do usuário
     * @param email O email do usuário
     * @param senha A senha do usuário
     * @throws EmailJaCadastradoException Se o email já estiver cadastrado
     */
    public void registrar(String nome, String email, String senha) throws EmailJaCadastradoException {
        registrar(nome, email, senha, "Arigó");
    }

    /**
     * Registra um novo usuário no sistema com nome e tipo.
     * 
     * @param nome O nome do usuário
     * @param email O email do usuário
     * @param senha A senha do usuário
     * @param tipo O tipo de usuário ("Arigó" ou "Crítico")
     * @throws EmailJaCadastradoException Se o email já estiver cadastrado
     */
    public void registrar(String nome, String email, String senha, String tipo) throws EmailJaCadastradoException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }

        // Verifica se o email já está cadastrado
        Pessoa pessoaExistente = buscarPessoaPorEmail(email);
        if (pessoaExistente != null) {
            throw new EmailJaCadastradoException(email);
        }

        // Para registro, usamos data de nascimento padrão (18 anos atrás)
        LocalDate dataNasc = LocalDate.now().minusYears(18);
        
        // Cria Arigo ou Critico conforme o tipo selecionado
        Pessoa novaPessoa;
        if (tipo.equalsIgnoreCase("Crítico") || tipo.equalsIgnoreCase("Critico")) {
            novaPessoa = new Critico(nome, dataNasc, email, senha);
            Logger.info("Novo crítico registrado: " + nome + " (" + email + ")");
        } else {
            // Default: Arigó
            novaPessoa = new Arigo(nome, dataNasc, email, senha);
            Logger.info("Novo arigó registrado: " + nome + " (" + email + ")");
        }
        
        this.pessoaAutenticada = novaPessoa;
        
        try {
            context.pessoas.add(novaPessoa);
            context.save();
        } catch (Exception e) {
            Logger.error(e, "Erro ao salvar contexto após registro de novo usuário: " + e.getMessage());
        }
    }

    @Override
    public void autenticar(String email, String senha) throws CredenciaisInvalidasException {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }

        // Busca a pessoa por email
        Pessoa pessoa = buscarPessoaPorEmail(email);
        if (pessoa == null) {
            Logger.warn("Tentativa de autenticação com email inexistente: {}", email);
            throw new CredenciaisInvalidasException("Email ou senha inválidos");
        }

        // Verifica se a senha está correta
        String senhaHash = Pessoa.hashSenha(senha);
        if (!senhaHash.equals(pessoa.getSenhaHash())) {
            Logger.warn("Tentativa de autenticação com senha incorreta para email: {}", email);
            throw new CredenciaisInvalidasException("Email ou senha inválidos");
        }

        // Autenticação bem-sucedida
        this.pessoaAutenticada = pessoa;
        Logger.info("Usuário autenticado com sucesso: {}", email);
    }

    @Override
    public boolean estaAutenticado() {
        return pessoaAutenticada != null;
    }

    @Override
    public Pessoa getPessoaAutenticada() {
        if (!estaAutenticado()) {
            throw new IllegalStateException("Nenhum usuário autenticado no momento");
        }
        return pessoaAutenticada;
    }

    @Override
    public void sair() {
        if (estaAutenticado()) {
            Logger.info("Usuário desautenticado: {}", pessoaAutenticada.getEmail());
            this.pessoaAutenticada = null;
        }
    }

    /**
     * Busca uma pessoa no repositório pelo email.
     * 
     * @param email O email a ser buscado
     * @return A pessoa encontrada ou null se não existir
     */
    private Pessoa buscarPessoaPorEmail(String email) {
        for (Pessoa pessoa : context.pessoas.findAll()) {
            if (pessoa.getEmail().equalsIgnoreCase(email)) {
                return pessoa;
            }
        }
        return null;
    }
}

