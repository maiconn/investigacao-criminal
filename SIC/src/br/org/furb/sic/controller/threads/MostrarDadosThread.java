package br.org.furb.sic.controller.threads;

import twitter4j.Status;
import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.model.ListaTweetsValidos;
import br.org.furb.sic.view.Main;

public class MostrarDadosThread extends Thread {
	private ListaTweetsValidos listaTweetsValidos;
	private TwitterController twitterController = TwitterController
			.getInstance();

	private int qtdTweetsProcessados = 0;

	public MostrarDadosThread(ListaTweetsValidos listaTweetsValidos) {
		this.listaTweetsValidos = listaTweetsValidos;
	}

	@Override
	public void run() {
		Main.print(getClass(), "startou thread.");
		while (!listaTweetsValidos.isFimDosTweets()
				|| !listaTweetsValidos.getLista().isEmpty()) {
			if (!listaTweetsValidos.getLista().isEmpty()) {
				try {
					qtdTweetsProcessados++;
					Status tweet = listaTweetsValidos.retirarTweetValido();
					Main.print(getClass(), "mostrando tweet para usuário: " + tweet.getId());
					if (!Main.DEBUG)
						twitterController.mostrarInformacoesUsuario(tweet);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		Main.print(getClass(), "quantidade de tweets processados: " + qtdTweetsProcessados);
	}
}
