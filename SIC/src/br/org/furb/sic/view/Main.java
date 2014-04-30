package br.org.furb.sic.view;

import java.util.Scanner;

import br.org.furb.sic.controller.TwitterController;

public class Main {
	public static final boolean DEBUG = false;

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("SIC - Sistema de investigação criminal");
		System.out.print("Digite a palavra desejada para ser pesquisada: ");
		String pesquisa = scan.nextLine();

		System.out.println();
		System.out.println("RESULTADOS");
		print("TESTE");

		TwitterController tc = TwitterController.getInstance();
		tc.buscaPalavraChave(pesquisa);
	}

	public static void print(String msg) {
		if (DEBUG) {
			Exception ex = new Exception();
			StackTraceElement[] stack = ex.getStackTrace();
			String[] separator = stack[1].getClassName().split("\\.");
			String simpleCassName = separator[separator.length - 1];
			System.out.println("[" + simpleCassName + "] " + msg);
		}
	}
}
