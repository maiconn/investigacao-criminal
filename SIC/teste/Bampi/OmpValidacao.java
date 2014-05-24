package Bampi;

import java.util.List;

import br.org.furb.sic.model.ListaTweets;
import jomp.runtime.OMP;


public class OmpValidacao {
	
	//private Object[] vetorTweetsBruto;
	private List listTweetsBruto;
	
	public OmpValidacao(List listTweetsBruto) {
		//this.vetorTweetsBruto = vetorTweetsBruto;
		this.listTweetsBruto = listTweetsBruto;
	}
	
	public void validaTweets(/*Lock jompLock*/) {
		//OMP.setNumThreads(vetorTweetsBruto.length);
		OMP.setNumThreads(listTweetsBruto.size());
		
		//omp parallel
		{
			//System.out.print(vetorTweetsBruto[OMP.getThreadNum()] + ", ");
			//if (OMP.getThreadNum() < listTweetsBruto.size())
			if (listTweetsBruto.get(OMP.getThreadNum()) != null) {
				System.out.print(listTweetsBruto.get(OMP.getThreadNum()) + ", ");
				listTweetsBruto.set(OMP.getThreadNum(), null);
			}
		}
		
		//jompLock.unset();
		System.out.println("");
	}
}
