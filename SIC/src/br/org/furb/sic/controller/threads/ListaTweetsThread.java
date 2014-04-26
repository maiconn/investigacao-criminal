package br.org.furb.sic.controller.threads;

import br.org.furb.sic.model.ListaTweets;
import br.org.furb.sic.model.ListaTweetsValidos;

/**
 * Thread para controlar a lista de tweets e iniciar threads de validação.
 */
public class ListaTweetsThread extends Thread {
	private ListaTweets listaTweets;
	private ListaTweetsValidos listaTweetsValidos;

	public ListaTweetsThread(ListaTweets listaTweets) {
		this.listaTweets = listaTweets;
		listaTweetsValidos = new ListaTweetsValidos();
	}

	@Override
	public void run() {

		while (!listaTweets.isFimPesquisaTwitter() || !listaTweets.vazia()) {
			try {
				if (!listaTweets.vazia()) {
					listaTweets.getSemaforo().acquire();

					/**
					 * Iniciar uma thread se existir recurso para isso.
					 */
					ValidarTweetThread thread = new ValidarTweetThread(
							listaTweets.retirarTweet(),
							listaTweets.getSemaforo(), listaTweetsValidos);
					thread.start();
				}

				// TODO falta finalizar Thread após acabar os tweets

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		listaTweetsValidos.finalizar();
	}

}
