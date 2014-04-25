package br.org.furb.sic.model;

import java.util.List;

import twitter4j.Status;

public class ListaTweets {
	
	private List<Status> lista;
	
	public void insereTweet(Status tweet){
		lista.add(tweet);		
	}

}
