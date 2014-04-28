package br.org.furb.sic.controller.threads;

import java.util.concurrent.Semaphore;

import twitter4j.Status;
import br.org.furb.sic.model.ListaTweetsValidos;
import br.org.furb.sic.view.Main;

public class ValidarTweetThread extends Thread {
	private Status tweet;
	private ListaTweetsValidos listaTweetsValidos;
	private Semaphore semaforo;

	public ValidarTweetThread(Status tweet,
			ListaTweetsValidos listaTweetsValidos, Semaphore semaforo) {
		this.tweet = tweet;
		this.listaTweetsValidos = listaTweetsValidos;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		Main.print(getClass(), "validando um tweet: " + tweet.getId());
		// TODO Validação do tweet e encaminhamento para a Lista de válidos.

		try {
			listaTweetsValidos.adicionarTweetValido(tweet);

			semaforo.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
