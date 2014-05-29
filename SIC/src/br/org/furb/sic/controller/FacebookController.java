package br.org.furb.sic.controller;

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
		.setOAuthAccessToken("CAAJiEGjwJ0IBANhZAoZAEJHMZBpiXRVEsFDrb2EiNb9eyA7Y1YegbaupjqiyUviw7fvibZByUgD2qEocqCdvxjZA8h6SEEruRPmsZBWydnFGpt2SQgCfQ7NOP0T36R9ZBmYxiXsS6HzcYFS0mbpb7HbSZBHcXCBRr0Cj4FvHoZBURjf0mT4ehksO3gwYCMIfmZCGQZD")							  	
		.setOAuthPermissions("email,publish_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();	
	}

	public String buscaPerfilFacebook(String userName) {
		String perfis = "";

		try {
			Reading read = new Reading();
			read.limit(5);
			ResponseList<User> result = facebook.searchUsers(userName, read);
			
			if (result == null || result.isEmpty())
				perfis = "====== Nenhum perfil do Facebook encontrado ======\n";
			else
				perfis = "====== Perfil Facebook ======\n";


			//for(User p: result){
			for (int i = 0; i < result.size() && i < 5; i++) {
				//perfis += "-> ID: "+ p.getId() + " - Nome: "+ p.getName() + "\n";						
				perfis += "-> ID: "+ result.get(i).getId() + " - Nome: "+ result.get(i).getName() + "\n";
			}
		} catch (FacebookException e) {
			perfis = "====== Token de acesso do Facebook expirado ======\n";
		}
		return perfis;			
				
	}

	
}
