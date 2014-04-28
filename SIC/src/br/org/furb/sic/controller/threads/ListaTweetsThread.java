package br.org.furb.sic.controller.threads;

import java.util.concurrent.Semaphore;

import br.org.furb.sic.model.ListaTweets;
import br.org.furb.sic.model.ListaTweetsValidos;
import br.org.furb.sic.view.Main;

/**
 * Thread para controlar a lista de tweets e iniciar threads de validação.
 */
public class ListaTweetsThread extends Thread {
	public static final int LIMITE_THREAD = 20;
	
	private ListaTweets listaTweets;
	private ListaTweetsValidos listaTweetsValidos;
	private Semaphore semaforo;
	
	private int qtdTweets = 0;
	

	public ListaTweetsThread(ListaTweets listaTweets) {
		this.semaforo = new Semaphore(LIMITE_THREAD);
		this.listaTweets = listaTweets;
		listaTweetsValidos = new ListaTweetsValidos(semaforo);
	}

	@Override
	public void run() {
		Main.print(getClass(), "startou thread.");
		while (!listaTweets.isFimPesquisaTwitter() || !listaTweets.vazia()) {
			try {
				if (!listaTweets.vazia()) {
					semaforo.acquire();

					/**
					 * Iniciar uma thread se existir recurso para isso.
					 */
					qtdTweets++;
					ValidarTweetThread thread = new ValidarTweetThread(
							listaTweets.retirarTweet(), listaTweetsValidos, semaforo);
					
					thread.start();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Main.print(getClass(), "quantidade de tweets processados: " + qtdTweets);
		listaTweetsValidos.finalizar();
	}

}
