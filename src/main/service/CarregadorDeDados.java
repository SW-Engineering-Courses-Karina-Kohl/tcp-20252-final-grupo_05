package main.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.tinylog.Logger;
import main.models.*;

public class CarregadorDeDados {

    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // --- Constantes para Caminhos de Arquivos ---
    private static final String CAMINHO_USUARIOS = "data/usuarios.csv";
    private static final String CAMINHO_FILMES = "data/filmes.csv";
    private static final String CAMINHO_LIVROS = "data/livros.csv";
    private static final String CAMINHO_JOGOS = "data/jogos.csv";
    private static final String CAMINHO_SERIES = "data/series.csv";
    private static final String CAMINHO_EPISODIOS = "data/episodios.csv";

    // --- Constantes para Índices do CSV de USUÁRIOS ---
    private static final int COL_USER_TIPO = 0;
    private static final int COL_USER_NOME = 1;
    private static final int COL_USER_EMAIL = 2;
    private static final int COL_USER_DATA = 3;
    private static final String TIPO_ARIGO = "ARIGO";
    private static final String TIPO_CRITICO = "CRITICO";

    // --- Constantes para Índices do CSV de FILMES ---
    // (Assume-se que o índice 0 seja um ID ignorado, já que começavas do 1)
    private static final int COL_FILME_TITULO = 1;
    private static final int COL_FILME_DATA = 2;
    private static final int COL_FILME_DURACAO = 3; 
    private static final int COL_FILME_DIRETOR = 4;
    private static final int MIN_COLUNAS_FILME = 5;

    // --- Constantes para Índices do CSV de LIVROS ---
    private static final int COL_LIVRO_TITULO = 1;
    private static final int COL_LIVRO_DATA = 2;
    private static final int COL_LIVRO_EDITORA = 3;
    private static final int COL_LIVRO_AUTOR = 4;
    private static final int COL_LIVRO_PAGINAS = 5;
    private static final int MIN_COLUNAS_LIVRO = 6;

    // --- Constantes para Índices do CSV de JOGOS ---
    private static final int COL_JOGO_TITULO = 1;
    private static final int COL_JOGO_DATA = 2;
    private static final int COL_JOGO_PUBLISHER = 3;
    private static final int COL_JOGO_PLATAFORMA = 4;
    private static final int MIN_COLUNAS_JOGO = 5;

    // --- Constantes para SÉRIES e EPISÓDIOS ---
    private static final int COL_SERIE_ID = 1;
    private static final int COL_SERIE_TITULO = 2;
    private static final int COL_SERIE_DATA = 3;
    private static final int MIN_COLUNAS_SERIE = 4;

    private static final int COL_EP_ID_SERIE = 0;
    private static final int COL_EP_NUM_TEMP = 1;
    private static final int COL_EP_TITULO = 3;
    private static final int COL_EP_DURACAO = 4;
    private static final int MIN_COLUNAS_EPISODIO = 5;


    /**
     * Lê um arquivo do classpath e retorna um BufferedReader.
     */
    private BufferedReader lerArquivo(String caminho) {
        InputStream fluxoEntrada = getClass().getClassLoader().getResourceAsStream(caminho);
        if (fluxoEntrada == null) {
            Logger.error("Arquivo não encontrado no classpath: {}", caminho);
            throw new RuntimeException("Arquivo não encontrado: " + caminho);
        }
        return new BufferedReader(new InputStreamReader(fluxoEntrada, StandardCharsets.UTF_8));
    }

    public List<Pessoa> carregarUsuarios() {
        List<Pessoa> listaDeUsuarios = new ArrayList<>();
        Logger.info("Iniciando carregamento de usuários a partir de '{}'.", CAMINHO_USUARIOS);

        try (BufferedReader leitor = lerArquivo(CAMINHO_USUARIOS)){
            String linhaCSV;
            while((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                // Agora usamos nomes significativos em vez de números soltos
                String tipoUsuario = dados[COL_USER_TIPO];
                String nome = dados[COL_USER_NOME];
                String email = dados[COL_USER_EMAIL];
                LocalDate dataNasc = LocalDate.parse(dados[COL_USER_DATA], FORMATO_DATA);

                if (tipoUsuario.equalsIgnoreCase(TIPO_ARIGO)) {
                    listaDeUsuarios.add(new Arigo(nome, dataNasc, email));
                } else if (tipoUsuario.equalsIgnoreCase(TIPO_CRITICO)) {
                    listaDeUsuarios.add(new Critico(nome, dataNasc, email));
                }
            }
        } catch (Exception e) {
            Logger.error(e, "Erro ao carregar usuários.");
        }
        return listaDeUsuarios;
    }

    public List<Conteudo> carregarTodosConteudos() {
        Logger.info("Carregando todos os conteúdos: filmes, livros, jogos e séries.");
        List<Conteudo> catalogoDeConteudos = new ArrayList<>();
        catalogoDeConteudos.addAll(carregarFilmes());
        catalogoDeConteudos.addAll(carregarLivros());
        catalogoDeConteudos.addAll(carregarJogos());
        catalogoDeConteudos.addAll(carregarSeriesCompletas());
        return catalogoDeConteudos;
    }

    public List<Filme> carregarFilmes() {
        List<Filme> filmes = new ArrayList<>();
        Logger.info("Iniciando carregamento de filmes a partir de '{}'.", CAMINHO_FILMES);

        try (BufferedReader leitor = lerArquivo(CAMINHO_FILMES)) {
            String linhaCSV;
            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);
                
                // Validação usando constante
                if (dados.length < MIN_COLUNAS_FILME) continue;

                filmes.add(new Filme(
                    dados[COL_FILME_TITULO], 
                    LocalDate.parse(dados[COL_FILME_DATA], FORMATO_DATA), 
                    Integer.parseInt(dados[COL_FILME_DURACAO]), 
                    dados[COL_FILME_DIRETOR]));
            }
        } catch (Exception e) {
            Logger.error(e, "Erro ao carregar filmes.");
        }
        return filmes;
    }

    public List<Livro> carregarLivros() {
        List<Livro> livros = new ArrayList<>();
        Logger.info("Iniciando carregamento de livros a partir de '{}'.", CAMINHO_LIVROS);

        try (BufferedReader leitor = lerArquivo(CAMINHO_LIVROS)) {
            String linhaCSV;
            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < MIN_COLUNAS_LIVRO) continue;
                
                livros.add(new Livro(
                    dados[COL_LIVRO_TITULO], 
                    LocalDate.parse(dados[COL_LIVRO_DATA], FORMATO_DATA), 
                    dados[COL_LIVRO_EDITORA], 
                    dados[COL_LIVRO_AUTOR], 
                    Integer.parseInt(dados[COL_LIVRO_PAGINAS])));
            }
        } catch (Exception e) {
            Logger.error(e, "Erro ao carregar livros.");
        }
        return livros;
    }

    public List<Jogo> carregarJogos() {
        List<Jogo> jogos = new ArrayList<>();
        Logger.info("Iniciando carregamento de jogos a partir de '{}'.", CAMINHO_JOGOS);

        try (BufferedReader leitor = lerArquivo(CAMINHO_JOGOS)) {
            String linhaCSV;
            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < MIN_COLUNAS_JOGO) continue;

                jogos.add(new Jogo(
                    dados[COL_JOGO_TITULO], 
                    LocalDate.parse(dados[COL_JOGO_DATA], FORMATO_DATA), 
                    dados[COL_JOGO_PUBLISHER], 
                    dados[COL_JOGO_PLATAFORMA])
                );
            }
        } catch (Exception e) {
            Logger.error(e, "Erro ao carregar jogos.");
        }
        return jogos;
    }

    public List<Serie> carregarSeriesCompletas() {
        Map<Integer, Serie> mapaSeries = new HashMap<>();
        Logger.info("Iniciando carregamento de séries e episódios.");

        // Carregar Séries
        try (BufferedReader leitor = lerArquivo(CAMINHO_SERIES)) {
            String linhaCSV;
            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < MIN_COLUNAS_SERIE) continue;
                
                int id = Integer.parseInt(dados[COL_SERIE_ID]);
                Serie serie = new Serie(
                    dados[COL_SERIE_TITULO], 
                    LocalDate.parse(dados[COL_SERIE_DATA], FORMATO_DATA)
                );
                
                mapaSeries.put(id, serie);
            }
        } catch (Exception e) {
            Logger.error(e, "Erro ao carregar séries.");
        }

        // Carregar Episódios
        try (BufferedReader leitor = lerArquivo(CAMINHO_EPISODIOS)) {
            String linhaCSV;
            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < MIN_COLUNAS_EPISODIO) continue;
                
                int idSerie = Integer.parseInt(dados[COL_EP_ID_SERIE]);
                int numTemporada = Integer.parseInt(dados[COL_EP_NUM_TEMP]);
                String tituloEpisodio = dados[COL_EP_TITULO];
                int duracao = Integer.parseInt(dados[COL_EP_DURACAO]);

                Serie serieAlvo = mapaSeries.get(idSerie);
                if (serieAlvo != null) {
                    garantirTemporadas(serieAlvo, numTemporada);
                    
                    // Listas começam em 0, mas temporadas em 1.
                    // Lógica de conversão.
                    int indiceDaTemporada = numTemporada - 1; 
                    Temporada temporada = serieAlvo.getTemporadas().get(indiceDaTemporada);
                    
                    temporada.adicionarEpisodio(new Episodio(tituloEpisodio, duracao));
                }
            }
        } catch (Exception e) {
            Logger.error(e, "Erro ao carregar episódios.");
        }

        List<Serie> series = new ArrayList<>(mapaSeries.values());
        Logger.info("Carregamento concluído. Total: {}.", series.size());
        return series;
    }

    private void garantirTemporadas(Serie serie, int numeroDeTemporadas) {
        while (serie.getTemporadas().size() < numeroDeTemporadas) {
            serie.adicionarTemporada(new Temporada());
        }
    }
}