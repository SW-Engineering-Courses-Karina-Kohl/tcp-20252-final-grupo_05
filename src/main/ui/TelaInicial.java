package main.ui;

import main.models.Conteudo;
import main.models.Filme;
import main.models.Jogo;
import main.models.Livro;
import main.models.Serie;
import main.service.Context;
import main.service.autenticacao.Autenticacao;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaInicial extends JPanel {
    
    private Autenticacao autenticacao;
    private Context context;
    private JTextField campoPesquisa;
    private JButton botaoUsuario;
    private GerenciadorTelas gerenciadorTelas;
    
    public TelaInicial(Autenticacao autenticacao, Context context) {
        this.autenticacao = autenticacao;
        this.context = context;
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
        JPanel painelLogo = new JPanel();
        painelLogo.setLayout(new BoxLayout(painelLogo, BoxLayout.Y_AXIS));
        painelLogo.setBackground(Color.WHITE);
        painelLogo.add(Box.createVerticalGlue());
        JLabel logo = new JLabel("ArigóFlix");
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setForeground(new Color(70, 130, 180));
        logo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        painelLogo.add(logo);
        painelLogo.add(Box.createVerticalGlue());
        navbar.add(painelLogo, BorderLayout.WEST);
        
        // Campo de pesquisa ao centro
        JPanel painelPesquisa = new JPanel();
        painelPesquisa.setLayout(new BoxLayout(painelPesquisa, BoxLayout.Y_AXIS));
        painelPesquisa.setBackground(Color.WHITE);
        painelPesquisa.add(Box.createVerticalGlue());
        JPanel painelPesquisaInterno = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelPesquisaInterno.setBackground(Color.WHITE);
        campoPesquisa = new JTextField(25);
        campoPesquisa.setFont(new Font("Arial", Font.PLAIN, 14));
        campoPesquisa.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        // Adicionar listener para atualizar resultados quando o usuário digitar
        campoPesquisa.addActionListener(e -> {
            // Quando Enter é pressionado, atualizar a tela
            render();
        });
        // Atualizar enquanto digita (com debounce seria melhor, mas por simplicidade vamos usar um timer)
        campoPesquisa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private javax.swing.Timer timer;
            
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                atualizarComDelay();
            }
            
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                atualizarComDelay();
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                atualizarComDelay();
            }
            
            private void atualizarComDelay() {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                timer = new javax.swing.Timer(500, evt -> render());
                timer.setRepeats(false);
                timer.start();
            }
        });
        painelPesquisaInterno.add(campoPesquisa);
        painelPesquisa.add(painelPesquisaInterno);
        painelPesquisa.add(Box.createVerticalGlue());
        navbar.add(painelPesquisa, BorderLayout.CENTER);
        
        // Nome do usuário à direita (clicável)
        JPanel painelUsuario = new JPanel();
        painelUsuario.setLayout(new BoxLayout(painelUsuario, BoxLayout.Y_AXIS));
        painelUsuario.setBackground(Color.WHITE);
        painelUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        painelUsuario.add(Box.createVerticalGlue());
        JPanel painelUsuarioInterno = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelUsuarioInterno.setBackground(Color.WHITE);
        
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
        
        painelUsuarioInterno.add(botaoUsuario);
        painelUsuario.add(painelUsuarioInterno);
        painelUsuario.add(Box.createVerticalGlue());
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
        
        // Obter termo de busca do campo de pesquisa
        String termoBusca = null;
        if (campoPesquisa != null) {
            String textoBusca = campoPesquisa.getText().trim();
            if (!textoBusca.isEmpty()) {
                termoBusca = textoBusca;
            }
        }
        
        // Buscar dados reais do Context, com filtro se houver termo de busca
        List<Filme> filmes = context.filmes.findAll(termoBusca);
        List<Livro> livros = context.livros.findAll(termoBusca);
        List<Jogo> jogos = context.jogos.findAll(termoBusca);
        List<Serie> series = context.series.findAll(termoBusca);
        
        // Criar seções para cada tipo de conteúdo
        conteudo.add(criarSecaoConteudo("Filmes", "FILMES", filmes));
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(criarSecaoConteudo("Livros", "LIVROS", livros));
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(criarSecaoConteudo("Jogos", "JOGOS", jogos));
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(criarSecaoConteudo("Séries", "SERIES", series));
        
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
     * @param conteudos Lista de conteúdos do tipo especificado
     * @return JPanel com a seção completa
     */
    private JPanel criarSecaoConteudo(String tituloSecao, String tipoConteudo, List<? extends Conteudo> conteudos) {
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
        
        if (conteudos.isEmpty()) {
            // Mostrar mensagem quando não houver conteúdos
            JLabel labelVazio = new JLabel("Nenhum resultado em " + tituloSecao.toLowerCase());
            labelVazio.setFont(new Font("Arial", Font.PLAIN, 14));
            labelVazio.setForeground(new Color(150, 150, 150));
            labelVazio.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            painelCards.add(labelVazio);
        } else {
            // Mostrar apenas os primeiros 4 conteúdos
            int quantidadeCards = Math.min(conteudos.size(), 4);
            for (int i = 0; i < quantidadeCards; i++) {
                Conteudo conteudo = conteudos.get(i);
                JPanel card = criarCardComConteudo(conteudo);
                painelCards.add(card);
                if (i < quantidadeCards - 1) {
                    painelCards.add(Box.createVerticalStrut(15));
                }
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
     * Cria um card com largura total, imagem placeholder e título do conteúdo.
     */
    private JPanel criarCardComConteudo(Conteudo conteudo) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // Largura total, altura fixa
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Adicionar listener para tornar o card clicável
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (gerenciadorTelas != null) {
                    gerenciadorTelas.navegarParaDetalhes(conteudo);
                }
            }
        });
        
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
        
        // Conteúdo à direita (título e informações)
        JPanel painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(Color.WHITE);
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel labelTitulo = new JLabel(conteudo.getTitulo());
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(50, 50, 50));
        
        // Adicionar informações adicionais se disponíveis
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setBackground(Color.WHITE);
        
        JLabel labelData = new JLabel("Lançamento: " + conteudo.getDataLanc().toString());
        labelData.setFont(new Font("Arial", Font.PLAIN, 12));
        labelData.setForeground(new Color(100, 100, 100));
        
        int numAvaliacoes = conteudo.getAvaliacoes().size();
        String textoAvaliacoes = numAvaliacoes == 1 ? "1 avaliação" : numAvaliacoes + " avaliações";
        JLabel labelAvaliacoes = new JLabel(textoAvaliacoes);
        labelAvaliacoes.setFont(new Font("Arial", Font.PLAIN, 12));
        labelAvaliacoes.setForeground(new Color(100, 100, 100));
        
        double media = conteudo.calcularMedia();
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
        // Garantir que a navbar existe antes de atualizar
        boolean navbarExiste = false;
        Component conteudoAtual = null;
        
        if (getLayout() instanceof BorderLayout layout) {
            // Verificar se navbar existe
            Component northComp = layout.getLayoutComponent(BorderLayout.NORTH);
            if (northComp != null) {
                navbarExiste = true;
            }
            
            // Encontrar e remover apenas o conteúdo central
            Component centerComp = layout.getLayoutComponent(BorderLayout.CENTER);
            if (centerComp != null) {
                conteudoAtual = centerComp;
            }
        }
        
        // Se a navbar não existe, recriar toda a tela
        if (!navbarExiste) {
            removeAll();
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            
            JPanel navbar = criarNavbar();
            add(navbar, BorderLayout.NORTH);
        } else if (conteudoAtual != null) {
            // Remove apenas o conteúdo central, mantém a navbar
            remove(conteudoAtual);
        }
        
        // Atualizar usuário após garantir que navbar existe
        atualizarUsuario();
        
        JPanel novoConteudo = criarConteudo();
        add(novoConteudo, BorderLayout.CENTER);
        
        revalidate();
        repaint();
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


