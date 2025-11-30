package main.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Base repository class for in-memory data storage and retrieval.
 * Provides basic CRUD operations similar to .NET DbContext pattern.
 * 
 * @param <T> The entity type that must have a getId() method returning UUID
 */
public class BaseRepository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> entities;

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
            } catch (Exception e) {
                // If getId() doesn't exist or fails, skip this entity
                continue;
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
}

