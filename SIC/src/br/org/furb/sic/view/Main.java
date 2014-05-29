package br.org.furb.sic.view;

import java.util.Scanner;

import twitter4j.TwitterException;
import br.org.furb.sic.controller.TwitterController;

public class Main {
	public static final boolean DEBUG = true;

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("SIC - Sistema de investigação criminal");
		System.out.print("Digite a palavra desejada para ser pesquisada: ");
		String pesquisa = scan.nextLine();

		System.out.println();
		System.out.println("RESULTADOS");

		TwitterController tc = TwitterController.getInstance();
		//tc.buscaPalavraChave(pesquisa);
		tc.buscaPalavraChaveOmp(pesquisa);
	}

	/**
	 * Método que imprime as mensagens conforme a flag de DEBUG.
	 * 
	 * @param msg
	 */
	public static void print(String msg) {
		if (DEBUG) {
			Exception ex = new Exception();
			StackTraceElement[] stack = ex.getStackTrace();
			String[] separator = stack[1].getClassName().split("\\.");
			String simpleCassName = separator[separator.length - 1];
			System.out.println("[" + simpleCassName + "] " + msg);
		}
	}

	/**
	 * Método que irá tratar as exceptions do sistema.
	 * 
	 * @param ex
	 */
	public static void tratarExcessao(Exception ex) {
		if (ex instanceof TwitterException) {
			TwitterException te = (TwitterException) ex;
			if (te.getMessage().contains("code - 88")) {
				System.err
						.println("["
								+ te.getClass().getName()
								+ "] Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.");
			} else {
				te.printStackTrace();
				System.err.println("Failed to search tweets: "
						+ te.getMessage());
			}
			System.exit(-1);
		} else {
			ex.printStackTrace();
		}
	}
}
