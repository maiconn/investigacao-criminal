package br.org.furb.sic;

import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.controller.threads.ListaTweetsThread;
import br.org.furb.sic.model.ListaTweets;
import br.org.furb.sic.model.ListaTweetsValidos;

/**
 * Interface para configurar todas as constantes do sistema.
 * 
 */
public interface Config {
	/**
	 * Debug da aplicação (mostrar ou não os logs)
	 */
	boolean DEBUG = false;

	/**
	 * Título do sistema na tela.
	 */
	String TITULO_SISTEMA = "SIC - Sistema de Investigação Criminal";

	/**
	 * Constante utilizada para controle do semátoro de validação de tweets da
	 * classe {@link ListaTweetsThread}
	 */
	int LIMITE_SEMAFORO_VALIDACAO_TWEET = 20;

	/**
	 * Determina o limite da lista de tweets.{@link ListaTweets}
	 */
	int LIMITE_LISTA_TWEETS = 100;
	
	/**;
	 * Determina o limite da lista de tweets válidos.{@link ListaTweetsValidos}
	 */
	int LIMITE_LISTA_TWEETS_VALIDOS = 100;

	/**
	 * Limite dos ultimos tweets do usuário. {@link TwitterController}
	 */
	int TWEETS_TIME_LINE = 5;

	/**
	 * Conjunto de configurações para acessar a API do twitter. {@link TwitterController}
	 */
	public interface TwitterConfiguration {
		public final String TWITTER_CONSUMER_KEY = "9wCm1u34L3pLVYnvOiFIKPHSi";
		public final String TWITTER_SECRET_KEY = "SoqKvwPy2pi19twgc82qBENGOeuJhCkLQOXpebTbBYXkCdVNLk";
		public final String TWITTER_ACCESS_TOKEN = "2460706994-2ztrCLNxNYe574qQJBJVQdVjlFm2bOI3yxA8cdO";
		public final String TWITTER_ACCESS_TOKEN_SECRET = "qXt2Mm78vRTOahtWqyfqgVAWdpjH5augH6LztptZh5zQf";
	}
}
