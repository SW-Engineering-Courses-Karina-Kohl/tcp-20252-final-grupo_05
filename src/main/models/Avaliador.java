package main.models;

public interface Avaliador {

    /*
    * @param conteudo(Filme, Serie, etc) a ser avaliado
    * @param nota valor numerico para avaliacao
    * @param comentario texto sobre o conteudo
    * @return um objeto avaliacao contendo os dados da avaliacao
    */
    Avaliacao avaliar(Conteudo conteudo, int nota, String comentario);
}