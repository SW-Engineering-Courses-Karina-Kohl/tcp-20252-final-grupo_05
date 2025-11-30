package main.ui;

import main.models.Serie;
import main.service.Context;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaSerieLista extends JPanel {
    
    private GerenciadorTelas gerenciadorTelas;
    private Context context;
    
    public TelaSerieLista(Context context) {
        this.context = context;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Navbar no topo
        JPanel navbar = criarNavbar();
        add(navbar, BorderLayout.NORTH);
        
        // Conteúdo com scroll
        JPanel conteudo = criarConteudo();
        JScrollPane scrollPane = new JScrollPane(conteudo);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Cria a navbar com botão voltar à esquerda e título ao centro.
     */
    private JPanel criarNavbar() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.WHITE);
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        
        // Botão voltar à esquerda
        JButton botaoVoltar = new JButton("← Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoVoltar.setForeground(new Color(70, 130, 180));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        botaoVoltar.addActionListener(e -> {
            if (gerenciadorTelas != null) {
                gerenciadorTelas.navegarParaHome();
            }
        });
        navbar.add(botaoVoltar, BorderLayout.WEST);
        
        // Título ao centro
        JLabel titulo = new JLabel("Todas as Séries", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(50, 50, 50));
        navbar.add(titulo, BorderLayout.CENTER);
        
        // Espaço vazio à direita para manter o título centralizado
        JPanel painelDireita = new JPanel();
        painelDireita.setBackground(Color.WHITE);
        painelDireita.setPreferredSize(new Dimension(100, 0));
        navbar.add(painelDireita, BorderLayout.EAST);
        
        return navbar;
    }
    
    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(new Color(245, 245, 245));
        conteudo.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Buscar séries do Context
        List<Serie> series = context.series.findAll();
        
        if (series.isEmpty()) {
            JLabel labelVazio = new JLabel("Nenhuma série cadastrada.", SwingConstants.CENTER);
            labelVazio.setFont(new Font("Arial", Font.PLAIN, 16));
            labelVazio.setForeground(new Color(150, 150, 150));
            conteudo.add(labelVazio);
        } else {
            for (int i = 0; i < series.size(); i++) {
                JPanel card = criarCard(series.get(i));
                conteudo.add(card);
                if (i < series.size() - 1) {
                    conteudo.add(Box.createVerticalStrut(15));
                }
            }
        }
        
        return conteudo;
    }
    
    private JPanel criarCard(Serie serie) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Adicionar listener para tornar o card clicável
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (gerenciadorTelas != null) {
                    gerenciadorTelas.navegarParaDetalhes(serie);
                }
            }
        });
        
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
        
        JLabel labelTitulo = new JLabel(serie.getTitulo());
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(50, 50, 50));
        
        // Informações adicionais
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setBackground(Color.WHITE);
        
        JLabel labelData = new JLabel("Lançamento: " + serie.getDataLanc().toString());
        labelData.setFont(new Font("Arial", Font.PLAIN, 12));
        labelData.setForeground(new Color(100, 100, 100));
        
        int numAvaliacoes = serie.getAvaliacoes().size();
        String textoAvaliacoes = numAvaliacoes == 1 ? "1 avaliação" : numAvaliacoes + " avaliações";
        JLabel labelAvaliacoes = new JLabel(textoAvaliacoes);
        labelAvaliacoes.setFont(new Font("Arial", Font.PLAIN, 12));
        labelAvaliacoes.setForeground(new Color(100, 100, 100));
        
        double media = serie.calcularMedia();
        String textoMedia = numAvaliacoes > 0 
            ? String.format("Média: %.1f", media)
            : "";
        JLabel labelMedia = new JLabel(textoMedia);
        labelMedia.setFont(new Font("Arial", Font.PLAIN, 12));
        labelMedia.setForeground(new Color(100, 100, 100));
        
        painelInfo.add(labelData);
        painelInfo.add(Box.createVerticalStrut(5));
        painelInfo.add(labelAvaliacoes);
        painelInfo.add(Box.createVerticalStrut(5));
        painelInfo.add(labelMedia);
        
        painelConteudo.add(labelTitulo, BorderLayout.NORTH);
        painelConteudo.add(painelInfo, BorderLayout.CENTER);
        card.add(painelConteudo, BorderLayout.CENTER);
        
        return card;
    }
    
    public void setGerenciadorTelas(GerenciadorTelas gerenciadorTelas) {
        this.gerenciadorTelas = gerenciadorTelas;
    }
}

