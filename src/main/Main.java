package main;

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

