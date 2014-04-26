package br.org.furb.sic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import twitter4j.Status;
import br.org.furb.sic.view.Main;

/**
 * @category Produtor
 */
public class ListaTweets {
	private final int LIMITE_LISTA = 100;
	private final int LIMITE_THREAD = 20;
	
	private List<Status> lista;
	private Semaphore semaforo;

	public ListaTweets() {
		lista = new ArrayList<Status>();
		semaforo = new Semaphore(LIMITE_THREAD);
		new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						semaforo.acquire();
					
						/**
						 * Iniciar uma thread se existir recurso para isso.
						 */
						TratarTweetThread thread = new TratarTweetThread(lista.remove(0), semaforo);
						thread.start();
						
						// TODO falta finalizar Thread após acabar os tweets
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	public List<Status> getLista() {
		return lista;
	}

	public synchronized void insereTweet(Status tweet)
			throws InterruptedException {
		while (lista.size() > LIMITE_LISTA)
			wait();

		lista.add(tweet);
		Main.print(getClass(), "tamanho(" + lista.size()
				+ ") adicionou tweet na ListaTweet: " + tweet.toString());


		notifyAll();
	}

	// Precisa dar um Lock para remover?
	public Status removeTweet() {

		if (!lista.isEmpty())
			return lista.remove(0);

		return null;

	}

}
