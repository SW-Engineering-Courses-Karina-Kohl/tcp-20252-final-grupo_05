package main.ui;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JPanel {
    
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastro;
    
    public TelaLogin() {
        setLayout(new BorderLayout());
        
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel painelEsquerdo = criarPainelEsquerdo();
        JPanel painelDireito = criarPainelDireito();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        painelPrincipal.add(painelEsquerdo, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        painelPrincipal.add(painelDireito, gbc);
        
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel tituloApp = new JLabel("ArigóFlix");
        tituloApp.setFont(new Font("Arial", Font.BOLD, 48));
        tituloApp.setForeground(new Color(70, 130, 180));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(tituloApp, gbc);
        
        JLabel imagemPlaceholder = new JLabel("🎬", SwingConstants.CENTER);
        imagemPlaceholder.setFont(new Font("Arial", Font.PLAIN, 120));
        
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        painel.add(imagemPlaceholder, gbc);
        
        return painel;
    }
    
    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel titulo = new JLabel("Sign In");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(40, 20, 30, 20);
        painel.add(titulo, gbc);
        
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 20, 5, 20);
        painel.add(labelEmail, gbc);
        
        campoEmail = new JTextField(20);
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 15, 20);
        painel.add(campoEmail, gbc);
        
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 20, 5, 20);
        painel.add(labelSenha, gbc);
        
        campoSenha = new JPasswordField(20);
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 20, 20);
        painel.add(campoSenha, gbc);
        
        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        painel.add(botaoEntrar, gbc);
        
        botaoCadastro = new JButton("Não tem conta? Cadastre-se");
        botaoCadastro.setFont(new Font("Arial", Font.PLAIN, 12));
        botaoCadastro.setBorderPainted(false);
        botaoCadastro.setContentAreaFilled(false);
        botaoCadastro.setForeground(new Color(70, 130, 180));
        botaoCadastro.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "CADASTRO");
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 20, 20);
        painel.add(botaoCadastro, gbc);
        
        return painel;
    }
    
    public JTextField getCampoEmail() {
        return campoEmail;
    }
    
    public JPasswordField getCampoSenha() {
        return campoSenha;
    }
    
    public JButton getBotaoEntrar() {
        return botaoEntrar;
    }
}

