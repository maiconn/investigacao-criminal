package br.org.furb.sic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import twitter4j.Status;
import br.org.furb.sic.controller.threads.MostrarDadosThread;
import br.org.furb.sic.view.Main;

/**
 * @category Consumidor
 */
public class ListaTweetsValidos {
	private static final int LIMITE_LISTA = 100;

	private List<Status> lista = new ArrayList<Status>();
	private boolean fimDosTweets = false;

	private Lock lock = new ReentrantLock();
	private Condition podeAdicionar = lock.newCondition();
	private Condition podeRetirar = lock.newCondition();
	private MostrarDadosThread mostrarDadosThread;
	
	public ListaTweetsValidos() {
		mostrarDadosThread = new MostrarDadosThread(this);
		mostrarDadosThread.start();
	}

	public void adicionarTweetValido(Status tweet) throws InterruptedException {
		lock.lock();
		try {
			if (lista.size() >= LIMITE_LISTA) {
				podeAdicionar.await();
			}

			lista.add(tweet);

			Main.print(getClass(), "adicionando tweet:" + tweet.getId());

			podeRetirar.signal();
		} finally {
			lock.unlock();
		}
	}

	public Status retirarTweetValido() throws InterruptedException {
		lock.lock();
		try {
			if (lista.isEmpty()) {
				podeRetirar.await();
			}
			Status tweet = lista.remove(0);
			Main.print(getClass(), "removedo tweet: " + tweet.getId());

			podeAdicionar.signal();
			return tweet;
		} finally {
			lock.unlock();
		}
	}

	public List<Status> getLista() {
		return lista;
	}

	public void finalizar() {
		fimDosTweets = true;
	}

	public boolean isFimDosTweets() {
		return fimDosTweets;
	}

}
