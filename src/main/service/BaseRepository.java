package main.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import main.models.ContextEntity;

/**
 * Repositório base para armazenamento e recuperação de dados em memória.
 * 
 * @param <T> Tipo da entidade que deve ter um método getId() retornando UUID
 */
public class BaseRepository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<T> entities;

    /**
     * Inicializa o repositório com uma lista de entidades.
     * 
     * @param initialEntities Lista inicial de entidades
     */
    public BaseRepository(List<T> initialEntities) {
        this.entities = new ArrayList<>(initialEntities);
    }

    /**
     * Adiciona uma nova entidade ao repositório.
     * 
     * @param entity Entidade a adicionar
     */
    public void add(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        entities.add(entity);
    }

    /**
     * Busca uma entidade pelo seu UUID.
     * 
     * @param id UUID da entidade a buscar
     * @return Entidade com o ID correspondente, ou null se não encontrada
     */
    public T getById(UUID id) {
        if (id == null) {
            return null;
        }
        
        for (T entity : entities) {
            try {
                java.lang.reflect.Method getIdMethod = entity.getClass().getMethod("getId");
                UUID entityId = (UUID) getIdMethod.invoke(entity);
                if (id.equals(entityId)) {
                    return entity;
                }
            } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                // Pula esta entidade se não tiver getId()
            }
        }
        return null;
    }

    /**
     * Remove uma entidade do repositório pelo seu UUID.
     * 
     * @param id UUID da entidade a remover
     * @return true se a entidade foi encontrada e removida, false caso contrário
     */
    public boolean delete(UUID id) {
        T entity = getById(id);
        if (entity != null) {
            return entities.remove(entity);
        }
        return false;
    }

    /**
     * Retorna todas as entidades do repositório.
     * 
     * @return Lista de todas as entidades
     */
    public List<T> getAll() {
        return entities;
    }

    /**
     * Alias para getAll().
     * 
     * @return Lista de todas as entidades
     */
    public List<T> findAll() {
        return getAll();
    }

    /**
     * Retorna a entidade mais recente baseada na data de criação.
     * 
     * @return Entidade com a data de criação mais recente, ou null se não houver
     */
    public T getMaisRecente() {
        if (entities.isEmpty()) {
            return null;
        }

        T maisRecente = null;
        LocalDate dataMaisRecente = null;

        for (T entity : entities) {
            LocalDate dataCriacao = null;

            if (entity instanceof ContextEntity) {
                dataCriacao = ((ContextEntity) entity).getDataCriacao();
            } else {
                try {
                    java.lang.reflect.Method getDataCriacaoMethod = entity.getClass().getMethod("getDataCriacao");
                    dataCriacao = (LocalDate) getDataCriacaoMethod.invoke(entity);
                } catch (Exception e) {
                    continue;
                }
            }

            if (dataCriacao != null) {
                if (dataMaisRecente == null || dataCriacao.isAfter(dataMaisRecente)) {
                    dataMaisRecente = dataCriacao;
                    maisRecente = entity;
                }
            }
        }

        return maisRecente;
    }
}

