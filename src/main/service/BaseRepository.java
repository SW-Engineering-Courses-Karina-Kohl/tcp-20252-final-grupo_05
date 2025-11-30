package main.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import main.models.ContextEntity;

/**
 * Base repository class for in-memory data storage and retrieval.
 * Provides basic CRUD operations similar to .NET DbContext pattern.
 * 
 * @param <T> The entity type that must have a getId() method returning UUID
 */
public class BaseRepository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<T> entities;

    /**
     * Constructor that initializes the repository with a list of entities.
     * 
     * @param initialEntities The initial list of entities to populate the repository
     */
    public BaseRepository(List<T> initialEntities) {
        this.entities = new ArrayList<>(initialEntities);
    }

    /**
     * Adds a new entity to the repository.
     * 
     * @param entity The entity to add
     */
    public void add(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        entities.add(entity);
    }

    /**
     * Retrieves an entity by its UUID identifier.
     * Assumes the entity has a getId() method that returns UUID.
     * 
     * @param id The UUID of the entity to find
     * @return The entity with the matching ID, or null if not found
     */
    public T getById(UUID id) {
        if (id == null) {
            return null;
        }
        
        for (T entity : entities) {
            try {
                // Use reflection to call getId() method
                java.lang.reflect.Method getIdMethod = entity.getClass().getMethod("getId");
                UUID entityId = (UUID) getIdMethod.invoke(entity);
                if (id.equals(entityId)) {
                    return entity;
                }
            } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                // If getId() doesn't exist or fails, skip this entity
            }
        }
        return null;
    }

    /**
     * Deletes an entity from the repository by its UUID identifier.
     * 
     * @param id The UUID of the entity to delete
     * @return true if the entity was found and removed, false otherwise
     */
    public boolean delete(UUID id) {
        T entity = getById(id);
        if (entity != null) {
            return entities.remove(entity);
        }
        return false;
    }

    /**
     * Returns all entities in the repository as a mutable list reference.
     * 
     * @return The list of all entities (mutable reference)
     */
    public List<T> getAll() {
        return entities;
    }

    /**
     * Alias for getAll() to provide .NET-like API (findAll).
     * 
     * @return The list of all entities (mutable reference)
     */
    public List<T> findAll() {
        return getAll();
    }

    /**
     * Retorna a entidade mais recente baseada na data de criação.
     * A entidade deve ser uma instância de ContextEntity ou ter um método getDataCriacao().
     * 
     * @return A entidade com a data de criação mais recente, ou null se não houver entidades ou nenhuma tiver data de criação
     */
    public T getMaisRecente() {
        if (entities.isEmpty()) {
            return null;
        }

        T maisRecente = null;
        LocalDate dataMaisRecente = null;

        for (T entity : entities) {
            LocalDate dataCriacao = null;

            // Verifica se é instância de ContextEntity
            if (entity instanceof ContextEntity) {
                dataCriacao = ((ContextEntity) entity).getDataCriacao();
            } else {
                // Tenta usar reflection para chamar getDataCriacao()
                try {
                    java.lang.reflect.Method getDataCriacaoMethod = entity.getClass().getMethod("getDataCriacao");
                    dataCriacao = (LocalDate) getDataCriacaoMethod.invoke(entity);
                } catch (Exception e) {
                    // Se não tiver o método, pula esta entidade
                    continue;
                }
            }

            // Se encontrou uma data de criação e é mais recente que a atual
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

