package Bampi;

import java.util.ArrayList;
import java.util.List;

import jomp.runtime.OMP;

//import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		//Object[] vetorTweetsBruto = new Object[15];
		
		List listTweetsBruto;
		
		//Random enchedorDeVetor = new Random();
		
		//Lock jompLock = new Lock();
		
		for (int qtdeVezes = 0; qtdeVezes < 500; qtdeVezes++) {
			
			//jompLock.set();
			listTweetsBruto = new ArrayList();
			
			for (int i = 0; i < 15; i++) {
				listTweetsBruto.add(String.valueOf(i)); //enchedorDeVetor.nextInt(100));
			}
		
			OmpValidacao_jomp ompValidacao = new OmpValidacao_jomp(listTweetsBruto);
			ompValidacao.validaTweets();
			
			//System.out.println("\nVez de número " + qtdeVezes + "\n");
			
			
		}
	}

}
