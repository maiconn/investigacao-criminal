package br.org.furb.sic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import twitter4j.Status;
import br.org.furb.sic.controller.threads.ListaTweetsThread;
import br.org.furb.sic.view.Main;

/**
 * @category Produtor
 */
public class ListaTweets {
	private final int LIMITE_LISTA = 100;
	private final int LIMITE_THREAD = 20;

	private List<Status> lista;
	private Semaphore semaforo;
	private ListaTweetsThread listaValidacaoThread;
	private boolean fimPesquisaTwitter = false;

	public ListaTweets() {
		lista = new ArrayList<Status>();
		semaforo = new Semaphore(LIMITE_THREAD);

		listaValidacaoThread = new ListaTweetsThread(this);
		listaValidacaoThread.start();
	}

	public synchronized void insereTweet(Status tweet)
			throws InterruptedException {
		while (lista.size() >= LIMITE_LISTA)
			wait();

		lista.add(tweet);
		Main.print(getClass(), "tamanho(" + lista.size()
				+ ") adicionou tweet na ListaTweet: " + tweet.getId());

		notifyAll();
	}

	public void finalizar() {
		this.fimPesquisaTwitter = true;
	}

	public synchronized Status retirarTweet() {
		if (!lista.isEmpty()) {
			Status tweet = lista.remove(0);
			notifyAll();
			return tweet;
		}

		return null;
	}

	public Semaphore getSemaforo() {
		return semaforo;
	}

	public boolean isFimPesquisaTwitter() {
		return fimPesquisaTwitter;
	}

	public boolean vazia() {
		return this.lista.isEmpty();
	}

}
