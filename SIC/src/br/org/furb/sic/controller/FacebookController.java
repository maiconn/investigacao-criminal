package br.org.furb.sic.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import br.org.furb.sic.util.StringUtil;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookController {

	private Facebook facebook;
	private static FacebookController instance;

	public static FacebookController getInstance() {
		if (instance == null) {
			instance = new FacebookController();
		}
		return instance;
	}

	private FacebookController() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthAppId("670772572989250")
				.setOAuthAppSecret("5656c9ece88d88110d79796bb82b174a")
				// Trocar chave de acesso abaixo
				.setOAuthAccessToken(
						"CAAJiEGjwJ0IBAIzcRZBgeH2X4D4Roy7ViDe9pIjbUpYMB3gv091YgjXpjP9utizJgMa6AHT4Pi70p7ZBePsi2aZCSeZCV6UXb1QsZBWi2VN0cFQUH8Gi95qh8q4ptCyDsssZCZAJAghvvd0SheEkxgAboqZA89ABCEJm5HEv6LsyCS22q21fQYBYIBSNtP5RaeYZD")
				.setOAuthPermissions("email,publish_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
	}

	public String buscaPerfilFacebook(String userName) {
		String perfis = "";

		try {
			Reading read = new Reading();
			read.limit(5);
			ResponseList<User> result = facebook.searchUsers(
					StringUtil.normalize(userName), read);

			if (result == null || result.isEmpty())
				perfis = "====== Nenhum perfil do Facebook encontrado ======\n";
			else
				perfis = "====== Perfil Facebook ======\n";

			// for(User p: result){
			for (int i = 0; i < result.size() && i < 5; i++) {
				// perfis += "-> ID: "+ p.getId() + " - Nome: "+ p.getName() +
				// "\n";
				perfis += "-> ID: " + result.get(i).getId() + " - Nome: "
						+ result.get(i).getName() + "\n";
			}
		} catch (FacebookException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			perfis = stackTrace;
//			perfis = "====== Token de acesso do Facebook expirado ======\n";
		}
		return perfis;

	}

}
