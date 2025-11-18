package main;

import main.ui.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    
    public static void main(String[] args) {
        // 1 - Ler arquivos


        // 2 - Inicializar estado da aplicação em memória


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
            
            cardLayout.show(painelPrincipal, "LOGIN");
            
            frame.setVisible(true);
        });
    }
}

