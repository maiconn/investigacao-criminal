package br.org.furb.sic.controller.pvm;

public enum Tag {
	VALIDAR, BUSCAR_TWEETS, BUSCAR_FACEBOOK;

	public static Tag getTag(int numero) {
		for (Tag tag : values()) {
			if (numero == tag.ordinal()) {
				return tag;
			}
		}
		return null;
	}
}
