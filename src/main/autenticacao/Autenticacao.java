package main.autenticacao;

import main.models.Pessoa;

/**
 * Interface para serviços de autenticação de usuários.
 */
public interface Autenticacao {
    /**
     * Registra um novo usuário no sistema.
     * 
     * @param email O email do usuário
     * @param senha A senha do usuário
     * @throws EmailJaCadastradoException Se o email já estiver cadastrado
     */
    void registrar(String email, String senha) throws EmailJaCadastradoException;

    /**
     * Autentica um usuário no sistema.
     * 
     * @param email O email do usuário
     * @param senha A senha do usuário
     * @throws CredenciaisInvalidasException Se o email ou senha forem inválidos
     */
    void autenticar(String email, String senha) throws CredenciaisInvalidasException;

    /**
     * Verifica se há um usuário autenticado no momento.
     * 
     * @return true se houver um usuário autenticado, false caso contrário
     */
    boolean estaAutenticado();

    /**
     * Retorna a pessoa atualmente autenticada.
     * 
     * @return A pessoa autenticada
     * @throws IllegalStateException Se não houver usuário autenticado
     */
    Pessoa getPessoaAutenticada();

    /**
     * Encerra a sessão do usuário autenticado.
     */
    void sair();
}

