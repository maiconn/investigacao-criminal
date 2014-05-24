package br.org.furb.sic.controller.omp;

import java.util.List;

import br.org.furb.sic.controller.TwitterController;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet {
	
	private TwitterController tc;
	private List listTweetsFiltrado;
	
	public OmpValidacaoTweet(List listTweetsFiltrado) {
		this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
		OMP.setNumThreads(15);
	}
	
	public void validaTweets(List listTweetsBruto) {
		
		//omp parallel
		{
			if ((OMP.getThreadNum() < listTweetsBruto.size())
			&&  ((tc.isValidTweet((Status)listTweetsBruto.get(OMP.getThreadNum()))))) {
				//omp critical
				{
					listTweetsFiltrado.add((Status)listTweetsBruto.get(OMP.getThreadNum()));
				}
			}
			//if (OMP.getThreadNum() < listTweetsBruto.size())
			//	System.out.print(OMP.getThreadNum() + ", ");
		}
		//System.out.println("");
	}
}
