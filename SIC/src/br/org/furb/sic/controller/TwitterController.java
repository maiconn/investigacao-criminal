package br.org.furb.sic.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import br.org.furb.sic.Config;
import br.org.furb.sic.model.ListaTweets;
import br.org.furb.sic.util.StringUtil;
import br.org.furb.sic.view.BuscaPalavrasChaveView;
import br.org.furb.sic.view.Main;

public class TwitterController {
	private Twitter twitter;
	private List<String> palavrasChave;

	private static TwitterController instance;

	public static TwitterController getInstance() {
		if (instance == null) {
			instance = new TwitterController();
		}
		return instance;
	}

	private TwitterController() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(
						Config.TwitterConfiguration.TWITTER_CONSUMER_KEY)
				.setOAuthConsumerSecret(
						Config.TwitterConfiguration.TWITTER_SECRET_KEY)
				.setOAuthAccessToken(
						Config.TwitterConfiguration.TWITTER_ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(
						Config.TwitterConfiguration.TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	public void buscaPalavraChave(String pesquisa)  {
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
		} catch (Exception e) {
			Main.tratarExcessao(e);		
		} finally {
			lista.finalizar();
		}
	}

	public void mostrarInformacoesUsuario(Status tweet) throws TwitterException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		BuscaPalavrasChaveView.resultadoPrintln("Usuario: @"
				+ tweet.getUser().getScreenName());
		BuscaPalavrasChaveView.resultadoPrintln("Descrição: "
				+ tweet.getUser().getDescription());
		BuscaPalavrasChaveView.resultadoPrintln("Localização: "
				+ tweet.getUser().getLocation());
		BuscaPalavrasChaveView.resultadoPrintln("Nome: "
				+ tweet.getUser().getName());
		BuscaPalavrasChaveView
				.resultadoPrintln(sdf.format(tweet.getCreatedAt()) + " - "
						+ tweet.getText());

		List<Status> statusUser = twitter.getUserTimeline(tweet.getUser()
				.getId());
		if (!statusUser.isEmpty()) {
			BuscaPalavrasChaveView.resultadoPrintln("[Últimos 5 Tweets]");
			try {
				int count = 0;
				Status status = null;
				while ((status = statusUser.get(count)) != null
						&& count < Config.TWEETS_TIME_LINE) {
					BuscaPalavrasChaveView.resultadoPrintln(" -> "
							+ status.getText());
					count++;
				}
			} catch (IndexOutOfBoundsException ex) {

			}
		}
		BuscaPalavrasChaveView.resultadoPrintln("");
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
}
