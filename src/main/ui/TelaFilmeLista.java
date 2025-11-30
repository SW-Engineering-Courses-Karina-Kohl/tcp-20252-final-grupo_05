package main.ui;

import javax.swing.*;
import java.awt.*;
import org.tinylog.Logger;

public class TelaFilmeLista extends JPanel {
    
    private GerenciadorTelas gerenciadorTelas;
    
    public TelaFilmeLista() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        
        // Título
        JLabel titulo = new JLabel("Todos os Filmes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(50, 50, 50));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Conteúdo com scroll
        JPanel conteudo = criarConteudo();
        JScrollPane scrollPane = new JScrollPane(conteudo);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        
        // Botão voltar
        JButton botaoVoltar = new JButton("Voltar para Home");
        botaoVoltar.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoVoltar.addActionListener(e -> {
            if (gerenciadorTelas != null) {
                gerenciadorTelas.navegarParaHome();
            } else {
                Logger.warn("Gerenciador de telas não configurado");
            }
        });
        
        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(new Color(245, 245, 245));
        painelBotao.add(botaoVoltar);
        painelBotao.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(painelBotao, BorderLayout.SOUTH);
    }
    
    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(new Color(245, 245, 245));
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Dados mockados de filmes
        String[] filmes = {
            "Matrix", "Inception", "Interstellar", "Blade Runner 2049",
            "The Dark Knight", "Pulp Fiction", "Fight Club", "Forrest Gump",
            "The Shawshank Redemption", "The Godfather", "Goodfellas", "Scarface"
        };
        
        for (int i = 0; i < filmes.length; i++) {
            JPanel card = criarCard(filmes[i]);
            conteudo.add(card);
            if (i < filmes.length - 1) {
                conteudo.add(Box.createVerticalStrut(15));
            }
        }
        
        return conteudo;
    }
    
    private JPanel criarCard(String titulo) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        // Placeholder de imagem
        JPanel placeholderImagem = new JPanel();
        placeholderImagem.setPreferredSize(new Dimension(200, 150));
        placeholderImagem.setBackground(new Color(230, 230, 230));
        placeholderImagem.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        JLabel labelImagem = new JLabel("Imagem", SwingConstants.CENTER);
        labelImagem.setFont(new Font("Arial", Font.PLAIN, 12));
        labelImagem.setForeground(new Color(150, 150, 150));
        placeholderImagem.setLayout(new BorderLayout());
        placeholderImagem.add(labelImagem, BorderLayout.CENTER);
        
        card.add(placeholderImagem, BorderLayout.WEST);
        
        // Conteúdo
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
    
    public void setGerenciadorTelas(GerenciadorTelas gerenciadorTelas) {
        this.gerenciadorTelas = gerenciadorTelas;
    }
}

