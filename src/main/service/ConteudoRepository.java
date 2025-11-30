package main.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import main.models.Conteudo;

/**
 * Repository especializado para conteúdos com métodos específicos.
 * 
 * @param <T> O tipo de conteúdo que estende Conteudo
 */
public class ConteudoRepository<T extends Conteudo> extends BaseRepository<T> {

    /**
     * Constructor that initializes the repository with a list of contents.
     * 
     * @param initialEntities The initial list of contents to populate the repository
     */
    public ConteudoRepository(List<T> initialEntities) {
        super(initialEntities);
    }

    /**
     * Retorna todos os conteúdos, opcionalmente filtrados por termo de busca.
     * Se searchParam for null ou vazio, retorna todos os conteúdos.
     * Caso contrário, filtra por conteúdos cujo título contenha o termo (case-insensitive).
     * 
     * @param searchParam O termo de busca para filtrar por título (pode ser null ou vazio)
     * @return Lista de conteúdos filtrados ou todos se searchParam for null/vazio
     */
    public List<T> findAll(String searchParam) {
        List<T> todos = findAll();
        
        if (searchParam == null || searchParam.trim().isEmpty()) {
            return todos;
        }
        
        String termoBusca = searchParam.trim().toLowerCase();
        return todos.stream()
                .filter(conteudo -> conteudo.getTitulo().toLowerCase().contains(termoBusca))
                .collect(Collectors.toList());
    }

    /**
     * Retorna os 3 conteúdos com mais avaliações.
     * 
     * @return Lista com os 3 conteúdos que possuem mais avaliações
     */
    public List<T> getDestaque() {
        return findAll().stream()
                .sorted(Comparator.comparingInt((T c) -> c.getAvaliacoes().size()).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}

