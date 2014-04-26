package br.org.furb.sic.model;

import java.util.concurrent.Semaphore;

import br.org.furb.sic.view.Main;
import twitter4j.Status;

public class TratarTweetThread extends Thread {
	private Status status;
	private Semaphore semaforo;
	
	public TratarTweetThread(Status status, Semaphore semaforo) {
		this.status = status;
		this.semaforo = semaforo;
	}
	
	@Override
	public void run() {
		Main.print(getClass(), "tratando um tweet: "+status.toString());
		
		semaforo.release();
	}
}
