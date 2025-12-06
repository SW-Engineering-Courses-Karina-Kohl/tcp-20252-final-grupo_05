package main.service.autenticacao;

/**
 * Exceção lançada quando as credenciais fornecidas (email ou senha) são inválidas.
 */
public class CredenciaisInvalidasException extends Exception {
    private static final long serialVersionUID = 1L;

    public CredenciaisInvalidasException() {
        super("Email ou senha inválidos");
    }

    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }

    public CredenciaisInvalidasException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

