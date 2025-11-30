package main.service;

import main.models.Arigo;
import main.models.Avaliacao;
import main.models.Critico;
import org.tinylog.Logger;

public class ServicoPromocao {

    //Define meta de likes necessária para ser promovido.(RF-4)
    private static final int LIMITE_LIKES = 10;

    /**
     * Verifica se um Arigo atingiu a meta de likes e o promove a Critico.
     * @param arigo O usuário que deseja ser promovido.
     * @return Um novo objeto Critico com os mesmos dados, ou null se não tiver likes suficientes.
     */

    public Critico tentarPromover(Arigo arigo) {
        Logger.info("Iniciando tentativa de promoção para o usuário {}.", arigo.getId());

        int totalLikes = 0;

        //Soma todos os likes obtidos em todas as avaliacoes feitas pelo Arigo.
        for(Avaliacao avaliacao : arigo.getListaAvaliacoes()) {
            totalLikes += avaliacao.getLikes();
        }

        if (totalLikes >= LIMITE_LIKES) {
            //Passa as informacoes do usuario de um objeto Arigo para um objeto Crítico.
            // Mantém mesmo ID e data de criação
            Critico novoCritico = new Critico(arigo.getId(), arigo.getDataCriacao(), arigo.getNome(), arigo.getDataNasc(), arigo.getEmail());

            Logger.info("Usuário {} promovido a crítico. Total de likes: {}.", arigo.getId(), totalLikes);

            return novoCritico;
        }
        Logger.warn("Usuário {} não atingiu o limite de likes para promoção ({} / {}).", arigo.getId(), totalLikes, LIMITE_LIKES);
        return null;
    }
}
