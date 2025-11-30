package main.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import main.models.*;
import org.tinylog.Logger;

/**
 * Context class that provides DbContext-like access to in-memory data repositories.
 * Initializes repositories from CarregadorDeDados and exposes them as public properties.
 */
public class Context implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_SAVE_PATH = "context.dat";
    
    public final Repository<Pessoa> pessoas;
    public final Repository<Filme> filmes;
    public final Repository<Livro> livros;
    public final Repository<Jogo> jogos;
    public final Repository<Serie> series;
    public final Repository<Avaliacao> avaliacoes;

    /**
     * Constructor that creates an empty context with empty repositories.
     * Use initialize() to load data from CarregadorDeDados.
     */
    public Context() {
        this.pessoas = new Repository<>(new ArrayList<>());
        this.filmes = new Repository<>(new ArrayList<>());
        this.livros = new Repository<>(new ArrayList<>());
        this.jogos = new Repository<>(new ArrayList<>());
        this.series = new Repository<>(new ArrayList<>());
        this.avaliacoes = new Repository<>(new ArrayList<>());
    }

    /**
     * Initializes a context by first trying to load from disk.
     * If no saved data exists, loads from CarregadorDeDados and saves it.
     * 
     * @param carregador The CarregadorDeDados instance to load initial data from if needed
     * @return The initialized Context instance
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
     * Loads data from CarregadorDeDados into the context repositories.
     * 
     * @param context The context to populate
     * @param carregador The CarregadorDeDados instance to load data from
     */
    private static void loadFromCarregador(Context context, CarregadorDeDados carregador) {
        Logger.info("Inicializando contexto com dados do CarregadorDeDados.");

        // Clear existing data and load from CarregadorDeDados
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

        // Avaliacoes are not loaded from CSV, so keep empty
        context.avaliacoes.getAll().clear();

        Logger.info("Contexto inicializado. Total de pessoas: {}, filmes: {}, livros: {}, jogos: {}, séries: {}.",
            context.pessoas.findAll().size(),
            context.filmes.findAll().size(),
            context.livros.findAll().size(),
            context.jogos.findAll().size(),
            context.series.findAll().size());
    }

    /**
     * Saves the entire context to disk using Java serialization.
     * Uses the default save path "context.dat".
     * 
     * @throws IOException If writing fails
     */
    public void save() throws IOException {
        save(DEFAULT_SAVE_PATH);
    }

    /**
     * Saves the entire context to disk using Java serialization.
     * 
     * @param filePath The path where to save the context
     * @throws IOException If writing fails
     */
    public void save(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(this);
            Logger.info("Contexto salvo em: {}", filePath);
        }
    }

    /**
     * Checks if a saved context file exists on disk.
     * Uses the default save path "context.dat".
     * 
     * @return true if the file exists, false otherwise
     */
    public static boolean hasSavedData() {
        return hasSavedData(DEFAULT_SAVE_PATH);
    }

    /**
     * Checks if a saved context file exists on disk.
     * 
     * @param filePath The path to check
     * @return true if the file exists, false otherwise
     */
    public static boolean hasSavedData(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }

    /**
     * Loads a context from disk using Java deserialization.
     * Uses the default save path "context.dat".
     * 
     * @return The loaded Context instance
     * @throws IOException If reading fails
     * @throws ClassNotFoundException If the class cannot be found
     */
    public static Context load() throws IOException, ClassNotFoundException {
        return load(DEFAULT_SAVE_PATH);
    }

    /**
     * Loads a context from disk using Java deserialization.
     * 
     * @param filePath The path to the file to load
     * @return The loaded Context instance
     * @throws IOException If reading fails
     * @throws ClassNotFoundException If the class cannot be found
     */
    public static Context load(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            Context context = (Context) ois.readObject();
            Logger.info("Contexto carregado de: {}", filePath);
            return context;
        }
    }
}

