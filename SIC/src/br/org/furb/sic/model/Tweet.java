package br.org.furb.sic.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.Status;

@SuppressWarnings("serial")
public class Tweet implements Serializable {
	private Status tweet;
	private List<String> palavrasChave;
	private String listaCincoUltimosTweets;
	private String listaPossiveisPerfisFacebook;
	private boolean valido;

	public Tweet(Status tweet, List<String> palavrasChave) {
		this.tweet = tweet;
		this.palavrasChave = palavrasChave;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public boolean isValido() {
		return valido;
	}

	public List<String> getPalavrasChave() {
		return palavrasChave;
	}

	public Status getTweet() {
		return tweet;
	}

	public String getListaPossiveisPerfisFacebook() {
		return listaPossiveisPerfisFacebook;
	}

	public void setListaPossiveisPerfisFacebook(
			String listaPossiveisPerfisFacebook) {
		this.listaPossiveisPerfisFacebook = listaPossiveisPerfisFacebook;
	}

	public String getListaCincoUltimosTweets() {
		return listaCincoUltimosTweets;
	}

	public void setListaCincoUltimosTweets(String listaCincoUltimosTweets) {
		this.listaCincoUltimosTweets = listaCincoUltimosTweets;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		str.append("Usuario: @" + tweet.getUser().getScreenName() + "\n");
		str.append("Descrição: " + tweet.getUser().getDescription() + "\n");
		str.append("Localização: " + tweet.getUser().getLocation() + "\n");
		str.append("Nome: " + tweet.getUser().getName() + "\n");
		str.append(sdf.format(tweet.getCreatedAt()) + " - " + tweet.getText()
				+ "\n");
		str.append("==== ULTIMOS 5 TWEETS ====\n");
		str.append(listaCincoUltimosTweets);
		str.append("==== POSSÍVEIS PERFIS FACEBOOK ====\n");
		str.append(listaPossiveisPerfisFacebook + "\n");
		
		return str.toString();
	}
}
