package br.org.furb.sic.controller.omp;

import java.util.List;

import jomp.runtime.OMP;
import br.org.furb.sic.controller.TwitterController;

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
				System.out.println(listTweetsFiltrado.get(i) + "\n");
			}
		}
	}

}
