package br.org.furb.sic.controller.threads;

import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.model.ListaTweetsValidos;

public class MostrarDadosThread extends Thread {
	private ListaTweetsValidos listaTweetsValidos;
	private TwitterController twitterController = TwitterController
			.getInstance();

	public MostrarDadosThread(ListaTweetsValidos listaTweetsValidos) {
		this.listaTweetsValidos = listaTweetsValidos;
	}

	@Override
	public void run() {
		while (!listaTweetsValidos.isFimDosTweets()
				|| !listaTweetsValidos.getLista().isEmpty()) {
			
			if (!listaTweetsValidos.getLista().isEmpty()) {
				try {
					twitterController
							.mostrarInformacoesUsuario(listaTweetsValidos
									.retirarTweetValido());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
