package Bampi;

import jomp.runtime.Lock;
import jomp.runtime.OMP;


public class OmpValidacao {
	
	private Object[] vetorTweetsBruto;
	
	public OmpValidacao(Object[] vetorTweetsBruto) {
		this.vetorTweetsBruto = vetorTweetsBruto;
	}
	
	public void validaTweets(Lock jompLock) {
		OMP.setNumThreads(vetorTweetsBruto.length);
		
		//omp parallel
		{
			System.out.println(vetorTweetsBruto[OMP.getThreadNum()]);
		}
		
		jompLock.unset();
	}
}
