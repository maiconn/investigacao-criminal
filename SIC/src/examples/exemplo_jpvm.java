package examples;

import java.io.IOException;

import jpvm.jpvmBuffer;
import jpvm.jpvmEnvironment;
import jpvm.jpvmException;
import jpvm.jpvmMessage;
import jpvm.jpvmTaskId;
import br.org.furb.sic.util.SerialUtil;

public class exemplo_jpvm {

	static int num_workers = 4;

	public static void main(String[] args) {
		try {
			jpvmEnvironment jpvm = new jpvmEnvironment();

			jpvmTaskId myid = jpvm.pvm_mytid();
			jpvmTaskId masterTaskId = jpvm.pvm_parent();
			System.out.println("task id = " + myid.toString());

			// definir se ÃƒÂ© o master, ou se ÃƒÂ© algum escravo.
			if (masterTaskId == jpvm.PvmNoParent) {
				// ÃƒÂ© o master
				System.out.println("inicializado como master");
				jpvmTaskId tids[] = new jpvmTaskId[num_workers];
				jpvm.pvm_spawn("examples.exemplo_jpvm", num_workers, tids);

				System.out.println("workers taks: ");
				int i;
				for (i = 0; i < num_workers; i++)
					System.out.println("\t" + tids[i].toString());

				for (i = 0; i < num_workers; i++) {
					System.out.println("mandando mensagem para o worker " + i);
					TesteSerial teste = new TesteSerial("MAICON GOSTOSAO");
					jpvmBuffer buf = new jpvmBuffer();
					try {
						buf.pack(SerialUtil.toString(teste));
					} catch (IOException e) {
						e.printStackTrace();
					}

					// ObjectOutput out = null;
					// byte[] yourBytes = null;
					// ByteArrayOutputStream bos = new ByteArrayOutputStream();
					// try {
					// out = new ObjectOutputStream(bos);
					// out.writeObject(teste);
					// yourBytes = bos.toByteArray();
					// } catch (IOException ex) {
					//
					// }
					// buf.pack(yourBytes, yourBytes.length, 1);

					jpvm.pvm_send(buf, tids[i], i);

					System.out.println("recebeu resposta:");
					jpvmMessage message = jpvm.pvm_recv();
					String str = message.buffer.upkstr();
					System.out.println("recebeu : " + str);
					System.out.println("com a tag " + message.messageTag
							+ " from " + message.sourceTid.toString());
				}

			} else {
				// ÃƒÂ© o escravo.
				System.out.println("inicializado como escravo.");

				jpvmMessage message = jpvm.pvm_recv();
				String str = message.buffer.upkstr();
				TesteSerial teste = null;
				try {
					teste = (TesteSerial) SerialUtil.fromString(str);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// byte[] retorno = null;
				// message.buffer.unpack(retorno, 1, 1);
				//
				// ByteArrayInputStream bis = new ByteArrayInputStream(retorno);
				// ObjectInput in = null;
				// TesteSerial testeSerial = null;
				// try {
				// in = new ObjectInputStream(bis);
				// testeSerial = (TesteSerial) in.readObject();
				// } catch (Exception ex) {
				//
				// }

				System.out.println("recebeu : " + teste.getNome());
				System.out.println("com a tag " + message.messageTag + " from "
						+ message.sourceTid.toString());

				System.out.println("preparando resposta.");
				jpvmBuffer buf = new jpvmBuffer();
				buf.pack(teste.getNome() + " cliente : "
						+ jpvm.pvm_mytid().toString());
				jpvm.pvm_send(buf, masterTaskId, 0);
				System.out.println("mando resposta.");
			}
			jpvm.pvm_exit();
		} catch (jpvmException e) {
			e.printStackTrace(System.out);
		}

	}


}