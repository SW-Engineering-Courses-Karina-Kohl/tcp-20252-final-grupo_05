package main;

import main.ui.*;
import main.service.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import org.tinylog.Logger;

public class Main {
    
    public static void main(String[] args) {
        Logger.info("Aplicação iniciada. Iniciando configuração da interface gráfica.");

        // 1 - Tentar carregar contexto do disco
        // 2 - Se não houver dados salvos, usar CarregadorDeDados para carregar dados iniciais
        Context context;
        if (Context.hasSavedData()) {
            try {
                context = Context.load();
                Logger.info("Contexto carregado do disco. Total de pessoas: {}, filmes: {}, livros: {}, jogos: {}, séries: {}.",
                    context.pessoas.findAll().size(),
                    context.filmes.findAll().size(),
                    context.livros.findAll().size(),
                    context.jogos.findAll().size(),
                    context.series.findAll().size());
            } catch (IOException | ClassNotFoundException e) {
                Logger.warn(e, "Erro ao carregar contexto do disco. Carregando dados iniciais do CarregadorDeDados.");
                context = new Context();
                CarregadorDeDados carregador = new CarregadorDeDados();
                context.initialize(carregador);
            }
        } else {
            Logger.info("Nenhum contexto salvo encontrado. Carregando dados iniciais do CarregadorDeDados.");
            context = new Context();
            CarregadorDeDados carregador = new CarregadorDeDados();
            context.initialize(carregador);
        }

        // 3 - Iniciar interface gráfica
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Aplicação");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            
            CardLayout cardLayout = new CardLayout();
            JPanel painelPrincipal = new JPanel(cardLayout);
            
            TelaLogin telaLogin = new TelaLogin();
            TelaCadastro telaCadastro = new TelaCadastro();
            TelaInicial telaInicial = new TelaInicial();
            TelaDetalhes telaDetalhes = new TelaDetalhes();
            
            painelPrincipal.add(telaLogin, "LOGIN");
            painelPrincipal.add(telaCadastro, "CADASTRO");
            painelPrincipal.add(telaInicial, "HOME");
            painelPrincipal.add(telaDetalhes, "DETALHES");
            
            frame.add(painelPrincipal);

            try {
                cardLayout.show(painelPrincipal, "LOGIN");
                Logger.info("Interface gráfica carregada. Exibindo tela de login.");
                frame.setVisible(true);
            } catch (Exception e) {
                Logger.error(e, "Erro crítico ao exibir a interface gráfica inicial.");
            }
        });
    }
}

