package br.org.furb.sic.model;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import br.org.furb.sic.controller.threads.ListaTweetsThread;
import br.org.furb.sic.view.Main;

/**
 * @category Produtor
 */
public class ListaTweets {
	private final int LIMITE_LISTA = 100;

	private List<Status> lista;
	private ListaTweetsThread listaValidacaoThread;
	private boolean fimPesquisaTwitter = false;

	public ListaTweets() {
		lista = new ArrayList<Status>();

		listaValidacaoThread = new ListaTweetsThread(this);
		listaValidacaoThread.start();
	}

	public synchronized void insereTweet(Status tweet)
			throws InterruptedException {
		while (lista.size() >= LIMITE_LISTA)
			wait();

		lista.add(tweet);
		Main.print("tamanho(" + lista.size()
				+ ") adicionou tweet na ListaTweet: " + tweet.getId());

		notifyAll();
	}

	public void finalizar() {
		Main.print("finalizou a pesquisa de tweets.");
		this.fimPesquisaTwitter = true;
	}

	public synchronized Status retirarTweet() throws InterruptedException {
		if (lista.isEmpty()) {
			wait();
		}

		Status tweet = lista.remove(0);
		Main.print("tamanho(" + lista.size()
				+ ") removeu tweet da ListaTweet: " + tweet.getId());
		notifyAll();
		return tweet;

	}

	public boolean isFimPesquisaTwitter() {
		return fimPesquisaTwitter;
	}

	public boolean vazia() {
		return this.lista.isEmpty();
	}

}
