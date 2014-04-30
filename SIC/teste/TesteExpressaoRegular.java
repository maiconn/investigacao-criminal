public class TesteExpressaoRegular {
	public static void main(String[] args) {
		String str = "@maicon_gerardi comO  54 podemos54 viver #sem a copa,";  
		str = str.replaceAll("[^a-zA-Z0-9#@ _]", "");  
		System.out.println("["+str+"]");
	}
}
