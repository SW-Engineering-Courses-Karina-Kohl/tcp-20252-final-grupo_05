package main.service.autenticacao;

/**
 * Exceção lançada quando se tenta registrar um email que já está cadastrado no sistema.
 */
public class EmailJaCadastradoException extends Exception {
    private static final long serialVersionUID = 1L;

    public EmailJaCadastradoException(String email) {
        super("Email já cadastrado no sistema: " + email);
    }

    public EmailJaCadastradoException(String email, Throwable cause) {
        super("Email já cadastrado no sistema: " + email, cause);
    }
}

