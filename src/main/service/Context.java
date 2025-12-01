package main.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import main.models.*;
import org.tinylog.Logger;

/**
 * Contexto que fornece acesso aos repositórios de dados em memória.
 */
public class Context implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_SAVE_PATH = "context.dat";
    
    public final BaseRepository<Pessoa> pessoas;
    public final ConteudoRepository<Filme> filmes;
    public final ConteudoRepository<Livro> livros;
    public final ConteudoRepository<Jogo> jogos;
    public final ConteudoRepository<Serie> series;
    public final BaseRepository<Avaliacao> avaliacoes;

    /**
     * Cria um contexto vazio com repositórios vazios.
     */
    public Context() {
        this.pessoas = new BaseRepository<>(new ArrayList<>());
        this.filmes = new ConteudoRepository<>(new ArrayList<>());
        this.livros = new ConteudoRepository<>(new ArrayList<>());
        this.jogos = new ConteudoRepository<>(new ArrayList<>());
        this.series = new ConteudoRepository<>(new ArrayList<>());
        this.avaliacoes = new BaseRepository<>(new ArrayList<>());
    }

    /**
     * Inicializa o contexto. Tenta carregar do disco, senão carrega do CarregadorDeDados.
     * 
     * @param carregador Instância do CarregadorDeDados para carregar dados iniciais
     * @return Contexto inicializado
     */
    public static Context initialize(CarregadorDeDados carregador) {
        if (carregador == null) {
            throw new IllegalArgumentException("CarregadorDeDados cannot be null");
        }

        Context context;
        if (hasSavedData()) {
            try {
                context = load();
                Logger.info("Contexto carregado do disco. Total de pessoas: {}, filmes: {}, livros: {}, jogos: {}, séries: {}.",
                    context.pessoas.findAll().size(),
                    context.filmes.findAll().size(),
                    context.livros.findAll().size(),
                    context.jogos.findAll().size(),
                    context.series.findAll().size());
            } catch (IOException | ClassNotFoundException e) {
                Logger.warn(e, "Erro ao carregar contexto do disco. Carregando dados iniciais do CarregadorDeDados.");
                context = new Context();
                loadFromCarregador(context, carregador);
                try {
                    context.save();
                    Logger.info("Contexto inicializado do CarregadorDeDados e salvo em disco.");
                } catch (IOException saveException) {
                    Logger.error(saveException, "Erro ao salvar contexto após inicialização do CarregadorDeDados.");
                }
            }
        } else {
            Logger.info("Nenhum contexto salvo encontrado. Carregando dados iniciais do CarregadorDeDados.");
            context = new Context();
            loadFromCarregador(context, carregador);
            try {
                context.save();
                Logger.info("Contexto inicializado do CarregadorDeDados e salvo em disco.");
            } catch (IOException saveException) {
                Logger.error(saveException, "Erro ao salvar contexto após inicialização do CarregadorDeDados.");
            }
        }
        return context;
    }

    /**
     * Carrega dados do CarregadorDeDados nos repositórios do contexto.
     */
    private static void loadFromCarregador(Context context, CarregadorDeDados carregador) {
        Logger.info("Inicializando contexto com dados do CarregadorDeDados.");
        List<Pessoa> pessoasList = carregador.carregarUsuarios();
        context.pessoas.getAll().clear();
        context.pessoas.getAll().addAll(pessoasList);

        List<Filme> filmesList = carregador.carregarFilmes();
        context.filmes.getAll().clear();
        context.filmes.getAll().addAll(filmesList);

        List<Livro> livrosList = carregador.carregarLivros();
        context.livros.getAll().clear();
        context.livros.getAll().addAll(livrosList);

        List<Jogo> jogosList = carregador.carregarJogos();
        context.jogos.getAll().clear();
        context.jogos.getAll().addAll(jogosList);

        List<Serie> seriesList = carregador.carregarSeriesCompletas();
        context.series.getAll().clear();
        context.series.getAll().addAll(seriesList);

        context.avaliacoes.getAll().clear();

        Logger.info("Contexto inicializado. Total de pessoas: {}, filmes: {}, livros: {}, jogos: {}, séries: {}.",
            context.pessoas.findAll().size(),
            context.filmes.findAll().size(),
            context.livros.findAll().size(),
            context.jogos.findAll().size(),
            context.series.findAll().size());
    }

    /**
     * Salva o contexto em disco usando serialização Java.
     * 
     * @throws IOException Se houver erro ao escrever
     */
    public void save() throws IOException {
        save(DEFAULT_SAVE_PATH);
    }

    /**
     * Salva o contexto em disco no caminho especificado.
     * 
     * @param filePath Caminho onde salvar o contexto
     * @throws IOException Se houver erro ao escrever
     */
    public void save(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(this);
            Logger.info("Contexto salvo em: {}", filePath);
        }
    }

    /**
     * Verifica se existe um contexto salvo em disco.
     * 
     * @return true se o arquivo existe, false caso contrário
     */
    public static boolean hasSavedData() {
        return hasSavedData(DEFAULT_SAVE_PATH);
    }

    /**
     * Verifica se existe um contexto salvo no caminho especificado.
     * 
     * @param filePath Caminho a verificar
     * @return true se o arquivo existe, false caso contrário
     */
    public static boolean hasSavedData(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }

    /**
     * Carrega um contexto do disco usando deserialização Java.
     * 
     * @return Contexto carregado
     * @throws IOException Se houver erro ao ler
     * @throws ClassNotFoundException Se a classe não for encontrada
     */
    public static Context load() throws IOException, ClassNotFoundException {
        return load(DEFAULT_SAVE_PATH);
    }

    /**
     * Carrega um contexto do disco no caminho especificado.
     * 
     * @param filePath Caminho do arquivo a carregar
     * @return Contexto carregado
     * @throws IOException Se houver erro ao ler
     * @throws ClassNotFoundException Se a classe não for encontrada
     */
    public static Context load(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            Context context = (Context) ois.readObject();
            Logger.info("Contexto carregado de: {}", filePath);
            return context;
        }
    }

    /**
     * Retorna os 3 conteúdos com mais avaliações.
     * 
     * @return Lista com os 3 conteúdos que possuem mais avaliações
     */
    public List<Conteudo> getDestaque() {
        List<Conteudo> todosConteudos = new ArrayList<>();
        todosConteudos.addAll(filmes.findAll());
        todosConteudos.addAll(livros.findAll());
        todosConteudos.addAll(jogos.findAll());
        todosConteudos.addAll(series.findAll());
        
        return todosConteudos.stream()
                .sorted(java.util.Comparator.comparingInt((Conteudo c) -> c.getAvaliacoes().size()).reversed())
                .limit(3)
                .collect(java.util.stream.Collectors.toList());
    }
}

