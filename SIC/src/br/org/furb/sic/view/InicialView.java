package br.org.furb.sic.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import br.org.furb.sic.Config;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Tela que inicia o sistema.
 * 
 */
public class InicialView extends JFrame {
	private static final long serialVersionUID = -2893941148869790938L;

	public InicialView() {
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setSize(700, 430);
		setTitle(Config.TITULO_SISTEMA + " :: Inicial");

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Menu", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP, null, null));
		panel.setBounds(0, 0, 430, 187);
		getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnBuscaPalavrasChave = new JButton("Busca Palavras Chave");
		btnBuscaPalavrasChave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BuscaPalavrasChaveView();
			}
		});
		btnBuscaPalavrasChave.setHorizontalAlignment(SwingConstants.LEFT);
		btnBuscaPalavrasChave.setIcon(new ImageIcon(InicialView.class
				.getResource("/br/org/furb/sic/img/palavras_chave.png")));
		btnBuscaPalavrasChave.setBounds(10, 20, 200, 43);
		panel.add(btnBuscaPalavrasChave);

		JButton btnNewButton_1 = new JButton("Fnc");
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setIcon(new ImageIcon(InicialView.class
				.getResource("/br/org/furb/sic/img/igual.png")));
		btnNewButton_1.setBounds(10, 73, 200, 43);
		panel.add(btnNewButton_1);

		JButton btnFnc = new JButton("Fnc");
		btnFnc.setHorizontalAlignment(SwingConstants.LEFT);
		btnFnc.setIcon(new ImageIcon(InicialView.class
				.getResource("/br/org/furb/sic/img/algemas.png")));
		btnFnc.setBounds(10, 127, 200, 43);
		panel.add(btnFnc);

		JButton button = new JButton("Fnc");
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setIcon(new ImageIcon(InicialView.class
				.getResource("/br/org/furb/sic/img/perfil.png")));
		button.setBounds(220, 20, 200, 43);
		panel.add(button);

		JButton button_1 = new JButton("Fnc");
		button_1.setHorizontalAlignment(SwingConstants.LEFT);
		button_1.setIcon(new ImageIcon(InicialView.class
				.getResource("/br/org/furb/sic/img/historico.png")));
		button_1.setBounds(220, 73, 200, 43);
		panel.add(button_1);

		JButton button_2 = new JButton("Fnc");
		button_2.setHorizontalAlignment(SwingConstants.LEFT);
		button_2.setIcon(new ImageIcon(InicialView.class
				.getResource("/br/org/furb/sic/img/quadrilha.png")));
		button_2.setBounds(220, 127, 200, 43);
		panel.add(button_2);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(55, 0, 784, 400);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setIcon(new ImageIcon(InicialView.class
				.getResource("/br/org/furb/sic/img/sic_logo.png")));
		getContentPane().add(lblNewLabel);
		setVisible(true);
	}
}
