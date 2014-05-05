package br.org.furb.sic.controller.threads;

import java.util.concurrent.Semaphore;

import twitter4j.Status;
import br.org.furb.sic.Config;
import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.model.ListaTweetsValidos;
import br.org.furb.sic.view.Main;

public class ValidarTweetThread extends Thread {
	private Status tweet;
	private ListaTweetsValidos listaTweetsValidos;
	private Semaphore semaforo;
	private TwitterController tc = TwitterController.getInstance();

	public ValidarTweetThread(Status tweet,
			ListaTweetsValidos listaTweetsValidos, Semaphore semaforo) {
		this.tweet = tweet;
		this.listaTweetsValidos = listaTweetsValidos;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		Main.print("validando um tweet: " + tweet.getId());
		try {
			if (tc.isValidTweet(tweet) || Config.DEBUG) {
				listaTweetsValidos.adicionarTweetValido(tweet);
			}
			semaforo.release();
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		}

	}
}
