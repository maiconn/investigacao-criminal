package Bampi;

import jomp.runtime.*;

//import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		Object[] vetorTweetsBruto = new Object[100];
		
		//Random enchedorDeVetor = new Random();
		
		Lock jompLock = new Lock();
		
		for (int qtdeVezes = 0; qtdeVezes < 3; qtdeVezes++) {
			
			jompLock.set();
			
			for (int i = 0; i < vetorTweetsBruto.length; i++) {
				vetorTweetsBruto[i] = String.valueOf(i); //enchedorDeVetor.nextInt(100));
			}
		
			OmpValidacao_jomp ompValidacao = new OmpValidacao_jomp(vetorTweetsBruto);
			ompValidacao.validaTweets(jompLock);
			
			System.out.println("\nVez de número " + qtdeVezes + "\n");
			
			
		}
	}

}
