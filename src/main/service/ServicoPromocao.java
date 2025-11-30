package main.service;

import main.models.Arigo;
import main.models.Avaliacao;
import main.models.Critico;
import main.models.Pessoa;
import org.tinylog.Logger;

import java.util.UUID;

public class ServicoPromocao {

    //Define meta de likes necessária para ser promovido.(RF-4)
    private static final int LIMITE_LIKES = 10;

    /**
     * Verifica se um Arigo atingiu a meta de likes e o promove a Critico.
     * Busca o Arigo no context pelo ID, verifica se tem likes suficientes,
     * e se sim, substitui o Arigo por um Critico no repository.
     * 
     * @param context O contexto contendo os repositórios
     * @param idUsuario O ID do usuário dono da avaliação que recebeu like
     */
    public void tentarPromover(Context context, UUID idUsuario) {
        if (context == null) {
            throw new IllegalArgumentException("Context não pode ser nulo");
        }
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        // Busca a pessoa no context
        Pessoa pessoa = context.pessoas.getById(idUsuario);
        
        // Verifica se é um Arigo
        if (!(pessoa instanceof Arigo)) {
            Logger.debug("Usuário {} não é um Arigo, não pode ser promovido.", idUsuario);
            return;
        }

        Arigo arigo = (Arigo) pessoa;
        Logger.info("Iniciando tentativa de promoção para o usuário {}.", arigo.getId());

        int totalLikes = 0;

        //Soma todos os likes obtidos em todas as avaliacoes feitas pelo Arigo.
        for(Avaliacao avaliacao : arigo.getListaAvaliacoes()) {
            totalLikes += avaliacao.getLikes();
        }

        if (totalLikes >= LIMITE_LIKES) {
            //Passa as informacoes do usuario de um objeto Arigo para um objeto Crítico.
            // Mantém mesmo ID, data de criação e senha
            Critico novoCritico = new Critico(arigo.getId(), arigo.getDataCriacao(), arigo.getNome(), arigo.getDataNasc(), arigo.getEmail());
            // Preserva a senha do Arigo
            novoCritico.setSenhaHash(arigo.getSenhaHash());
            // Preserva as avaliações do Arigo (copia a lista de avaliações)
            novoCritico.getListaAvaliacoes().addAll(arigo.getListaAvaliacoes());

            // Substitui o Arigo pelo Critico no repository
            context.pessoas.delete(arigo.getId());
            context.pessoas.add(novoCritico);

            Logger.info("Usuário {} promovido a crítico. Total de likes: {}.", arigo.getId(), totalLikes);
        } else {
            Logger.debug("Usuário {} não atingiu o limite de likes para promoção ({} / {}).", arigo.getId(), totalLikes, LIMITE_LIKES);
        }
    }
}
