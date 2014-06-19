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
import br.org.furb.sic.controller.FacebookController;
import br.org.furb.sic.model.Tweet;
import br.org.furb.sic.util.FileUtil;
import br.org.furb.sic.util.SerialUtil;

public class Escravo {
	public static void main(String[] args) {

		jpvmEnvironment jpvm = null;
		try {
			jpvm = new jpvmEnvironment();
			jpvmMessage message = jpvm.pvm_recv();

			Tag tag = Tag.getTag(message.messageTag);
			log("TAG: [" + tag.name() + "] RECEBIDA");
			Tweet tweet = (Tweet) SerialUtil
					.fromString(message.buffer.upkstr());

			jpvmBuffer buf = new jpvmBuffer();
			switch (tag) {
			case ENVIAR_BUSCA_FACEBOOK:
				FacebookController facebookController = FacebookController
						.getInstance();

				String perfisFacebook = facebookController
						.buscaPerfilFacebook(tweet.getTweet().getUser()
								.getName());

				buf.pack(tweet.getTweet().getId());
				buf.pack(perfisFacebook);

				jpvm.pvm_send(buf, jpvm.pvm_parent(),
						Tag.RECEBER_BUSCA_FACEBOOK.ordinal());
				break;
			case ENVIAR_BUSCA_TWEETS:
				String cincoUltimosTweets = tweet.getTwitterController()
						.cincoUltimosTweetsUsuario(tweet.getTweet().getId());

				buf.pack(tweet.getTweet().getId());
				buf.pack(cincoUltimosTweets);

				jpvm.pvm_send(buf, jpvm.pvm_parent(),
						Tag.RECEBER_BUSCA_TWEET.ordinal());
				break;
			case ENVIAR_VALIDAR:
				int valido = tweet.getTwitterController().isValidTweet(tweet) == true ? 1
						: 0;

				buf.pack(tweet.getTweet().getId());
				buf.pack(valido);

				jpvm.pvm_send(buf, jpvm.pvm_parent(),
						Tag.RECEBER_VALIDAR.ordinal());
				break;
			default:
				jpvmBuffer buff = new jpvmBuffer();
				buff.pack("TAG INCORRETA: " + tag.name());
				jpvm.pvm_send(buff, jpvm.pvm_parent(), Tag.ERRO.ordinal());
				break;
			}
		} catch (jpvmException e) {
			log(e.getMessage());
			String stackTrace = logStackTraceTrowable(e);
			try {
				jpvmBuffer buf = new jpvmBuffer();
				buf.pack(stackTrace);
				jpvm.pvm_send(buf, jpvm.pvm_parent(), Tag.ERRO_JPVM.ordinal());
			} catch (jpvmException e1) {
				logStackTraceTrowable(e);
			}
		} catch (Exception ex) {
			log(ex.getMessage());
			String stackTrace = logStackTraceException(ex);
			try {
				jpvmBuffer buf = new jpvmBuffer();
				buf.pack(stackTrace);
				jpvm.pvm_send(buf, jpvm.pvm_parent(), Tag.ERRO.ordinal());
			} catch (jpvmException e1) {
				logStackTraceTrowable(e1);
			}
		}
	}

	private static String logStackTraceTrowable(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String stackTrace = sw.toString();
		log(stackTrace);
		return stackTrace;
	}

	private static String logStackTraceException(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String stackTrace = sw.toString();
		log(stackTrace);
		return stackTrace;
	}

	private static void log(String texto) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"dd/MM/yyy HH:mm:ss.SSS");
			String data = format.format(new Date());
			FileUtil.writeFile("[" + data + "] " + texto + "\n",
					"C:\\temp\\log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
