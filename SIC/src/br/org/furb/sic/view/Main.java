package br.org.furb.sic.view;

import br.org.furb.sic.controller.TwitterController;

public class Main {
	private static final boolean DEBUG = false;

	public static void main(String[] args) {
		TwitterController tc = TwitterController.getInstance();
		tc.buscaPalavraChave("Furb");
	}

	public static void print(Class<?> clazz, String msg) {
		if (DEBUG) {
			System.out.println("[" + clazz.getSimpleName() + "] " + msg);
		}
	}

}
