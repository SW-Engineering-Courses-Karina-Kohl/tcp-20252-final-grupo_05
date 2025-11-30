package main.models;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SENHA_PADRAO = "senha123";

    private UUID id;
    private String nome;
    private LocalDate dataNasc;
    private String email;
    private String senhaHash;

    private List<Avaliacao> listaAvaliacoes;

    /**
     * Construtor com ID opcional. Se o ID for null, gera um UUID aleatório.
     * Define senha padrão se não fornecida.
     */
    public Pessoa(UUID id, String nome, LocalDate dataNasc, String email){
        this(id, nome, dataNasc, email, SENHA_PADRAO);
    }

    /**
     * Construtor com ID e senha.
     */
    public Pessoa(UUID id, String nome, LocalDate dataNasc, String email, String senha){
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.email = email;
        
        this.id = (id != null) ? id : UUID.randomUUID();
        this.senhaHash = hashSenha(senha);
        this.listaAvaliacoes = new ArrayList<>();
    }

    /**
     * Construtor sem ID. Gera um UUID aleatório automaticamente.
     * Define senha padrão se não fornecida.
     */
    public Pessoa(String nome, LocalDate dataNasc, String email){
        this(null, nome, dataNasc, email, SENHA_PADRAO);
    }

    /**
     * Construtor sem ID mas com senha.
     */
    public Pessoa(String nome, LocalDate dataNasc, String email, String senha){
        this(null, nome, dataNasc, email, senha);
    }

    // GETTERS E SETTERS

     public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    /**
     * Gera hash SHA-256 de uma senha.
     * 
     * @param senha A senha em texto plano
     * @return O hash hexadecimal da senha
     */
    public static String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(senha.getBytes());
            
            // Converter bytes para hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }
  
    protected void registrarAvaliacao(Avaliacao avaliacao) {
        this.listaAvaliacoes.add(avaliacao);
    }

    public List<Avaliacao> getListaAvaliacoes() {
        return listaAvaliacoes;
    }
}
