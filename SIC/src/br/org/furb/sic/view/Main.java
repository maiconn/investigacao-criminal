package br.org.furb.sic.view;

import java.util.Scanner;

import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.util.StringUtil;

public class Main {
	public static final boolean DEBUG = false;
	
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("SIC - Sistema de investigação criminal");
		System.out.print("Digite a palavra desejada para ser pesquisada: ");
		String pesquisa = scan.nextLine();
		
		System.out.println();
		System.out.println("RESULTADOS");
		
		TwitterController tc = TwitterController.getInstance();
		String[] str = StringUtil.normalize(pesquisa).split(" ");
		tc.setArrayPalavras(str);
		tc.buscaPalavraChave(pesquisa);
	}

	public static void print(Class<?> clazz, String msg) {
		if (DEBUG) {
			System.out.println("[" + clazz.getSimpleName() + "] " + msg);
		}
	}
}
