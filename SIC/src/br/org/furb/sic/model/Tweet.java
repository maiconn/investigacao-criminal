package br.org.furb.sic.model;

import java.io.Serializable;
import java.util.List;

import twitter4j.Status;

@SuppressWarnings("serial")
public class Tweet implements Serializable {
	private Status tweet;
	private List<String> palavrasChave;

	public Tweet(Status tweet, List<String> palavrasChave) {
		this.tweet = tweet;
		this.palavrasChave = palavrasChave;
	}

	public List<String> getPalavrasChave() {
		return palavrasChave;
	}

	public Status getTweet() {
		return tweet;
	}
}
