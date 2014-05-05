package br.org.furb.sic.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.generic.GenericJDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BuscaPalavrasChaveView extends GenericJDialog {
	private JTextField txtPesquisa;
	private static JTextArea txtAreaResultado;

	public BuscaPalavrasChaveView() {
		super(800, 600, "Buscar Palavras Chave");
		getContentPane().setMaximumSize(new Dimension(0, 0));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 784, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Pesquisa",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 10, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 10, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblPesquisa = new JLabel("Palavras Chave: ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblPesquisa, gbc_lblNewLabel);

		txtPesquisa = new JTextField();
		GridBagConstraints gbc_txtPesquisa = new GridBagConstraints();
		gbc_txtPesquisa.insets = new Insets(0, 0, 0, 5);
		gbc_txtPesquisa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPesquisa.gridx = 1;
		gbc_txtPesquisa.gridy = 0;
		panel.add(txtPesquisa, gbc_txtPesquisa);
		txtPesquisa.setColumns(10);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					public void run() {
						txtAreaResultado.setText("");
						TwitterController twitterController = TwitterController
								.getInstance();
						try {
							twitterController.buscaPalavraChave(txtPesquisa
									.getText());
						} catch (Exception e) {
							Main.tratarExcessao(e);
						}
					};
				}.start();
			}
		});
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.gridx = 2;
		gbc_btnPesquisar.gridy = 0;
		panel.add(btnPesquisar, gbc_btnPesquisar);

		JPanel panel_1 = new JPanel();
		panel_1.setAutoscrolls(true);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 784, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 511, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblResultadosDaPesquisa = new JLabel("Resultados da Pesquisa");
		GridBagConstraints gbc_lblResultadosDaPesquisa = new GridBagConstraints();
		gbc_lblResultadosDaPesquisa.anchor = GridBagConstraints.SOUTH;
		gbc_lblResultadosDaPesquisa.insets = new Insets(0, 0, 5, 0);
		gbc_lblResultadosDaPesquisa.gridx = 0;
		gbc_lblResultadosDaPesquisa.gridy = 0;
		panel_1.add(lblResultadosDaPesquisa, gbc_lblResultadosDaPesquisa);

		txtAreaResultado = new JTextArea();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollPane.setViewportView(txtAreaResultado);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		panel_1.add(scrollPane, gbc_scrollPane);

		setVisible(true);
	}

	public static void resultadoPrintln(String txt) {
		txtAreaResultado.append(txt + "\n");
	}

	public static void resultadoPrint(String txt) {
		txtAreaResultado.append(txt);
	}

}
