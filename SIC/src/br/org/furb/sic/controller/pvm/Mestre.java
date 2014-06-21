package br.org.furb.sic.controller.pvm;

import java.io.IOException;
import java.util.Map;

import jpvm.jpvmBuffer;
import jpvm.jpvmEnvironment;
import jpvm.jpvmException;
import jpvm.jpvmMessage;
import jpvm.jpvmTaskId;
import br.org.furb.sic.model.Tweet;
import br.org.furb.sic.model.exception.JpvmException;
import br.org.furb.sic.util.SerialUtil;
import br.org.furb.sic.view.Main;

public class Mestre {
	private Mestre() {
	}

	public static void enviar(Tweet tweet, Tag tag, jpvmEnvironment jpvm,
			jpvmTaskId tid) throws jpvmException {
		jpvmBuffer buf = new jpvmBuffer();
		try {
			String tweetSerializado = SerialUtil.toString(tweet);
			buf.pack(tweetSerializado);
			jpvm.pvm_send(buf, tid, tag.ordinal());
		} catch (IOException e) {
			Main.tratarExcessao(e);
		}
	}

	public static void receber(Map<Long, Tweet> tweetsRecebidos,
			jpvmEnvironment jpvm) throws jpvmException, JpvmException {
		jpvmMessage message = jpvm.pvm_recv();

		Tag tag = Tag.getTag(message.messageTag);
		System.out.println(tag.name());
		Long tweetId = message.buffer.upklong();
		Tweet tweet = tweetsRecebidos.get(tweetId);

		switch (tag) {
		case RECEBER_BUSCA_FACEBOOK:
			tweet.setListaPossiveisPerfisFacebook(message.buffer.upkstr());
			break;
		case RECEBER_BUSCA_TWEET:
			tweet.setListaCincoUltimosTweets(message.buffer.upkstr());
			break;
		case RECEBER_VALIDAR:
			tweet.setValido(message.buffer.upkint() == 1 ? true : false);
			break;
		case ERRO:
		case ERRO_JPVM:
			String messageException = message.buffer.upkstr();
			throw new JpvmException(messageException);
		default:
			throw new JpvmException("Tag inválida ao receber: " + tag.name());
		}
		tweetsRecebidos.put(tweetId, tweet);
		
		jpvm.pvm_exit();
	}
}
