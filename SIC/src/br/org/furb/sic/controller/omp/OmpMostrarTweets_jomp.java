package br.org.furb.sic.controller.omp;

import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import jomp.runtime.OMP;
import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.view.Main;

public class OmpMostrarTweets_jomp {

	
	private TwitterController tc;
	private List listTweetsFiltrado;
	
	public OmpMostrarTweets_jomp(List listTweetsFiltrado) {
		this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
	}
	
	public void mostrarTweets() {
		OMP.setNumThreads(15);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.listTweetsFiltrado = listTweetsFiltrado;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  listTweetsFiltrado = __omp_Object0.listTweetsFiltrado;
}
// OMP PARALLEL BLOCK ENDS

	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  List listTweetsFiltrado;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

		{
                         { // OMP FOR BLOCK BEGINS
                         // copy of firstprivate variables, initialized
                         // copy of lastprivate variables
                         // variables to hold result of reduction
                         boolean amLast=false;
                         {
                           // firstprivate variables + init
                           // [last]private variables
                           // reduction variables + init to default
                           // -------------------------------------
                           jomp.runtime.LoopData __omp_WholeData2 = new jomp.runtime.LoopData();
                           jomp.runtime.LoopData __omp_ChunkData1 = new jomp.runtime.LoopData();
                           __omp_WholeData2.start = (long)( 0);
                           __omp_WholeData2.stop = (long)( listTweetsFiltrado.size());
                           __omp_WholeData2.step = (long)(1);
                           jomp.runtime.OMP.setChunkStatic(__omp_WholeData2);
                           while(!__omp_ChunkData1.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData2, __omp_ChunkData1)) {
                           for(;;) {
                             if(__omp_WholeData2.step > 0) {
                                if(__omp_ChunkData1.stop > __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                if(__omp_ChunkData1.start >= __omp_WholeData2.stop) break;
                             } else {
                                if(__omp_ChunkData1.stop < __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                if(__omp_ChunkData1.start > __omp_WholeData2.stop) break;
                             }
                             for(int i = (int)__omp_ChunkData1.start; i < __omp_ChunkData1.stop; i += __omp_ChunkData1.step) {
                               // OMP USER CODE BEGINS
 {
				
				String exibirNaTela = "";
				Status tweet = (Status)listTweetsFiltrado.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				exibirNaTela += "Usuario: @" + tweet.getUser().getScreenName() + "\n";
				exibirNaTela += "Descri\u00e7\u00e3o: " + tweet.getUser().getDescription() + "\n";
				exibirNaTela += "Localiza\u00e7\u00e3o: " + tweet.getUser().getLocation() + "\n";
				exibirNaTela += "Nome: " + tweet.getUser().getName() + "\n";
				exibirNaTela += sdf.format(tweet.getCreatedAt()) + " - " + tweet.getText() + "\n";

				String msgErroTwitter = "";
				String msgErroFacebook = "";
				List cincoUltimosTweetsUsuario = null;
                                 { // OMP SECTIONS BLOCK BEGINS
                                 // copy of firstprivate variables, initialized
                                 // copy of lastprivate variables
                                 // variables to hold result of reduction
                                 boolean amLast1=false;
                                 {
                                   // firstprivate variables + init
                                   // [last]private variables
                                   // reduction variables + init to default
                                   // -------------------------------------
                                   __ompName_4: while(true) {
                                   switch((int)jomp.runtime.OMP.getTicket(__omp_me)) {
                                   // OMP SECTION BLOCK 0 BEGINS
                                     case 0: {
                                   // OMP USER CODE BEGINS

					{
						try {
							cincoUltimosTweetsUsuario = tc.cincoUltimosTweetsUsuario(tweet);
						} catch (TwitterException e) {
							msgErroTwitter = "Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.";
						} catch (ListaVaziaException e) {
							msgErroTwitter = "Nenhum tweet recente.";
						}
					}
                                   // OMP USER CODE ENDS
                                     };  break;
                                   // OMP SECTION BLOCK 0 ENDS
                                   // OMP SECTION BLOCK 1 BEGINS
                                     case 1: {
                                   // OMP USER CODE BEGINS

					{
						msgErroFacebook = "\u00c0 fazer - Perfil no Facebook";
					}
                                   // OMP USER CODE ENDS
                                   amLast1 = true;
                                     };  break;
                                   // OMP SECTION BLOCK 1 ENDS

                                     default: break __ompName_4;
                                   } // of switch
                                   } // of while
                                   // call reducer
                                   jomp.runtime.OMP.resetTicket(__omp_me);
                                   jomp.runtime.OMP.doBarrier(__omp_me);
                                   // copy lastprivate variables out
                                   if (amLast1) {
                                   }
                                 }
                                 // update lastprivate variables
                                 if (amLast1) {
                                 }
                                 // update reduction variables
                                 if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                                 }
                                 } // OMP SECTIONS BLOCK ENDS

				
				if (msgErroTwitter.trim().equals("")) {
					if (cincoUltimosTweetsUsuario != null) {
						exibirNaTela += "[\u00daltimos Tweets]\n";
						for (int j = 0; j < cincoUltimosTweetsUsuario.size(); j++) {
							exibirNaTela += " -> " + ((Status) cincoUltimosTweetsUsuario.get(j)).getText() + "\n";
						}
					}
				} else {
					exibirNaTela += msgErroTwitter + "\n";
				}
				
				if (msgErroFacebook.trim().equals("")) {
					
				} else {
					exibirNaTela += msgErroFacebook + "\n";
				}
				
				System.out.println(exibirNaTela);
			}
                               // OMP USER CODE ENDS
                               if (i == (__omp_WholeData2.stop-1)) amLast = true;
                             } // of for 
                             if(__omp_ChunkData1.startStep == 0)
                               break;
                             __omp_ChunkData1.start += __omp_ChunkData1.startStep;
                             __omp_ChunkData1.stop += __omp_ChunkData1.startStep;
                           } // of for(;;)
                           } // of while
                           // call reducer
                           jomp.runtime.OMP.doBarrier(__omp_me);
                           // copy lastprivate variables out
                           if (amLast) {
                           }
                         }
                         // set global from lastprivate variables
                         if (amLast) {
                         }
                         // set global from reduction variables
                         if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                         }
                         } // OMP FOR BLOCK ENDS

		}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

