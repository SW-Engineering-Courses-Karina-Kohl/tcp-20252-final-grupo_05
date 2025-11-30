package main.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Classe base abstrata para todas as entidades que são armazenadas nos repositórios do Context.
 * Fornece campos comuns: id e dataCriacao.
 */
public abstract class ContextEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UUID id;
    private LocalDate dataCriacao;

    /**
     * Construtor com ID e data de criação opcionais.
     * Se o ID for null, gera um UUID aleatório.
     * Se a data de criação for null, usa a data atual.
     * 
     * @param id O ID da entidade (pode ser null para gerar automaticamente)
     * @param dataCriacao A data de criação (pode ser null para usar a data atual)
     */
    public ContextEntity(UUID id, LocalDate dataCriacao) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.dataCriacao = (dataCriacao != null) ? dataCriacao : LocalDate.now();
    }

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     * A data de criação é sempre definida como a data atual.
     * 
     * @param id O ID da entidade (pode ser null para gerar automaticamente)
     */
    public ContextEntity(UUID id) {
        this(id, null);
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     * A data de criação é sempre definida como a data atual.
     */
    public ContextEntity() {
        this(null, null);
    }

    /**
     * Retorna o ID da entidade.
     * 
     * @return O UUID da entidade
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retorna a data de criação da entidade.
     * 
     * @return A data de criação
     */
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}

