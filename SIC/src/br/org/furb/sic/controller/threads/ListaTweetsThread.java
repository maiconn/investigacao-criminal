package br.org.furb.sic.controller.threads;

import java.util.concurrent.Semaphore;

import br.org.furb.sic.Config;
import br.org.furb.sic.model.ListaTweets;
import br.org.furb.sic.model.ListaTweetsValidos;
import br.org.furb.sic.view.Main;

/**
 * Thread para controlar a lista de tweets e iniciar threads de validação.
 */
public class ListaTweetsThread extends Thread {

	private ListaTweets listaTweets;
	private ListaTweetsValidos listaTweetsValidos;
	private Semaphore semaforo;

	private int qtdTweets = 0;

	public ListaTweetsThread(ListaTweets listaTweets) {
		this.semaforo = new Semaphore(Config.LIMITE_SEMAFORO_VALIDACAO_TWEET);
		this.listaTweets = listaTweets;
		listaTweetsValidos = new ListaTweetsValidos(semaforo);
	}

	@Override
	public void run() {
		Main.print("startou thread.");
		try {
			while (!listaTweets.isFimPesquisaTwitter() || !listaTweets.vazia()) {

				if (!listaTweets.vazia()) {
					semaforo.acquire();

					/**
					 * Iniciar uma thread se existir recurso para isso.
					 */
					qtdTweets++;
					ValidarTweetThread thread = new ValidarTweetThread(
							listaTweets.retirarTweet(), listaTweetsValidos,
							semaforo);

					thread.start();
				}
			}
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		} finally {
			Main.print("quantidade de tweets processados: " + qtdTweets);
			listaTweetsValidos.finalizar();
		}
	}

}
