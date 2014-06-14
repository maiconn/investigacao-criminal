package br.org.furb.sic.controller.pvm;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import jpvm.jpvmBuffer;
import jpvm.jpvmEnvironment;
import jpvm.jpvmException;
import jpvm.jpvmMessage;
import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.model.Tweet;
import br.org.furb.sic.util.FileUtil;
import br.org.furb.sic.util.SerialUtil;

public class Escravo {
	public static void main(String[] args) {

		jpvmEnvironment jpvm = null;
		try {
			// log("inicializado como escravo.");
			jpvm = new jpvmEnvironment();
			jpvmMessage message = jpvm.pvm_recv();

			Tag tag = Tag.getTag(message.messageTag);

			switch (tag) {
			case BUSCAR_FACEBOOK:
				break;
			case BUSCAR_TWEETS:
				break;
			case VALIDAR:
				String str = message.buffer.upkstr();
				log(str);
				Tweet tweet = (Tweet) SerialUtil.fromString(str);

				int valido = TwitterController.getInstance()
						.isValidTweet(tweet) == true ? 1 : 0;
				jpvmBuffer buf = new jpvmBuffer();

				buf.pack(valido);

				log("mando resposta.");

				jpvm.pvm_send(buf, jpvm.pvm_parent(), 0);

				break;
			default:
				break;
			}
		} catch (jpvmException e) {
			log(e.getMessage());
			e.printStackTrace();
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			log(sw.toString());
			ex.printStackTrace();
		}
	}

	//
	private static void log(String texto) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy HH:mm");
			String data = format.format(new Date());
			FileUtil.writeFile("\n[" + data + "] " + texto + "\n",
					"C:\\temp\\log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
