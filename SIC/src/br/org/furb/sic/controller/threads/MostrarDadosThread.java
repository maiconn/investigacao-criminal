package br.org.furb.sic.controller.threads;

import twitter4j.Status;
import br.org.furb.sic.Config;
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
		Main.print("startou thread.");
		try {
			while (!listaTweetsValidos.isFimDosTweets()
					|| !listaTweetsValidos.getLista().isEmpty()) {
				if (!listaTweetsValidos.getLista().isEmpty()) {
					qtdTweetsProcessados++;
					Status tweet = listaTweetsValidos.retirarTweetValido();
					Main.print("mostrando tweet para usuário: " + tweet.getId());
					if (!Config.DEBUG)
						twitterController.mostrarInformacoesUsuario(tweet);

				}
			}
		} catch (Exception ex) {
			Main.tratarExcessao(ex);
		}

		Main.print("quantidade de tweets mostrados: " + qtdTweetsProcessados);
	}
}
