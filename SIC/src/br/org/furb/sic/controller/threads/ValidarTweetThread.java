package br.org.furb.sic.controller.threads;

import java.util.concurrent.Semaphore;

import br.org.furb.sic.model.ListaTweetsValidos;
import br.org.furb.sic.view.Main;
import twitter4j.Status;

public class ValidarTweetThread extends Thread {
	private Status tweet;
	private Semaphore semaforo;
	private ListaTweetsValidos listaTweetsValidos;
	
	public ValidarTweetThread(Status tweet, Semaphore semaforo, ListaTweetsValidos listaTweetsValidos) {
		this.tweet = tweet;
		this.semaforo = semaforo;
		this.listaTweetsValidos = listaTweetsValidos;
	}
	
	@Override
	public void run() {
		Main.print(getClass(), "validando um tweet: "+tweet.getId());
		//TODO Validação do tweet e encaminhamento para a Lista de válidos.
		
		try {
			listaTweetsValidos.adicionarTweetValido(tweet);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		semaforo.release();
	}
}
