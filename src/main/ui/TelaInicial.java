package main.ui;

import main.service.autenticacao.Autenticacao;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JPanel {
    
    private Autenticacao autenticacao;
    private JTextField campoPesquisa;
    private JButton botaoUsuario;
    private GerenciadorTelas gerenciadorTelas;
    
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
        
        // Inicializa com valor padrão, será atualizado quando o usuário fizer login
        botaoUsuario = new JButton("Usuário");
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
     * Cria a área de conteúdo com seções por tipo de conteúdo.
     */
    private JPanel criarConteudo() {
        // Painel principal com scroll
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(new Color(245, 245, 245));
        
        // Dados mockados para cada tipo de conteúdo
        String[] filmesMockados = {"Matrix", "Inception", "Interstellar", "Blade Runner 2049"};
        String[] livrosMockados = {"1984", "O Senhor dos Anéis", "Duna", "Neuromancer"};
        String[] jogosMockados = {"The Witcher 3", "Cyberpunk 2077", "Red Dead Redemption 2", "Elden Ring"};
        String[] seriesMockados = {"Breaking Bad", "Game of Thrones", "Stranger Things", "The Crown"};
        
        // Criar seções para cada tipo de conteúdo
        conteudo.add(criarSecaoConteudo("Filmes", "FILMES", filmesMockados));
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(criarSecaoConteudo("Livros", "LIVROS", livrosMockados));
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(criarSecaoConteudo("Jogos", "JOGOS", jogosMockados));
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(criarSecaoConteudo("Séries", "SERIES", seriesMockados));
        
        // Adicionar padding
        conteudo.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Painel com scroll
        JScrollPane scrollPane = new JScrollPane(conteudo);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        return painelPrincipal;
    }
    
    /**
     * Cria uma seção para um tipo específico de conteúdo.
     * 
     * @param tituloSecao O título da seção (ex: "Filmes")
     * @param tipoConteudo O tipo de conteúdo para navegação (ex: "FILMES")
     * @param titulosMockados Array com títulos mockados dos conteúdos
     * @return JPanel com a seção completa
     */
    private JPanel criarSecaoConteudo(String tituloSecao, String tipoConteudo, String[] titulosMockados) {
        JPanel secao = new JPanel();
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBackground(new Color(245, 245, 245));
        
        // Cabeçalho da seção com título e botão "Ver todos"
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(new Color(245, 245, 245));
        cabecalho.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Título da seção
        JLabel titulo = new JLabel(tituloSecao);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(50, 50, 50));
        cabecalho.add(titulo, BorderLayout.WEST);
        
        // Botão "Ver todos"
        JButton botaoVerTodos = new JButton("Ver todos");
        botaoVerTodos.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoVerTodos.setForeground(new Color(70, 130, 180));
        botaoVerTodos.setBorderPainted(false);
        botaoVerTodos.setContentAreaFilled(false);
        botaoVerTodos.setFocusPainted(false);
        botaoVerTodos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoVerTodos.addActionListener(e -> {
            if (gerenciadorTelas != null) {
                navegarParaLista(tipoConteudo);
            }
        });
        cabecalho.add(botaoVerTodos, BorderLayout.EAST);
        
        secao.add(cabecalho);
        
        // Cards dos conteúdos (mostrar apenas alguns, não todos)
        JPanel painelCards = new JPanel();
        painelCards.setLayout(new BoxLayout(painelCards, BoxLayout.Y_AXIS));
        painelCards.setBackground(new Color(245, 245, 245));
        
        // Mostrar apenas os primeiros 3-4 conteúdos
        int quantidadeCards = Math.min(titulosMockados.length, 4);
        for (int i = 0; i < quantidadeCards; i++) {
            JPanel card = criarCardComConteudo(titulosMockados[i]);
            painelCards.add(card);
            if (i < quantidadeCards - 1) {
                painelCards.add(Box.createVerticalStrut(15));
            }
        }
        
        secao.add(painelCards);
        
        return secao;
    }
    
    /**
     * Navega para a tela de lista correspondente ao tipo de conteúdo.
     */
    private void navegarParaLista(String tipoConteudo) {
        if (gerenciadorTelas == null) {
            Logger.warn("Gerenciador de telas não configurado. Não é possível navegar para: {}", tipoConteudo);
            return;
        }
        
        switch (tipoConteudo) {
            case "FILMES":
                gerenciadorTelas.navegarParaFilmeLista();
                break;
            case "LIVROS":
                gerenciadorTelas.navegarParaLivroLista();
                break;
            case "JOGOS":
                gerenciadorTelas.navegarParaJogoLista();
                break;
            case "SERIES":
                gerenciadorTelas.navegarParaSerieLista();
                break;
            default:
                Logger.warn("Tipo de conteúdo desconhecido: {}", tipoConteudo);
        }
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
    
    /**
     * Renderiza/atualiza toda a tela com os dados atuais.
     * Deve ser chamado quando o usuário fizer login, quando a tela for exibida,
     * ou sempre que houver mudanças que requerem atualização da interface.
     */
    public void render() {
        atualizarUsuario();
        // Aqui podem ser adicionadas outras atualizações da tela no futuro
        // Por exemplo: atualizarCards(), atualizarPesquisa(), etc.
    }
    
    /**
     * Atualiza o nome do usuário exibido na navbar.
     */
    private void atualizarUsuario() {
        try {
            if (autenticacao.estaAutenticado()) {
                String nomeUsuario = autenticacao.getPessoaAutenticada().getNome();
                botaoUsuario.setText(nomeUsuario);
                Logger.debug("Nome do usuário atualizado na tela inicial: {}", nomeUsuario);
            } else {
                botaoUsuario.setText("Usuário");
                Logger.warn("Tentativa de atualizar usuário, mas nenhum usuário está autenticado");
            }
        } catch (Exception e) {
            Logger.warn("Erro ao atualizar nome do usuário: {}", e.getMessage());
            botaoUsuario.setText("Usuário");
        }
    }
    
    /**
     * Define o gerenciador de telas para permitir navegação.
     * 
     * @param gerenciadorTelas O gerenciador de telas
     */
    public void setGerenciadorTelas(GerenciadorTelas gerenciadorTelas) {
        this.gerenciadorTelas = gerenciadorTelas;
    }
}


