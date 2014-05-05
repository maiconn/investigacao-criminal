package br.org.furb.sic.generic;

import javax.swing.JDialog;

import br.org.furb.sic.Config;

@SuppressWarnings("serial")
public abstract class GenericJDialog extends JDialog {

	public GenericJDialog(int width, int height, String title) {
		setSize(width, height);
		setTitle(Config.TITULO_SISTEMA + " :: " + title);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

}
