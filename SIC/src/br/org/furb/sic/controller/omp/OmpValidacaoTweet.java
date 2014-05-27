package br.org.furb.sic.controller.omp;

import java.util.HashMap;
import java.util.List;

import br.org.furb.sic.controller.TwitterController;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet {
	
	private TwitterController tc;
	private List listTweetsFiltrado;
	private HashMap listaCincoUltimosTweets;
	private HashMap listaPerfisFacebook;
	
	public OmpValidacaoTweet(List listTweetsFiltrado, HashMap listaCincoUltimosTweets, HashMap listaPerfisFacebook) {
		this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
		this.listaCincoUltimosTweets = listaCincoUltimosTweets;
		this.listaPerfisFacebook = listaPerfisFacebook;
		
		OMP.setNumThreads(15);
	}
	
	public void validaTweets(List listTweetsBruto) {
		
		//omp parallel
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
			}
		}
		for (int i = 0; i < listTweetsBruto.size(); i++) {
			if (listTweetsBruto.get(i) != null) {
				Status status = (Status)listTweetsBruto.get(i);
				//omp parallel
				{
					//omp sections
					{
						//omp section
						{
							listaCincoUltimosTweets.put(status.getUser().getId(), tc.cincoUltimosTweetsUsuario(status.getUser().getId()));
						}
						//omp section
						{
							//Facebook
						}
					}
				}
			}
		}
	}
}