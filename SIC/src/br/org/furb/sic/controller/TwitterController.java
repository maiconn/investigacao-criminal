package br.org.furb.sic.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import jomp.runtime.Lock;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import br.org.furb.sic.controller.omp.OmpValidacaoTweet_jomp;
import br.org.furb.sic.model.ListaTweets;
import br.org.furb.sic.util.StringUtil;
import br.org.furb.sic.view.Main;

public class TwitterController {

	private final String TWITTER_CONSUMER_KEY = "9wCm1u34L3pLVYnvOiFIKPHSi";
	private final String TWITTER_SECRET_KEY = "SoqKvwPy2pi19twgc82qBENGOeuJhCkLQOXpebTbBYXkCdVNLk";
	private final String TWITTER_ACCESS_TOKEN = "2460706994-2ztrCLNxNYe574qQJBJVQdVjlFm2bOI3yxA8cdO";
	private final String TWITTER_ACCESS_TOKEN_SECRET = "qXt2Mm78vRTOahtWqyfqgVAWdpjH5augH6LztptZh5zQf";
	private final int TWEETS_TIME_LINE = 5;
	private Twitter twitter;
	private static TwitterController instance;

	private List<String> palavrasChave;

	public static TwitterController getInstance() {
		if (instance == null) {
			instance = new TwitterController();
		}
		return instance;
	}

	private TwitterController() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
				.setOAuthConsumerSecret(TWITTER_SECRET_KEY)
				.setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	public void buscaPalavraChave(String pesquisa) {
		this.palavrasChave = Arrays.asList(StringUtil
				.normalizarPadronizarSepararString(pesquisa));

		ListaTweets lista = new ListaTweets();
		try {
			Query query = new Query(pesquisa);
			QueryResult result;

			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					lista.insereTweet(tweet);
				}
			} while ((query = result.nextQuery()) != null);
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		} finally {
			lista.finalizar();
		}
	}

	public void mostrarInformacoesUsuario(Status tweet) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			System.out.println("Usuario: @" + tweet.getUser().getScreenName());
			System.out
					.println("Descrição: " + tweet.getUser().getDescription());
			System.out.println("Localização: " + tweet.getUser().getLocation());
			System.out.println("Nome: " + tweet.getUser().getName());
			System.out.println(sdf.format(tweet.getCreatedAt()) + " - "
					+ tweet.getText());

			List<Status> statusUser = twitter.getUserTimeline(tweet.getUser()
					.getId());
			if (!statusUser.isEmpty()) {
				System.out.println("[Últimos 5 Tweets]");
				try {
					int count = 0;
					Status status = null;
					while ((status = statusUser.get(count)) != null
							&& count < TWEETS_TIME_LINE) {
						System.out.println(" -> " + status.getText());
						count++;
					}
				} catch (IndexOutOfBoundsException ex) {

				}
			}
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		} 
		System.out.println();
	}

	public boolean isValidTweet(Status tweet) {
		List<String> palavrasTweet = Arrays.asList(StringUtil
				.normalizarPadronizarSepararString(tweet.getText()));
		// if(Main.DEBUG){
		// Main.print(getClass(),
		// "palavras da pesquisa:"+palavrasChave.toString());
		// Main.print(getClass(),
		// "palavras do tweet:"+palavrasTweet.toString());
		// }
		for (String palavra : palavrasChave) {
			if (!palavrasTweet.contains(palavra)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	public void buscaPalavraChaveOmp(String pesquisa) {
		this.palavrasChave = Arrays.asList(StringUtil
				.normalizarPadronizarSepararString(pesquisa));

		Status[] vetorTweetsBruto;// = new Status[QTDE_TWEETS_BRUTOS];
		try {
			Query query = new Query(pesquisa);
			QueryResult result;
			
			Lock jompLock = new Lock();
			OmpValidacaoTweet_jomp ompValidacaoTweet = new OmpValidacaoTweet_jomp();

			do {
				//jompLock.set();
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				if (tweets.size() > 0)
					ompValidacaoTweet.validaTweets(tweets, jompLock);
			} while ((query = result.nextQuery()) != null);
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		//} finally {
		//	lista.finalizar();
		}
	}
	
	
	
}
