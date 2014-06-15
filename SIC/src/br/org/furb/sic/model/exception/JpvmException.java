package br.org.furb.sic.model.exception;

public class JpvmException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public JpvmException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
