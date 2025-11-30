package main;

import main.service.autenticacao.Autenticacao;
import main.service.autenticacao.ServicoAutenticacao;
import main.ui.*;
import main.service.*;
import javax.swing.*;
import java.awt.*;
import org.tinylog.Logger;

public class Main {
    
    public static void main(String[] args) {
        Logger.info("Aplicação iniciada. Iniciando configuração da interface gráfica.");

        // Inicializar contexto (carrega do disco se existir, senão usa CarregadorDeDados)
        CarregadorDeDados carregador = new CarregadorDeDados();
        Context context = Context.initialize(carregador);

        // Criar serviço de autenticação
        Autenticacao autenticacao = new ServicoAutenticacao(context, true);

        // 3 - Iniciar interface gráfica
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Aplicação");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            
            CardLayout cardLayout = new CardLayout();
            JPanel painelPrincipal = new JPanel(cardLayout);
            
                TelaLogin telaLogin = new TelaLogin(autenticacao);
                TelaCadastro telaCadastro = new TelaCadastro(autenticacao);
                TelaInicial telaInicial = new TelaInicial(autenticacao, context);
                TelaDetalhes telaDetalhes = new TelaDetalhes(context, autenticacao);
                TelaFilmeLista telaFilmeLista = new TelaFilmeLista(context);
                TelaLivroLista telaLivroLista = new TelaLivroLista(context);
                TelaJogoLista telaJogoLista = new TelaJogoLista(context);
                TelaSerieLista telaSerieLista = new TelaSerieLista(context);
            
            painelPrincipal.add(telaLogin, "LOGIN");
            painelPrincipal.add(telaCadastro, "CADASTRO");
            painelPrincipal.add(telaInicial, "HOME");
            painelPrincipal.add(telaDetalhes, "DETALHES");
            painelPrincipal.add(telaFilmeLista, "FILME_LISTA");
            painelPrincipal.add(telaLivroLista, "LIVRO_LISTA");
            painelPrincipal.add(telaJogoLista, "JOGO_LISTA");
            painelPrincipal.add(telaSerieLista, "SERIE_LISTA");
            
            // Criar gerenciador de telas
            GerenciadorTelas gerenciadorTelas = new GerenciadorTelas(
                painelPrincipal, cardLayout,
                telaLogin, telaCadastro, telaInicial, telaDetalhes,
                telaFilmeLista, telaLivroLista, telaJogoLista, telaSerieLista
            );
            
            // Passar gerenciador para as telas que precisam dele
            telaLogin.setGerenciadorTelas(gerenciadorTelas);
            telaCadastro.setGerenciadorTelas(gerenciadorTelas);
            telaInicial.setGerenciadorTelas(gerenciadorTelas);
            telaFilmeLista.setGerenciadorTelas(gerenciadorTelas);
            telaLivroLista.setGerenciadorTelas(gerenciadorTelas);
            telaJogoLista.setGerenciadorTelas(gerenciadorTelas);
            telaSerieLista.setGerenciadorTelas(gerenciadorTelas);
            telaDetalhes.setGerenciadorTelas(gerenciadorTelas);
            
            frame.add(painelPrincipal);

            try {
                if (autenticacao.estaAutenticado()) {
                    // Atualizar a tela inicial com os dados do usuário antes de exibir
                    gerenciadorTelas.navegarParaHome();
                    Logger.info("Interface gráfica carregada. Exibindo tela de home.");
                } else {
                    gerenciadorTelas.navegarParaLogin();
                    Logger.info("Interface gráfica carregada. Exibindo tela de login.");
                }
                frame.setVisible(true);
            } catch (Exception e) {
                Logger.error(e, "Erro crítico ao exibir a interface gráfica inicial.");
            }
        });
    }
}

