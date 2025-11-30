package main.ui;

import main.models.*;
import main.service.Context;
import main.service.autenticacao.Autenticacao;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaDetalhes extends JPanel {
    
    private Context context;
    private Autenticacao autenticacao;
    private GerenciadorTelas gerenciadorTelas;
    private Conteudo conteudoAtual;
    
    private JPanel painelPrincipal;
    private JTextArea campoComentario;
    private JSpinner campoNota;
    private JButton botaoAvaliar;
    private JPanel painelAvaliacoes;
    
    public TelaDetalhes(Context context, Autenticacao autenticacao) {
        this.context = context;
        this.autenticacao = autenticacao;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Painel principal com scroll
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(Color.WHITE);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JScrollPane scrollPane = new JScrollPane(painelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Define o conteúdo a ser exibido e atualiza a tela.
     */
    public void exibirConteudo(Conteudo conteudo) {
        this.conteudoAtual = conteudo;
        render();
    }
    
    /**
     * Renderiza toda a tela com as informações do conteúdo atual.
     */
    private void render() {
        painelPrincipal.removeAll();
        
        if (conteudoAtual == null) {
            JLabel labelErro = new JLabel("Nenhum conteúdo selecionado.", SwingConstants.CENTER);
            labelErro.setFont(new Font("Arial", Font.PLAIN, 18));
            painelPrincipal.add(labelErro);
            revalidate();
            repaint();
            return;
        }
        
        // Botão voltar
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelVoltar.setBackground(Color.WHITE);
        JButton botaoVoltar = new JButton("← Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoVoltar.setForeground(new Color(70, 130, 180));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoVoltar.addActionListener(e -> {
            if (gerenciadorTelas != null) {
                gerenciadorTelas.navegarParaHome();
            }
        });
        painelVoltar.add(botaoVoltar);
        painelPrincipal.add(painelVoltar);
        painelPrincipal.add(Box.createVerticalStrut(20));
        
        // Informações do conteúdo
        painelPrincipal.add(criarPainelInformacoes());
        painelPrincipal.add(Box.createVerticalStrut(30));
        
        // Seção de criar avaliação
        if (autenticacao.estaAutenticado() && autenticacao.getPessoaAutenticada() instanceof Avaliador) {
            painelPrincipal.add(criarPainelAvaliacao());
            painelPrincipal.add(Box.createVerticalStrut(30));
        }
        
        // Seção de avaliações
        painelPrincipal.add(criarPainelListaAvaliacoes());
        
        revalidate();
        repaint();
    }
    
    /**
     * Cria o painel com as informações do conteúdo.
     */
    private JPanel criarPainelInformacoes() {
        JPanel painel = new JPanel(new BorderLayout(15, 0));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Placeholder de imagem à esquerda
        JPanel placeholderImagem = new JPanel();
        placeholderImagem.setPreferredSize(new Dimension(200, 150));
        placeholderImagem.setBackground(new Color(230, 230, 230));
        placeholderImagem.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        JLabel labelImagem = new JLabel("Imagem", SwingConstants.CENTER);
        labelImagem.setFont(new Font("Arial", Font.PLAIN, 12));
        labelImagem.setForeground(new Color(150, 150, 150));
        placeholderImagem.setLayout(new BorderLayout());
        placeholderImagem.add(labelImagem, BorderLayout.CENTER);
        
        painel.add(placeholderImagem, BorderLayout.WEST);
        
        // Conteúdo à direita
        JPanel painelConteudo = new JPanel();
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));
        painelConteudo.setBackground(Color.WHITE);
        
        // Título
        JLabel labelTitulo = new JLabel(conteudoAtual.getTitulo());
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(50, 50, 50));
        painelConteudo.add(labelTitulo);
        painelConteudo.add(Box.createVerticalStrut(10));
        
        // Informações comuns
        JLabel labelData = new JLabel("Lançamento: " + 
            conteudoAtual.getDataLanc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        labelData.setFont(new Font("Arial", Font.PLAIN, 12));
        labelData.setForeground(new Color(100, 100, 100));
        painelConteudo.add(labelData);
        painelConteudo.add(Box.createVerticalStrut(5));
        
        int numAvaliacoes = conteudoAtual.getAvaliacoes().size();
        String textoAvaliacoes = numAvaliacoes == 1 ? "1 avaliação" : numAvaliacoes + " avaliações";
        JLabel labelNumAvaliacoes = new JLabel(textoAvaliacoes);
        labelNumAvaliacoes.setFont(new Font("Arial", Font.PLAIN, 12));
        labelNumAvaliacoes.setForeground(new Color(100, 100, 100));
        painelConteudo.add(labelNumAvaliacoes);
        
        if (numAvaliacoes > 0) {
            painelConteudo.add(Box.createVerticalStrut(5));
            double media = conteudoAtual.calcularMedia();
            String textoMedia = String.format("Média: %.1f", media);
            JLabel labelMedia = new JLabel(textoMedia);
            labelMedia.setFont(new Font("Arial", Font.BOLD, 12));
            labelMedia.setForeground(new Color(70, 130, 180));
            painelConteudo.add(labelMedia);
        }
        
        painelConteudo.add(Box.createVerticalStrut(10));
        
        // Informações específicas do tipo
        if (conteudoAtual instanceof Filme) {
            adicionarInfoFilme(painelConteudo, (Filme) conteudoAtual);
        } else if (conteudoAtual instanceof Livro) {
            adicionarInfoLivro(painelConteudo, (Livro) conteudoAtual);
        } else if (conteudoAtual instanceof Jogo) {
            adicionarInfoJogo(painelConteudo, (Jogo) conteudoAtual);
        } else if (conteudoAtual instanceof Serie) {
            adicionarInfoSerie(painelConteudo, (Serie) conteudoAtual);
        }
        
        painel.add(painelConteudo, BorderLayout.CENTER);
        
        return painel;
    }
    
    private void adicionarInfoFilme(JPanel painel, Filme filme) {
        JLabel labelDiretor = new JLabel("Diretor: " + filme.getDiretor());
        labelDiretor.setFont(new Font("Arial", Font.PLAIN, 12));
        labelDiretor.setForeground(new Color(100, 100, 100));
        painel.add(labelDiretor);
        
        painel.add(Box.createVerticalStrut(5));
        
        int horas = filme.getDuracaoMinutos() / 60;
        int minutos = filme.getDuracaoMinutos() % 60;
        String duracao = horas > 0 
            ? String.format("%dh %dm", horas, minutos)
            : filme.getDuracaoMinutos() + " minutos";
        JLabel labelDuracao = new JLabel("Duração: " + duracao);
        labelDuracao.setFont(new Font("Arial", Font.PLAIN, 12));
        labelDuracao.setForeground(new Color(100, 100, 100));
        painel.add(labelDuracao);
    }
    
    private void adicionarInfoLivro(JPanel painel, Livro livro) {
        JLabel labelAutor = new JLabel("Autor: " + livro.getAutor());
        labelAutor.setFont(new Font("Arial", Font.PLAIN, 12));
        labelAutor.setForeground(new Color(100, 100, 100));
        painel.add(labelAutor);
        
        painel.add(Box.createVerticalStrut(5));
        
        JLabel labelEditora = new JLabel("Editora: " + livro.getEditora());
        labelEditora.setFont(new Font("Arial", Font.PLAIN, 12));
        labelEditora.setForeground(new Color(100, 100, 100));
        painel.add(labelEditora);
        
        painel.add(Box.createVerticalStrut(5));
        
        JLabel labelPaginas = new JLabel("Número de páginas: " + livro.getNumeroPaginas());
        labelPaginas.setFont(new Font("Arial", Font.PLAIN, 12));
        labelPaginas.setForeground(new Color(100, 100, 100));
        painel.add(labelPaginas);
    }
    
    private void adicionarInfoJogo(JPanel painel, Jogo jogo) {
        JLabel labelGenero = new JLabel("Gênero: " + jogo.getGenero());
        labelGenero.setFont(new Font("Arial", Font.PLAIN, 12));
        labelGenero.setForeground(new Color(100, 100, 100));
        painel.add(labelGenero);
        
        painel.add(Box.createVerticalStrut(5));
        
        JLabel labelDesenvolvedora = new JLabel("Desenvolvedora: " + jogo.getDesenvolvedora());
        labelDesenvolvedora.setFont(new Font("Arial", Font.PLAIN, 12));
        labelDesenvolvedora.setForeground(new Color(100, 100, 100));
        painel.add(labelDesenvolvedora);
        
        if (jogo.getPlataformas() != null && !jogo.getPlataformas().isEmpty()) {
            painel.add(Box.createVerticalStrut(5));
            String plataformas = String.join(", ", jogo.getPlataformas());
            JLabel labelPlataformas = new JLabel("Plataformas: " + plataformas);
            labelPlataformas.setFont(new Font("Arial", Font.PLAIN, 12));
            labelPlataformas.setForeground(new Color(100, 100, 100));
            painel.add(labelPlataformas);
        }
    }
    
    private void adicionarInfoSerie(JPanel painel, Serie serie) {
        JLabel labelTemporadas = new JLabel("Número de temporadas: " + serie.getTotalTemporadas());
        labelTemporadas.setFont(new Font("Arial", Font.PLAIN, 12));
        labelTemporadas.setForeground(new Color(100, 100, 100));
        painel.add(labelTemporadas);
    }
    
    /**
     * Cria o painel para criar uma nova avaliação.
     */
    private JPanel criarPainelAvaliacao() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título da seção
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(Color.WHITE);
        JLabel labelTitulo = new JLabel("Avaliar Conteúdo");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitulo.setForeground(new Color(50, 50, 50));
        painelTitulo.add(labelTitulo);
        painel.add(painelTitulo);
        painel.add(Box.createVerticalStrut(15));
        
        // Campo de comentário
        JPanel painelComentario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelComentario.setBackground(Color.WHITE);
        JLabel labelComentario = new JLabel("Comentário:");
        labelComentario.setFont(new Font("Arial", Font.PLAIN, 14));
        painelComentario.add(labelComentario);
        painel.add(painelComentario);
        painel.add(Box.createVerticalStrut(5));
        
        campoComentario = new JTextArea(4, 30);
        campoComentario.setFont(new Font("Arial", Font.PLAIN, 14));
        campoComentario.setLineWrap(true);
        campoComentario.setWrapStyleWord(true);
        campoComentario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        JScrollPane scrollComentario = new JScrollPane(campoComentario);
        scrollComentario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        painel.add(scrollComentario);
        painel.add(Box.createVerticalStrut(15));
        
        // Campo de nota
        JPanel painelNota = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNota.setBackground(Color.WHITE);
        JLabel labelNota = new JLabel("Nota (1-5):");
        labelNota.setFont(new Font("Arial", Font.PLAIN, 14));
        painelNota.add(labelNota);
        
        SpinnerNumberModel model = new SpinnerNumberModel(5, 1, 5, 1);
        campoNota = new JSpinner(model);
        campoNota.setFont(new Font("Arial", Font.PLAIN, 14));
        painelNota.add(campoNota);
        
        painel.add(painelNota);
        painel.add(Box.createVerticalStrut(15));
        
        // Botão enviar
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotao.setBackground(Color.WHITE);
        botaoAvaliar = new JButton("Enviar");
        botaoAvaliar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoAvaliar.setBackground(new Color(70, 130, 180));
        botaoAvaliar.setForeground(Color.WHITE);
        botaoAvaliar.setOpaque(true);
        botaoAvaliar.setBorderPainted(false);
        botaoAvaliar.setContentAreaFilled(true);
        botaoAvaliar.setFocusPainted(false);
        botaoAvaliar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoAvaliar.setPreferredSize(new Dimension(120, 35));
        botaoAvaliar.addActionListener(e -> criarAvaliacao());
        painelBotao.add(botaoAvaliar);
        painel.add(painelBotao);
        
        return painel;
    }
    
    /**
     * Cria uma nova avaliação.
     */
    private void criarAvaliacao() {
        try {
            if (!autenticacao.estaAutenticado()) {
                JOptionPane.showMessageDialog(this, "Você precisa estar autenticado para avaliar.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Pessoa pessoa = autenticacao.getPessoaAutenticada();
            if (!(pessoa instanceof Avaliador)) {
                JOptionPane.showMessageDialog(this, "Você não tem permissão para avaliar.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String comentario = campoComentario.getText().trim();
            if (comentario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, escreva um comentário.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int nota = (Integer) campoNota.getValue();
            
            // Criar avaliação usando o método avaliar() do Avaliador
            // Isso já chama conteudo.adicionarAvaliacao() e pessoa.registrarAvaliacao()
            Avaliador avaliador = (Avaliador) pessoa;
            Avaliacao novaAvaliacao = avaliador.avaliar(conteudoAtual, nota, comentario);
            
            // Adicionar avaliação ao repository de avaliações
            context.avaliacoes.add(novaAvaliacao);
            
            // Limpar campos
            campoComentario.setText("");
            campoNota.setValue(5);
            
            // Atualizar tela
            render();

            context.save();
            
            JOptionPane.showMessageDialog(this, "Avaliação criada com sucesso!", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            Logger.info("Avaliação criada para conteúdo: {}", conteudoAtual.getTitulo());
        } catch (Exception e) {
            Logger.error(e, "Erro ao criar avaliação");
            JOptionPane.showMessageDialog(this, "Erro ao criar avaliação: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Cria o painel com a lista de avaliações.
     */
    private JPanel criarPainelListaAvaliacoes() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título da seção
        JLabel labelTitulo = new JLabel("Avaliações");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitulo.setForeground(new Color(50, 50, 50));
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(Color.WHITE);
        painelTitulo.add(labelTitulo);
        painel.add(painelTitulo, BorderLayout.NORTH);
        
        painelAvaliacoes = new JPanel();
        painelAvaliacoes.setLayout(new BoxLayout(painelAvaliacoes, BoxLayout.Y_AXIS));
        painelAvaliacoes.setBackground(Color.WHITE);
        painelAvaliacoes.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        List<Avaliacao> avaliacoes = conteudoAtual.getAvaliacoes();
        
        if (avaliacoes.isEmpty()) {
            JLabel labelVazio = new JLabel("Nenhuma avaliação ainda.", SwingConstants.CENTER);
            labelVazio.setFont(new Font("Arial", Font.PLAIN, 14));
            labelVazio.setForeground(new Color(150, 150, 150));
            labelVazio.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            painelAvaliacoes.add(labelVazio);
        } else {
            for (Avaliacao avaliacao : avaliacoes) {
                painelAvaliacoes.add(criarCardAvaliacao(avaliacao));
                painelAvaliacoes.add(Box.createVerticalStrut(15));
            }
        }
        
        JScrollPane scrollAvaliacoes = new JScrollPane(painelAvaliacoes);
        scrollAvaliacoes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollAvaliacoes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollAvaliacoes.setBorder(null);
        scrollAvaliacoes.setPreferredSize(new Dimension(0, 400));
        
        painel.add(scrollAvaliacoes, BorderLayout.CENTER);
        
        return painel;
    }
    
    /**
     * Cria um card para exibir uma avaliação.
     */
    private JPanel criarCardAvaliacao(Avaliacao avaliacao) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Cabeçalho com nota e nome do usuário
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(new Color(250, 250, 250));
        
        // Buscar nome do usuário
        String nomeUsuario = "Usuário";
        try {
            Pessoa pessoa = context.pessoas.getById(avaliacao.getIdUsuario());
            if (pessoa != null) {
                nomeUsuario = pessoa.getNome();
            }
        } catch (Exception e) {
            Logger.warn("Erro ao buscar nome do usuário: {}", e.getMessage());
        }
        
        JLabel labelUsuario = new JLabel(nomeUsuario);
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        labelUsuario.setForeground(new Color(70, 130, 180));
        cabecalho.add(labelUsuario, BorderLayout.WEST);
        
        JLabel labelNota = new JLabel("Nota: " + avaliacao.getNota() + "/5");
        labelNota.setFont(new Font("Arial", Font.BOLD, 14));
        labelNota.setForeground(new Color(50, 50, 50));
        cabecalho.add(labelNota, BorderLayout.EAST);
        
        card.add(cabecalho);
        card.add(Box.createVerticalStrut(10));
        
        // Comentário
        JTextArea areaComentario = new JTextArea(avaliacao.getComentario());
        areaComentario.setFont(new Font("Arial", Font.PLAIN, 14));
        areaComentario.setForeground(new Color(80, 80, 80));
        areaComentario.setLineWrap(true);
        areaComentario.setWrapStyleWord(true);
        areaComentario.setEditable(false);
        areaComentario.setBackground(new Color(250, 250, 250));
        areaComentario.setBorder(null);
        card.add(areaComentario);
        
        // Likes
        if (avaliacao.getLikes() > 0) {
            card.add(Box.createVerticalStrut(10));
            JLabel labelLikes = new JLabel("❤️ " + avaliacao.getLikes() + 
                (avaliacao.getLikes() == 1 ? " like" : " likes"));
            labelLikes.setFont(new Font("Arial", Font.PLAIN, 12));
            labelLikes.setForeground(new Color(150, 150, 150));
            card.add(labelLikes);
        }
        
        return card;
    }
    
    /**
     * Define o gerenciador de telas para permitir navegação.
     */
    public void setGerenciadorTelas(GerenciadorTelas gerenciadorTelas) {
        this.gerenciadorTelas = gerenciadorTelas;
    }
}
