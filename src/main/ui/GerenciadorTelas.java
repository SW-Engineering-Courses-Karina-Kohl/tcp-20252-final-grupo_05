package main.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Gerencia todas as telas da aplicação, fornecendo acesso centralizado
 * e métodos para renderizar e navegar entre telas.
 */
public class GerenciadorTelas {
    private final JPanel painelPrincipal;
    private final CardLayout cardLayout;
    
    private final TelaLogin telaLogin;
    private final TelaCadastro telaCadastro;
    private final TelaInicial telaInicial;
    private final TelaDetalhes telaDetalhes;
    private final TelaFilmeLista telaFilmeLista;
    private final TelaLivroLista telaLivroLista;
    private final TelaJogoLista telaJogoLista;
    private final TelaSerieLista telaSerieLista;
    
    /**
     * Construtor que recebe todas as telas e o painel principal.
     * 
     * @param painelPrincipal O painel principal que contém todas as telas
     * @param cardLayout O CardLayout usado para navegação
     * @param telaLogin A tela de login
     * @param telaCadastro A tela de cadastro
     * @param telaInicial A tela inicial/home
     * @param telaDetalhes A tela de detalhes
     * @param telaFilmeLista A tela de lista de filmes
     * @param telaLivroLista A tela de lista de livros
     * @param telaJogoLista A tela de lista de jogos
     * @param telaSerieLista A tela de lista de séries
     */
    public GerenciadorTelas(JPanel painelPrincipal, CardLayout cardLayout,
                           TelaLogin telaLogin, TelaCadastro telaCadastro,
                           TelaInicial telaInicial, TelaDetalhes telaDetalhes,
                           TelaFilmeLista telaFilmeLista, TelaLivroLista telaLivroLista,
                           TelaJogoLista telaJogoLista, TelaSerieLista telaSerieLista) {
        this.painelPrincipal = painelPrincipal;
        this.cardLayout = cardLayout;
        this.telaLogin = telaLogin;
        this.telaCadastro = telaCadastro;
        this.telaInicial = telaInicial;
        this.telaDetalhes = telaDetalhes;
        this.telaFilmeLista = telaFilmeLista;
        this.telaLivroLista = telaLivroLista;
        this.telaJogoLista = telaJogoLista;
        this.telaSerieLista = telaSerieLista;
    }
    
    /**
     * Renderiza a tela inicial.
     */
    public void renderTelaInicial() {
        telaInicial.render();
    }
    
    /**
     * Renderiza a tela de cadastro.
     */
    public void renderTelaCadastro() {
        // Se TelaCadastro tiver método render(), adicionar aqui
        // telaCadastro.render();
    }
    
    /**
     * Renderiza a tela de detalhes.
     */
    public void renderTelaDetalhes() {
        // Se TelaDetalhes tiver método render(), adicionar aqui
        // telaDetalhes.render();
    }
    
    /**
     * Renderiza todas as telas que possuem método render().
     */
    public void renderTodas() {
        renderTelaInicial();
        renderTelaCadastro();
        renderTelaDetalhes();
    }
    
    /**
     * Navega para a tela especificada.
     * 
     * @param nomeTela O nome da tela ("LOGIN", "CADASTRO", "HOME", "DETALHES")
     */
    public void navegarPara(String nomeTela) {
        cardLayout.show(painelPrincipal, nomeTela);
    }
    
    /**
     * Navega para a tela inicial e renderiza antes de exibir.
     */
    public void navegarParaHome() {
        renderTelaInicial();
        navegarPara("HOME");
    }
    
    /**
     * Navega para a tela de login.
     */
    public void navegarParaLogin() {
        navegarPara("LOGIN");
    }
    
    /**
     * Navega para a tela de cadastro.
     */
    public void navegarParaCadastro() {
        navegarPara("CADASTRO");
    }
    
    /**
     * Navega para a tela de detalhes.
     */
    public void navegarParaDetalhes() {
        navegarPara("DETALHES");
    }
    
    /**
     * Navega para a tela de lista de filmes.
     */
    public void navegarParaFilmeLista() {
        navegarPara("FILME_LISTA");
    }
    
    /**
     * Navega para a tela de lista de livros.
     */
    public void navegarParaLivroLista() {
        navegarPara("LIVRO_LISTA");
    }
    
    /**
     * Navega para a tela de lista de jogos.
     */
    public void navegarParaJogoLista() {
        navegarPara("JOGO_LISTA");
    }
    
    /**
     * Navega para a tela de lista de séries.
     */
    public void navegarParaSerieLista() {
        navegarPara("SERIE_LISTA");
    }
    
    // Getters para acesso às telas individuais, se necessário
    public TelaLogin getTelaLogin() {
        return telaLogin;
    }
    
    public TelaCadastro getTelaCadastro() {
        return telaCadastro;
    }
    
    public TelaInicial getTelaInicial() {
        return telaInicial;
    }
    
    public TelaDetalhes getTelaDetalhes() {
        return telaDetalhes;
    }
    
    public TelaFilmeLista getTelaFilmeLista() {
        return telaFilmeLista;
    }
    
    public TelaLivroLista getTelaLivroLista() {
        return telaLivroLista;
    }
    
    public TelaJogoLista getTelaJogoLista() {
        return telaJogoLista;
    }
    
    public TelaSerieLista getTelaSerieLista() {
        return telaSerieLista;
    }
}

