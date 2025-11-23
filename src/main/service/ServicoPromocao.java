package main.service;

import main.models.Arigo;
import main.models.Avaliacao;
import main.models.Critico;

public class ServicoPromocao {

    //Define meta de likes necessária para ser promovido.(RF-4)
    private static final int LIMITE_LIKES = 10;

    /**
     * Verifica se um Arigo atingiu a meta de likes e o promove a Critico.
     * @param arigo O usuário que deseja ser promovido.
     * @return Um novo objeto Critico com os mesmos dados, ou null se não tiver likes suficientes.
     */

    public Critico tentarPromover(Arigo arigo) {
        int totalLikes = 0;

        //Soma todos os likes obtidos em todas as avaliacoes feitas pelo Arigo.
        for(Avaliacao avaliacao : arigo.getListaAvaliacoes()) {
            totalLikes += avaliacao.getLikes();
        }

        if(totalLikes >= LIMITE_LIKES) {
            //Passa as informacoes do usuario de um objeto Arigo para um objeto Crítico.
            Critico novoCritico = new Critico(arigo.getNome(), arigo.getDataNasc(), arigo.getEmail());

            novoCritico.setId(arigo.getId()); //mantém mesmo ID.

            return novoCritico;
        }

        return null;
    }
}
