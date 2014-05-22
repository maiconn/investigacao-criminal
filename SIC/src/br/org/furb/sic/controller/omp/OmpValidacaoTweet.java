package br.org.furb.sic.controller.omp;

import java.util.List;

import jomp.runtime.Lock;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet {
	
	public void validaTweets(List listTweetsBruto, Lock jompLock) {
		OMP.setNumThreads(listTweetsBruto.size());
		
		//omp parallel
		{
			System.out.println(listTweetsBruto.get(OMP.getThreadNum()));
			System.out.println("N. da thread = " + OMP.getThreadNum() + "\n tamanho da lista = " + listTweetsBruto.size() + "\n");
		}
		
		System.out.println("\n\nLista Completa\n\n");
		
		jompLock.unset();
	}
}
