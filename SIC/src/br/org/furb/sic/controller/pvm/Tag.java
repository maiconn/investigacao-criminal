package br.org.furb.sic.controller.pvm;

public enum Tag {
	ENVIAR_VALIDAR,RECEBER_VALIDAR, 
	ENVIAR_BUSCA_TWEETS, RECEBER_BUSCA_TWEET, 
	ENVIAR_BUSCA_FACEBOOK, RECEBER_BUSCA_FACEBOOK,
	ERRO_JPVM, ERRO;

	public static Tag getTag(int numero) {
		for (Tag tag : values()) {
			if (numero == tag.ordinal()) {
				return tag;
			}
		}
		return null;
	}
}
