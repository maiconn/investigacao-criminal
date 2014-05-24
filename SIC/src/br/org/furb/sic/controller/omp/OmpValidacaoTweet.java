package br.org.furb.sic.controller.omp;

import java.util.List;

import br.org.furb.sic.controller.TwitterController;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet {
	
	//private TwitterController tc;
	private List listTweetsFiltrado;
	
	public OmpValidacaoTweet(List listTweetsFiltrado) {
		//this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
	}
	
	public void validaTweets(List listTweetsBruto) {
		OMP.setNumThreads(listTweetsBruto.size());
		
		//omp parallel
		{
			System.out.print(OMP.getThreadNum() + ", ");
		}
		System.out.println("");
	}
}
