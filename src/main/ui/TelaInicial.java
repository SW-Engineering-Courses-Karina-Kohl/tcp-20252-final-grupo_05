package main.ui;

import main.service.autenticacao.Autenticacao;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JPanel {
    
    private Autenticacao autenticacao;
    private JTextField campoPesquisa;
    private JButton botaoUsuario;
    
    public TelaInicial(Autenticacao autenticacao) {
        this.autenticacao = autenticacao;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Navbar no topo
        JPanel navbar = criarNavbar();
        add(navbar, BorderLayout.NORTH);
        
        // Conteúdo principal
        JPanel conteudo = criarConteudo();
        add(conteudo, BorderLayout.CENTER);
    }
    
    /**
     * Cria a navbar com logo, pesquisa e nome do usuário.
     */
    private JPanel criarNavbar() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.WHITE);
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        
        // Logo "Arigoflix" à esquerda
        JLabel logo = new JLabel("ArigóFlix");
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setForeground(new Color(70, 130, 180));
        logo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        navbar.add(logo, BorderLayout.WEST);
        
        // Campo de pesquisa ao centro
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelPesquisa.setBackground(Color.WHITE);
        campoPesquisa = new JTextField(25);
        campoPesquisa.setFont(new Font("Arial", Font.PLAIN, 14));
        campoPesquisa.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        painelPesquisa.add(campoPesquisa);
        navbar.add(painelPesquisa, BorderLayout.CENTER);
        
        // Nome do usuário à direita (clicável)
        JPanel painelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelUsuario.setBackground(Color.WHITE);
        painelUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        String nomeUsuario = "Usuário";
        try {
            if (autenticacao.estaAutenticado()) {
                nomeUsuario = autenticacao.getPessoaAutenticada().getNome();
            }
        } catch (Exception e) {
            Logger.warn("Erro ao obter nome do usuário: {}", e.getMessage());
        }
        
        botaoUsuario = new JButton(nomeUsuario);
        botaoUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoUsuario.setForeground(new Color(70, 130, 180));
        botaoUsuario.setBorderPainted(false);
        botaoUsuario.setContentAreaFilled(false);
        botaoUsuario.setFocusPainted(false);
        botaoUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoUsuario.addActionListener(e -> {
            Logger.debug("Usuário clicou no nome. Navegando para página do usuário.");
            // TODO: Implementar navegação para página do usuário
        });
        
        painelUsuario.add(botaoUsuario);
        navbar.add(painelUsuario, BorderLayout.EAST);
        
        return navbar;
    }
    
    /**
     * Cria a área de conteúdo com título "Destaque" e cards.
     */
    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(new Color(245, 245, 245));
        conteudo.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Título "Destaque"
        JLabel tituloDestaque = new JLabel("Destaque");
        tituloDestaque.setFont(new Font("Arial", Font.BOLD, 24));
        tituloDestaque.setForeground(new Color(50, 50, 50));
        tituloDestaque.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        conteudo.add(tituloDestaque, BorderLayout.NORTH);
        
        // Painel com cards (layout vertical para empilhar cards em linha)
        JPanel painelCards = new JPanel();
        painelCards.setLayout(new BoxLayout(painelCards, BoxLayout.Y_AXIS));
        painelCards.setBackground(new Color(245, 245, 245));
        
        // Criar 4 cards com título mockado e imagem placeholder
        String[] titulosMockados = {
            "Filme Exemplo 1",
            "Série Exemplo 2",
            "Livro Exemplo 3",
            "Jogo Exemplo 4"
        };
        
        for (int i = 0; i < 4; i++) {
            JPanel card = criarCardComConteudo(titulosMockados[i]);
            painelCards.add(card);
            if (i < 3) {
                painelCards.add(Box.createVerticalStrut(20)); // Espaçamento entre cards
            }
        }
        
        conteudo.add(painelCards, BorderLayout.CENTER);
        
        return conteudo;
    }
    
    /**
     * Cria um card com largura total, imagem placeholder e título mockado.
     */
    private JPanel criarCardComConteudo(String titulo) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // Largura total, altura fixa
        
        // Placeholder de imagem à esquerda
        JPanel placeholderImagem = new JPanel();
        placeholderImagem.setPreferredSize(new Dimension(200, 150));
        placeholderImagem.setBackground(new Color(230, 230, 230));
        placeholderImagem.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        // Label "Imagem" no centro do placeholder
        JLabel labelImagem = new JLabel("Imagem", SwingConstants.CENTER);
        labelImagem.setFont(new Font("Arial", Font.PLAIN, 12));
        labelImagem.setForeground(new Color(150, 150, 150));
        placeholderImagem.setLayout(new BorderLayout());
        placeholderImagem.add(labelImagem, BorderLayout.CENTER);
        
        card.add(placeholderImagem, BorderLayout.WEST);
        
        // Conteúdo à direita (título)
        JPanel painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(Color.WHITE);
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(50, 50, 50));
        
        painelConteudo.add(labelTitulo, BorderLayout.NORTH);
        card.add(painelConteudo, BorderLayout.CENTER);
        
        return card;
    }
    
    public JTextField getCampoPesquisa() {
        return campoPesquisa;
    }
    
    public JButton getBotaoUsuario() {
        return botaoUsuario;
    }
}

