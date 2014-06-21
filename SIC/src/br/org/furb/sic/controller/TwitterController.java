package br.org.furb.sic.controller;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jpvm.jpvmEnvironment;
import jpvm.jpvmException;
import jpvm.jpvmTaskId;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import br.org.furb.sic.controller.omp.ListaVaziaException;
import br.org.furb.sic.controller.omp.OmpMostrarTweets_jomp;
import br.org.furb.sic.controller.omp.OmpValidacaoTweet_jomp;
import br.org.furb.sic.controller.pvm.Mestre;
import br.org.furb.sic.controller.pvm.Tag;
import br.org.furb.sic.model.ListaTweets;
import br.org.furb.sic.model.Tweet;
import br.org.furb.sic.util.StringUtil;
import br.org.furb.sic.view.Main;

public class TwitterController implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String TWITTER_CONSUMER_KEY = "9wCm1u34L3pLVYnvOiFIKPHSi";
	private final String TWITTER_SECRET_KEY = "SoqKvwPy2pi19twgc82qBENGOeuJhCkLQOXpebTbBYXkCdVNLk";
	private final String TWITTER_ACCESS_TOKEN = "2460706994-2ztrCLNxNYe574qQJBJVQdVjlFm2bOI3yxA8cdO";
	private final String TWITTER_ACCESS_TOKEN_SECRET = "qXt2Mm78vRTOahtWqyfqgVAWdpjH5augH6LztptZh5zQf";
	private final int TWEETS_TIME_LINE = 5;
	private Twitter twitter;
	private static TwitterController instance;
	private final int NUM_WORKERS = 45;

	private List<String> palavrasChave;

	public static TwitterController getInstance() {
		if (instance == null) {
			instance = new TwitterController();
		}
		return instance;
	}

	private TwitterController(String twitterConsumerKey,
			String twitterScretKey, String twitterAcessToken,
			String twitteAcessTokenSecret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
				.setOAuthConsumerSecret(TWITTER_SECRET_KEY)
				.setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
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
	
	public Twitter getTwitter(){
		return this.twitter;
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

	public boolean isValidTweet(Tweet tweet) {
		List<String> palavrasTweet = Arrays.asList(StringUtil
				.normalizarPadronizarSepararString(tweet.getTweet().getText()));
		for (String palavra : tweet.getPalavrasChave()) {
			if (!palavrasTweet.contains(palavra)) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidTweet(Status tweet) {
		List<String> palavrasTweet = Arrays.asList(StringUtil
				.normalizarPadronizarSepararString(tweet.getText()));
		for (String palavra : palavrasChave) {
			if (!palavrasTweet.contains(palavra)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public synchronized void buscaPalavraChaveOmp(String pesquisa) {
		this.palavrasChave = Arrays.asList(StringUtil
				.normalizarPadronizarSepararString(pesquisa));

		// Status[] vetorTweetsBruto;// = new Status[QTDE_TWEETS_BRUTOS];
		List listTweetsFiltrado = new ArrayList();// = new ArrayList();
		HashMap listaCincoUltimosTweets = new HashMap();
		HashMap listaPerfisFacebook = new HashMap();
		int qtdeTweetsBruto = 0;
		int qtdeTweetsValidos = 0;
		long inicio = System.currentTimeMillis();

		try {
			Query query = new Query(pesquisa);
			QueryResult result;

			// Lock jompLock = new Lock();

			// OmpValidacaoTweet_jomp ompValidacaoTweet = new
			// OmpValidacaoTweet_jomp(listTweetsFiltrado,
			// listaCincoUltimosTweets, listaPerfisFacebook);

			OmpValidacaoTweet_jomp ompValidacaoTweet = new OmpValidacaoTweet_jomp(
					listTweetsFiltrado, listaCincoUltimosTweets,
					listaPerfisFacebook);
			OmpMostrarTweets_jomp ompMostrarTweets = new OmpMostrarTweets_jomp(
					listTweetsFiltrado, listaCincoUltimosTweets,
					listaPerfisFacebook);
			do {
				// jompLock.set();
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				if (tweets.size() > 0) {
					listTweetsFiltrado.clear();

					qtdeTweetsBruto = ompValidacaoTweet.validaTweets(tweets,
							qtdeTweetsBruto);
					qtdeTweetsValidos = ompMostrarTweets
							.mostrarTweets(qtdeTweetsValidos);
				}
			} while ((query = result.nextQuery()) != null);

			// OmpMostrarTweets_jomp ompMostrarTweets = new
			// OmpMostrarTweets_jomp(listTweetsFiltrado,
			// listaCincoUltimosTweets, listaPerfisFacebook);
			// ompMostrarTweets.mostrarTweets(qtdeTweetsBruto);
		} catch (TwitterException ex) {
			System.out.println("\n\nQuantidade total de tweets encontrados: "
					+ qtdeTweetsBruto + "\nQuantidade de tweets válidos: "
					+ qtdeTweetsValidos);
			Main.tratarExcessao(ex);
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		}
		System.out.println("\n\nQuantidade total de tweets encontrados: "
				+ qtdeTweetsBruto + "\nQuantidade de tweets válidos: "
				+ qtdeTweetsValidos);
		long fim = System.currentTimeMillis();

		Main.print("Tempo de execução: "
				+ (new SimpleDateFormat("mm:ss").format(new Date(fim - inicio))));

	}

	public List<Status> cincoUltimosTweetsUsuario(Status tweet)
			throws TwitterException, ListaVaziaException {
		List<Status> cincoUltimosTweets;
		cincoUltimosTweets = twitter.getUserTimeline(tweet.getUser().getId());

		if (cincoUltimosTweets.size() == 0) {
			throw new ListaVaziaException();
		}
		try {
			return cincoUltimosTweets.subList(0, 5);
		} catch (IndexOutOfBoundsException e) {
			return cincoUltimosTweets.subList(0, cincoUltimosTweets.size() - 1);
		}
	}

	public String cincoUltimosTweetsUsuario(long idUsuario) {
		int i = 0;
		String result = "";

		try {
			List<Status> cincoUltimosTweets = twitter.getUserTimeline(idUsuario);

			for (i = 0; i < 5; i++) {
				result += " -> "
						+ ((Status) cincoUltimosTweets.get(i)).getText() + "\n";
			}
		} catch (IndexOutOfBoundsException e) {
			if (i == 0) {
				result = "Nenhum tweet recente.\n";
			}
		} catch (TwitterException ex) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			ex.printStackTrace(pw);
//			String stackTrace = sw.toString();
//			result = result + stackTrace;
			 result =
			 "Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.\n";
		}
		return result;
	}

	public void buscaPalavraChavePvm(String pesquisa) {
		this.palavrasChave = Arrays.asList(StringUtil
				.normalizarPadronizarSepararString(pesquisa));
		int qtdeTweetsBruto = 0;
		int qtdeTweetsValidos = 0;
		long inicio = System.currentTimeMillis();

		try {
			Query query = new Query(pesquisa);
			QueryResult result;			

			do {
				
				jpvmEnvironment jpvm = new jpvmEnvironment();
				jpvmTaskId tids[] = new jpvmTaskId[NUM_WORKERS];
				jpvm.pvm_spawn("br.org.furb.sic.controller.pvm.Escravo",
						NUM_WORKERS, tids);
//				for (int i = 0; i < NUM_WORKERS; i++)
//					System.out.println("\t" + tids[i].toString());
				
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				Map<Long, Tweet> tweetsRecebidos = new HashMap<Long, Tweet>();
				if (tweets.size() > 0) {

					// ENVIA AOS ESCRAVOS
					int proxTID = -1;
					for (Status tweet : tweets) {
						qtdeTweetsBruto++;
						Tweet tweetWrapper = new Tweet(tweet,
								this.palavrasChave, instance);
						tweetsRecebidos.put(tweet.getId(), tweetWrapper);

						proxTID++;
						jpvmTaskId tid1 = tids[proxTID];
						proxTID++;
						jpvmTaskId tid2 = tids[proxTID];
						proxTID++;
						jpvmTaskId tid3 = tids[proxTID];

						Mestre.enviar(tweetWrapper, Tag.ENVIAR_VALIDAR, jpvm,
								tid1);
						Mestre.enviar(tweetWrapper, Tag.ENVIAR_BUSCA_TWEETS,
								jpvm, tid2);
						Mestre.enviar(tweetWrapper, Tag.ENVIAR_BUSCA_FACEBOOK,
								jpvm, tid3);
					}

					// ENVIA RECEBE DOS ESCRAVOS
					for (int i = 0; i < tweets.size(); i++) {
						Mestre.receber(tweetsRecebidos, jpvm);
						Mestre.receber(tweetsRecebidos, jpvm);
						Mestre.receber(tweetsRecebidos, jpvm);
					}

					// IMPRIME NA TELA
					for (Tweet tweet : tweetsRecebidos.values()) {
						if (tweet.isValido()) {
							qtdeTweetsValidos++;
							System.out.println(tweet.toString());
						}
					}

//					break;
				}
			} while ((query = result.nextQuery()) != null);

		} catch (TwitterException ex) {
			System.out.println("\n\nQuantidade total de tweets encontrados: "
					+ qtdeTweetsBruto + "\nQuantidade de tweets válidos: "
					+ qtdeTweetsValidos);
			Main.tratarExcessao(ex);
		} catch (jpvmException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		}
		System.out.println("\n\nQuantidade total de tweets encontrados: "
				+ qtdeTweetsBruto + "\nQuantidade de tweets válidos: "
				+ qtdeTweetsValidos);
		long fim = System.currentTimeMillis();

		System.out
				.println("Tempo de execução: "
						+ (new SimpleDateFormat("mm:ss").format(new Date(fim
								- inicio))));

	}

}
