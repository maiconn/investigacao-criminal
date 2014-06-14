package examples;

import java.io.Serializable;

public class TesteSerial implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome;
	
	public TesteSerial(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
