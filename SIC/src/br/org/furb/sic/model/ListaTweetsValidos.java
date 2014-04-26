package br.org.furb.sic.model;

import twitter4j.Status;

/** 
 * @category Consumidor
 */
public class ListaTweetsValidos {
	
	private ListaTweets listaTweets;
	
	public ListaTweetsValidos(ListaTweets lista){
		listaTweets = lista;		
	}
	
	// Método para ser acordado para consumir 
	public void validaTweet(String palavraChave){
		// Lock? 
		Status status = listaTweets.removeTweet();
		// UnLock?
		
		// Faz algo fodastico \/
		// Dava de fazer uma viagem aqui, criava varias threads mexendo no mesmo dado, verificando a mensagem se é valida
		System.out.println("Validando tweet: " + status.getText());		
		
		// Ok é valido
		if (status.getText().contains(palavraChave)){
			// Faz o procedimento para buscar a timeline do usuário;
			// Monta perfil do usuário
			// status.getUser().getProfileImageURL();
			// status.getUser().getLocation();
			// status.getUser().getDescription();
		}		
		
	}

}
