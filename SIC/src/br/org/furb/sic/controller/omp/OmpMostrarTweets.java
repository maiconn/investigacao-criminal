package br.org.furb.sic.controller.omp;

import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import jomp.runtime.OMP;
import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.view.Main;

public class OmpMostrarTweets {
	
	private TwitterController tc;
	private List listTweetsFiltrado;
	
	public OmpMostrarTweets(List listTweetsFiltrado) {
		this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
	}
	
	public void mostrarTweets() {
		OMP.setNumThreads(15);
		
		//omp parallel
		{
			//omp for
			for (int i = 0; i < listTweetsFiltrado.size(); i++) {
				
				String exibirNaTela = "";
				Status tweet = (Status)listTweetsFiltrado.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				exibirNaTela += "Usuario: @" + tweet.getUser().getScreenName() + "\n";
				exibirNaTela += "Descrição: " + tweet.getUser().getDescription() + "\n";
				exibirNaTela += "Localização: " + tweet.getUser().getLocation() + "\n";
				exibirNaTela += "Nome: " + tweet.getUser().getName() + "\n";
				exibirNaTela += sdf.format(tweet.getCreatedAt()) + " - " + tweet.getText() + "\n";

				String msgErroTwitter = "";
				String msgErroFacebook = "";
				List cincoUltimosTweetsUsuario = null;
				
				//omp sections
				{					
					//omp section
					{
						try {
							cincoUltimosTweetsUsuario = tc.cincoUltimosTweetsUsuario(tweet);
						} catch (TwitterException e) {
							msgErroTwitter = "Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.";
						} catch (ListaVaziaException e) {
							msgErroTwitter = "Nenhum tweet recente.";
						}
					}
						
					//omp section
					{
						msgErroFacebook = "À fazer - Perfil no Facebook";
					}
				}
				
				if (msgErroTwitter.trim().equals("")) {
					if (cincoUltimosTweetsUsuario != null) {
						exibirNaTela += "[Últimos Tweets]\n";
						for (int j = 0; j < cincoUltimosTweetsUsuario.size(); j++) {
							exibirNaTela += " -> " + ((Status) cincoUltimosTweetsUsuario.get(j)).getText() + "\n";
						}
					}
				} else {
					exibirNaTela += msgErroTwitter + "\n";
				}
				
				if (msgErroFacebook.trim().equals("")) {
					
				} else {
					exibirNaTela += msgErroFacebook + "\n";
				}
				
				System.out.println(exibirNaTela);
			}
		}
	}

}
