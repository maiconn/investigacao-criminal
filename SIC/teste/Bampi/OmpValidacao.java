package Bampi;

import java.util.List;

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
			System.out.print(listTweetsBruto.get(OMP.getThreadNum()) + ", ");
		}
		
		//jompLock.unset();
		System.out.println("");
	}
}
