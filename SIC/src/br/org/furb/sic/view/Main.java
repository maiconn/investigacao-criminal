package br.org.furb.sic.view;

import br.org.furb.sic.controller.TwitterController;

public class Main {
	private static final boolean DEBUG = true;

	public static void main(String[] args) {
		TwitterController tc = new TwitterController();
		tc.buscaPalavraChave("Furb");
	}

	public static void print(Class<?> clazz, String msg) {
		if (DEBUG) {
			System.out.println("[" + clazz.getName() + "] " + msg);
		}
	}

}
