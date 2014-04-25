package br.org.furb.sic.model;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

/** 
 * @category Produtor
 */
public class ListaTweets {	
	
	private List<Status> lista;
	private final int LIMITE_LISTA = 20;
	
	public ListaTweets(){
		lista = new ArrayList<Status>();
	}
	
	public List<Status> getLista(){
		return lista;
	}
	
	public synchronized void insereTweet(Status tweet) throws InterruptedException{
		
		while(lista.size() > LIMITE_LISTA)
			wait();	
		
		lista.add(tweet);
		
		notifyAll();
	}
	
	// Precisa dar um Lock para remover?
	public Status removeTweet(){
		
		if (!lista.isEmpty())
			return lista.remove(0);
		
		return null;		
		
	}
	
	 

}
