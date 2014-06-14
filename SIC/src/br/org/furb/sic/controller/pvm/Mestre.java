package br.org.furb.sic.controller.pvm;

import java.io.IOException;

import jpvm.jpvmBuffer;
import jpvm.jpvmEnvironment;
import jpvm.jpvmException;
import jpvm.jpvmMessage;
import jpvm.jpvmTaskId;
import twitter4j.Status;
import br.org.furb.sic.util.SerialUtil;
import br.org.furb.sic.view.Main;

public class Mestre {
	private Mestre() {
	}

	public static void enviarValidacaoTweet(Status tweet, jpvmEnvironment jpvm,
			jpvmTaskId tid) throws jpvmException {
		jpvmBuffer buf = new jpvmBuffer();
		try {
			buf.pack(SerialUtil.toString(tweet));
			jpvm.pvm_send(buf, tid, Tag.VALIDAR.ordinal());
			Main.print("Mandando validação de um tweet.");
			
			jpvmMessage message = jpvm.pvm_recv();
			Main.print("Recebendo validação.");
			System.out.println(message.sourceTid);
			int valido = message.buffer.upkint();
//			return valido == 1 ? true : false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean receberValidacao(jpvmEnvironment jpvm)
			throws jpvmException {
		Main.print("Recebendo validação.");
		jpvmMessage message = jpvm.pvm_recv();
		System.out.println(message.sourceTid);
		int valido = message.buffer.upkint();
		return valido == 1 ? true : false;
	}
}
