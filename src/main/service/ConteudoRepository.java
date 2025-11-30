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

