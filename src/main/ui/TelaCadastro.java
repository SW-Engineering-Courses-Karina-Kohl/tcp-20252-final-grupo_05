package main.ui;

import main.service.autenticacao.Autenticacao;
import main.service.autenticacao.EmailJaCadastradoException;
import main.service.autenticacao.ServicoAutenticacao;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JPanel {
    
    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoCadastrar;
    private JButton botaoLogin;
    private JLabel labelErro;
    private JLabel labelSucesso;
    private Autenticacao autenticacao;
    
    public TelaCadastro(Autenticacao autenticacao) {
        this.autenticacao = autenticacao;
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
        
        JLabel titulo = new JLabel("Sign Up");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(40, 20, 30, 20);
        painel.add(titulo, gbc);
        
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 20, 5, 20);
        painel.add(labelNome, gbc);
        
        campoNome = new JTextField(20);
        campoNome.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 15, 20);
        painel.add(campoNome, gbc);
        
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 20, 5, 20);
        painel.add(labelEmail, gbc);
        
        campoEmail = new JTextField(20);
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 15, 20);
        painel.add(campoEmail, gbc);
        
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 20, 5, 20);
        painel.add(labelSenha, gbc);
        
        campoSenha = new JPasswordField(20);
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 10, 20);
        painel.add(campoSenha, gbc);

        // Label para mensagens de erro
        labelErro = new JLabel(" ");
        labelErro.setFont(new Font("Arial", Font.PLAIN, 12));
        labelErro.setForeground(Color.RED);
        labelErro.setHorizontalAlignment(SwingConstants.CENTER);
        labelErro.setPreferredSize(new Dimension(0, 20)); // Altura mínima
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);
        painel.add(labelErro, gbc);

        // Label para mensagens de sucesso
        labelSucesso = new JLabel(" ");
        labelSucesso.setFont(new Font("Arial", Font.PLAIN, 12));
        labelSucesso.setForeground(new Color(0, 128, 0));
        labelSucesso.setHorizontalAlignment(SwingConstants.CENTER);
        labelSucesso.setPreferredSize(new Dimension(0, 20)); // Altura mínima
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 10, 20);
        painel.add(labelSucesso, gbc);
        
        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoCadastrar.addActionListener(e -> {
            realizarCadastro();
        });
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        painel.add(botaoCadastrar, gbc);
        
        botaoLogin = new JButton("Já tem conta? Faça login");
        botaoLogin.setFont(new Font("Arial", Font.PLAIN, 12));
        botaoLogin.setBorderPainted(false);
        botaoLogin.setContentAreaFilled(false);
        botaoLogin.setForeground(new Color(70, 130, 180));
        botaoLogin.addActionListener(e -> {
            Logger.debug("Navegando da tela de cadastro para a tela de login.");
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "LOGIN");
        });
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 20, 20);
        painel.add(botaoLogin, gbc);
        
        return painel;
    }

    /**
     * Realiza o cadastro do usuário.
     */
    private void realizarCadastro() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        // Limpar mensagens anteriores
        labelErro.setText(" ");
        labelSucesso.setText(" ");

        // Validação básica
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            labelErro.setText("Por favor, preencha todos os campos.");
            return;
        }

        if (senha.length() < 3) {
            labelErro.setText("A senha deve ter pelo menos 3 caracteres.");
            return;
        }

        try {
            // Verifica se é ServicoAutenticacao para usar o método com nome
            if (autenticacao instanceof ServicoAutenticacao) {
                ((ServicoAutenticacao) autenticacao).registrar(nome, email, senha);
            } else {
                autenticacao.registrar(email, senha);
            }
            
            Logger.info("Cadastro bem-sucedido para o email: {}", email);
            labelSucesso.setText("Cadastro realizado com sucesso! Redirecionando para Home...");
            
            // Limpar campos
            campoNome.setText("");
            campoEmail.setText("");
            campoSenha.setText("");
            
            // Aguardar um pouco e navegar para login
            Timer timer = new Timer(2000, e -> {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "HOME");
                labelSucesso.setText(" ");
            });
            timer.setRepeats(false);
            timer.start();
            
        } catch (EmailJaCadastradoException e) {
            labelErro.setText("Este email já está cadastrado. Tente fazer login.");
            Logger.warn("Tentativa de cadastro com email já existente: {}", email);
        } catch (Exception e) {
            labelErro.setText("Erro ao realizar cadastro. Tente novamente.");
            Logger.error(e, "Erro inesperado ao realizar cadastro");
        }
    }
    
    public JTextField getCampoNome() {
        return campoNome;
    }
    
    public JTextField getCampoEmail() {
        return campoEmail;
    }
    
    public JPasswordField getCampoSenha() {
        return campoSenha;
    }
    
    public JButton getBotaoCadastrar() {
        return botaoCadastrar;
    }
}

