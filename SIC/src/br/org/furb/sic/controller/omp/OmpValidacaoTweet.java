package br.org.furb.sic.controller.omp;

import java.util.HashMap;
import java.util.List;

import br.org.furb.sic.controller.FacebookController;
import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.view.Main;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet {
	
	private TwitterController tc;
	private FacebookController fc;
	private List listTweetsFiltrado;
	private HashMap listaCincoUltimosTweets;
	private HashMap listaPerfisFacebook;
	
	public OmpValidacaoTweet(List listTweetsFiltrado, HashMap listaCincoUltimosTweets, HashMap listaPerfisFacebook) {
		this.tc = TwitterController.getInstance();
		this.fc = FacebookController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
		this.listaCincoUltimosTweets = listaCincoUltimosTweets;
		this.listaPerfisFacebook = listaPerfisFacebook;
		
		OMP.setNumThreads(15);
	}
	
	public int validaTweets(List listTweetsBruto, int qtdeTweetsBruto) {
		
		//omp parallel reduction(+:qtdeTweetsBruto)
		{
			if (OMP.getThreadNum() < listTweetsBruto.size()) {
				if ((tc.isValidTweet((Status)listTweetsBruto.get(OMP.getThreadNum())))) {
					//omp critical
					{
						listTweetsFiltrado.add((Status)listTweetsBruto.get(OMP.getThreadNum()));
					}
				} else {
					listTweetsBruto.set(OMP.getThreadNum(), null);
				}
				qtdeTweetsBruto++;
			}
		}
		for (int i = 0; i < listTweetsBruto.size(); i++) {
			if (listTweetsBruto.get(i) != null) {
				Status status = (Status)listTweetsBruto.get(i);
//				OMP.setNumThreads(32);
				//omp parallel
				{
					//omp sections
					{
						//omp section
						{
							Main.print("Buscando os 5 último tweets de " +  status.getUser().getName());
							listaCincoUltimosTweets.put(status.getUser().getId(), tc.cincoUltimosTweetsUsuario(status.getUser().getId()));
						}
						//omp section
						{
							Main.print("Buscando Facebook de " +  status.getUser().getName());
							listaPerfisFacebook.put(status.getUser().getId(), fc.buscaPerfilFacebook(status.getUser().getName()));
						}
					}
				}
			}
		}
		return qtdeTweetsBruto;
	}
}
