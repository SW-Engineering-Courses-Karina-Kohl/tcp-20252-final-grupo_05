package main.ui;

import javax.swing.*;
import java.awt.*;
import org.tinylog.Logger;

public class TelaDetalhes extends JPanel {
    
    private JButton botaoHome;
    
    public TelaDetalhes() {
        setLayout(new BorderLayout());
        
        JLabel titulo = new JLabel("Detalhes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.CENTER);
        
        botaoHome = new JButton("Ir para Home");
        botaoHome.addActionListener(e -> {
            Logger.debug("Usuário navegou da tela de detalhes para a tela inicial.");
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "HOME");
        });
        
        JPanel painelBotao = new JPanel();
        painelBotao.add(botaoHome);
        add(painelBotao, BorderLayout.SOUTH);
    }
}

