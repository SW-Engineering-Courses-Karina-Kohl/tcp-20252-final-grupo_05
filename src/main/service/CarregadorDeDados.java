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
import main.models.*;

public class CarregadorDeDados {
    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd");




    /**
     * Lê um arquivo do classpath e retorna um BufferedReader pra ele.
     */
    private BufferedReader lerArquivo(String caminho) {
        InputStream fluxoEntrada = getClass().getClassLoader().getResourceAsStream(caminho);
        if (fluxoEntrada == null) {
            throw new RuntimeException("Arquivo não encontrado: " + caminho);
        }
        return new BufferedReader(new InputStreamReader(fluxoEntrada, StandardCharsets.UTF_8));
    }

    /**
     * Lê o arquivo usuarios.csv e retorna a lista de Pessoas (Arigo ou Critico).
     */

    public List<Pessoa> carregarUsuarios() {
        List<Pessoa> listaDeUsuarios = new ArrayList<>();

        try (BufferedReader leitor = lerArquivo("data/usuarios.csv")){
            String linhaCSV;

            while((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                //dados do usuário
                String tipoUsuario = dados[0];
                String nome = dados[1];
                String email = dados[2];

                LocalDate dataNasc = LocalDate.parse(dados[3], FORMATO_DATA);

                if (tipoUsuario.equalsIgnoreCase("ARIGO")) {
                    listaDeUsuarios.add(new Arigo(nome, dataNasc, email));
                } else if (tipoUsuario.equalsIgnoreCase("CRITICO")) {
                    listaDeUsuarios.add(new Critico(nome, dataNasc, email));
                }

            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar usuarios: " + e.getMessage());
        }
        return listaDeUsuarios;
    }

    /**
     * Organiza o carregamento de todos os tipos de conteúdo. (filmes, jogos, etc.)
     */

    public List<Conteudo> carregarTodosConteudos() {
        List<Conteudo> catalogoDeConteudos = new ArrayList<>();
        catalogoDeConteudos.addAll(carregarFilmes());
        catalogoDeConteudos.addAll(carregarLivros());
        catalogoDeConteudos.addAll(carregarJogos());
        catalogoDeConteudos.addAll(carregarSeriesCompletas());
        
        return catalogoDeConteudos;
    }

    private List<Filme> carregarFilmes() {
        List<Filme> filmes = new ArrayList<>();

        try (BufferedReader leitor = lerArquivo("data/filmes.csv")) {
            String linhaCSV;

            while ((linhaCSV = leitor.readLine()) != null) {

                String[] dados = linhaCSV.split(SEPARADOR);
                if (dados.length < 5) continue;

                filmes.add(new Filme(
                    dados[1], 
                    LocalDate.parse(dados[2], FORMATO_DATA), 
                    Integer.parseInt(dados[3]), 
                    dados[4]));
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar filmes: " + e.getMessage());
        }
        return filmes;
    }

    private List<Livro> carregarLivros() {
        List<Livro> livros = new ArrayList<>();
        try (BufferedReader leitor = lerArquivo("data/livros.csv")) {
            String linhaCSV;

            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < 6) continue;
                livros.add(new Livro(
                    dados[1], 
                    LocalDate.parse(dados[2], FORMATO_DATA), 
                    dados[3], 
                    dados[4], 
                    Integer.parseInt(dados[5])));
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar livros: " + e.getMessage());
        }
        return livros;
    }

    private List<Jogo> carregarJogos() {
        List<Jogo> jogos = new ArrayList<>();

        try (BufferedReader leitor = lerArquivo("data/jogos.csv")) {
            String linhaCSV;

            while ((linhaCSV = leitor.readLine()) != null) {

                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < 5) continue;

                jogos.add(new Jogo(
                    dados[1], 
                    LocalDate.parse(dados[2], FORMATO_DATA), 
                    dados[3], 
                    dados[4])
                );
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar jogos: " + e.getMessage());
        }
        return jogos;
    }

    /**
     * Carrega séries e episódios criando temporadas.
     */
    private List<Serie> carregarSeriesCompletas() {
        Map<Integer, Serie> mapaSeries = new HashMap<>();

        // Primeiro, lê as Séries.
        try (BufferedReader leitor = lerArquivo("data/series.csv")) {
            String linhaCSV;

            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < 4) continue;
                
                int id = Integer.parseInt(dados[1]);
                Serie serie = new Serie(dados[2], LocalDate.parse(dados[3], FORMATO_DATA));
                
                mapaSeries.put(id, serie);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar séries: " + e.getMessage());
        }

        // Depois, lê os episodios e encaixa nas séries corretas.
        try (BufferedReader leitor = lerArquivo("data/episodios.csv")) {
            String linhaCSV;
            while ((linhaCSV = leitor.readLine()) != null) {
                String[] dados = linhaCSV.split(SEPARADOR);

                if (dados.length < 5) continue;
                
                int idSerie = Integer.parseInt(dados[0]);
                int numTemporada = Integer.parseInt(dados[1]);
                String tituloEpisodio = dados[3];
                int duracao = Integer.parseInt(dados[4]);

                Serie serieAlvo = mapaSeries.get(idSerie);
                if (serieAlvo != null) {
                    // Garante que a temporada existe antes de adicionar um episódio
                    garantirTemporadas(serieAlvo, numTemporada);
                    
                    // Pega a temporada certa
                    Temporada temporada = serieAlvo.getTemporadas().get(numTemporada - 1);
                    temporada.adicionarEpisodio(new Episodio(tituloEpisodio, duracao));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar episódios: " + e.getMessage());
        }

        return new ArrayList<>(mapaSeries.values());
    }

    // Garante que a série tenha pelo menos o número de temporadas especificado.
    private void garantirTemporadas(Serie serie, int numeroDeTemporadas) {
        while (serie.getTemporadas().size() < numeroDeTemporadas) {
            serie.adicionarTemporada(new Temporada());
        }
    }

}
