package br.org.furb.sic.controller;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
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
		.setOAuthAccessToken("CAAJiEGjwJ0IBAKGdzg6fkKzY2CFcuGwcOlaUtr1awzZAcGl43rs4jlIgIzTeZAzHrwjevXYonjHQ9E7nzvFQDq3tGonfZBP6xqZAJuaaDokL9UlOm9lJkZBzq1DgUEHxPPBRpI0as71lyf4Qlocvmh4vMlyfS7ZAlRDZBVoeSlg9BnSsmEZB11fIEYIiqVMRLjMZD")							  	
		.setOAuthPermissions("email,publish_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();	
	}

	public String buscaPerfilFacebook(String userName) {
		String perfis = "";

		try {
			ResponseList<User> result = facebook.searchUsers(userName);
			
			if (result!=null)
				perfis = "====== Perfil Facebook ======";
			else
				perfis = "====== Nenhum perfil do Facebook encontrado ======";
			
			for(User p: result){
				perfis += "-> ID: "+ p.getId() + " - Nome: "+ p.getName() + "\n";						
			}
		} catch (FacebookException e) {
			perfis = "Perfil do Facebook não encontrado.";
		}
		return perfis;			
				
	}

	
}
