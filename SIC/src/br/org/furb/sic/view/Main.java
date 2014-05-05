package br.org.furb.sic.view;

import java.util.Scanner;

import javax.swing.JOptionPane;

import twitter4j.TwitterException;
import br.org.furb.sic.Config;

public class Main {

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		// System.out.println("SIC - Sistema de investigação criminal");
		// System.out.print("Digite a palavra desejada para ser pesquisada: ");
		// String pesquisa = scan.nextLine();
		//
		// System.out.println();
		// System.out.println("RESULTADOS");
		//
		// TwitterController tc = TwitterController.getInstance();
		// tc.buscaPalavraChave(pesquisa);

		new InicialView();
	}

	/**
	 * Método que imprime as mensagens conforme a flag de DEBUG.
	 * 
	 * @param msg
	 */
	public static void print(String msg) {
		if (Config.DEBUG) {
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
			te.printStackTrace();
			if (te.getMessage().contains("code - 88")) {
				JOptionPane
						.showMessageDialog(
								null,
								"["
										+ te.getClass().getName()
										+ "] Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.",
								"TwitterException", JOptionPane.WARNING_MESSAGE);
			} else {
				te.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed to search tweets: "
						+ te.getMessage(), "TwitterException",
						JOptionPane.ERROR_MESSAGE);
			}
//			System.exit(-1);
		} else {
			JOptionPane.showMessageDialog(null, "Erro: "
					+ ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}
