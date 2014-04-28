package br.org.furb.sic.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import br.org.furb.sic.model.ListaTweets;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterController {

	private final String TWITTER_CONSUMER_KEY = "9wCm1u34L3pLVYnvOiFIKPHSi";
	private final String TWITTER_SECRET_KEY = "SoqKvwPy2pi19twgc82qBENGOeuJhCkLQOXpebTbBYXkCdVNLk";
	private final String TWITTER_ACCESS_TOKEN = "2460706994-2ztrCLNxNYe574qQJBJVQdVjlFm2bOI3yxA8cdO";
	private final String TWITTER_ACCESS_TOKEN_SECRET = "qXt2Mm78vRTOahtWqyfqgVAWdpjH5augH6LztptZh5zQf";
	private final int TWEETS_TIME_LINE = 5;
	private Twitter twitter;
	private static TwitterController instance;

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

	public void buscaPalavraChave(String palavra) {
		ListaTweets lista = new ListaTweets();
		try {
			Query query = new Query(palavra);
			QueryResult result;

			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				// Se deixar rolar o foreach vai estourar excessão
				for (Status tweet : tweets) {
					// Inserir na lista
					lista.insereTweet(tweet);
					/**
					 * System.out.println("@" + tweet.getUser().getScreenName()
					 * + " |Description " + tweet.getUser().getDescription() +
					 * " - " + tweet.getText()); if(v)
					 * buscaUsuarioTimeLine(tweet.getUser().getId()); v = false;
					 */

				}
			} while ((query = result.nextQuery()) != null);

			// System.exit(0);
		} catch (TwitterException te) {
			if (te.getMessage().contains("code - 88")) {
				System.err
						.println("["
								+ te.getClass().getName()
								+ "] Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.");
			} else {
				te.printStackTrace();
				System.err.println("Failed to search tweets: "
						+ te.getMessage());
			}
			System.exit(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println("Failed insert tweet: " + e.getMessage());
			System.exit(-1);
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
		} catch (TwitterException te) {
			if (te.getMessage().contains("code - 88")) {
				System.err
						.println("["
								+ te.getClass().getName()
								+ "] Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.");
			} else {
				te.printStackTrace();
				System.err.println("Failed to search tweets: "
						+ te.getMessage());
			}
			System.exit(-1);
		}
		System.out.println();
	}

	public boolean isValidTweet(String texto, String palavra) {
		return false;
	}

}
