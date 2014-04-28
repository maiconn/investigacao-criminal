package br.org.furb.sic.view;

import java.util.Scanner;

import br.org.furb.sic.controller.TwitterController;

public class Main {
	public static final boolean DEBUG = true;
	
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("SIC - Sistema de investigação criminal");
		System.out.print("Digite a palavra desejada para ser pesquisada: ");
		String pesquisa = scan.nextLine();
		
		
		TwitterController tc = TwitterController.getInstance();
		tc.buscaPalavraChave(pesquisa);
	}

	public static void print(Class<?> clazz, String msg) {
		if (DEBUG) {
			System.out.println("[" + clazz.getSimpleName() + "] " + msg);
		}
	}
}
