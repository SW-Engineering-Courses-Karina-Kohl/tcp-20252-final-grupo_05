package main.ui;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JPanel {
    
    private JButton botaoDetalhes;
    
    public TelaInicial() {
        setLayout(new BorderLayout());
        
        JLabel titulo = new JLabel("Home", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.CENTER);
        
        botaoDetalhes = new JButton("Ir para Detalhes");
        botaoDetalhes.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "DETALHES");
        });
        
        JPanel painelBotao = new JPanel();
        painelBotao.add(botaoDetalhes);
        add(painelBotao, BorderLayout.SOUTH);
    }
}

