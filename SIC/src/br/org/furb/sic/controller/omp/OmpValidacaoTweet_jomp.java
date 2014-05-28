package br.org.furb.sic.controller.omp;

import java.util.HashMap;
import java.util.List;

import br.org.furb.sic.controller.FacebookController;
import br.org.furb.sic.controller.TwitterController;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet_jomp {

	
	private TwitterController tc;
	private FacebookController fc;
	private List listTweetsFiltrado;
	private HashMap listaCincoUltimosTweets;
	private HashMap listaPerfisFacebook;
	
	public OmpValidacaoTweet_jomp(List listTweetsFiltrado, HashMap listaCincoUltimosTweets, HashMap listaPerfisFacebook) {
		this.tc = TwitterController.getInstance();
		this.fc = FacebookController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
		this.listaCincoUltimosTweets = listaCincoUltimosTweets;
		this.listaPerfisFacebook = listaPerfisFacebook;
		
		OMP.setNumThreads(15);
	}
	
	public int validaTweets(List listTweetsBruto, int qtdeTweetsBruto) {

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.listTweetsBruto = listTweetsBruto;
  __omp_Object0.listaPerfisFacebook = listaPerfisFacebook;
  __omp_Object0.listaCincoUltimosTweets = listaCincoUltimosTweets;
  __omp_Object0.listTweetsFiltrado = listTweetsFiltrado;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  qtdeTweetsBruto  += __omp_Object0._rd_qtdeTweetsBruto;
  // shared variables
  listTweetsBruto = __omp_Object0.listTweetsBruto;
  listaPerfisFacebook = __omp_Object0.listaPerfisFacebook;
  listaCincoUltimosTweets = __omp_Object0.listaCincoUltimosTweets;
  listTweetsFiltrado = __omp_Object0.listTweetsFiltrado;
}
// OMP PARALLEL BLOCK ENDS

		for (int i = 0; i < listTweetsBruto.size(); i++) {
			if (listTweetsBruto.get(i) != null) {
				Status status = (Status)listTweetsBruto.get(i);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class1 __omp_Object1 = new __omp_Class1();
  // shared variables
  __omp_Object1.status = status;
  __omp_Object1.i = i;
  __omp_Object1.qtdeTweetsBruto = qtdeTweetsBruto;
  __omp_Object1.listTweetsBruto = listTweetsBruto;
  __omp_Object1.listaPerfisFacebook = listaPerfisFacebook;
  __omp_Object1.listaCincoUltimosTweets = listaCincoUltimosTweets;
  __omp_Object1.listTweetsFiltrado = listTweetsFiltrado;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object1);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  status = __omp_Object1.status;
  i = __omp_Object1.i;
  qtdeTweetsBruto = __omp_Object1.qtdeTweetsBruto;
  listTweetsBruto = __omp_Object1.listTweetsBruto;
  listaPerfisFacebook = __omp_Object1.listaPerfisFacebook;
  listaCincoUltimosTweets = __omp_Object1.listaCincoUltimosTweets;
  listTweetsFiltrado = __omp_Object1.listTweetsFiltrado;
}
// OMP PARALLEL BLOCK ENDS

			}
		}
		return qtdeTweetsBruto;
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class1 extends jomp.runtime.BusyTask {
  // shared variables
  Status status;
  int i;
  int qtdeTweetsBruto;
  List listTweetsBruto;
  HashMap listaPerfisFacebook;
  HashMap listaCincoUltimosTweets;
  List listTweetsFiltrado;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

				{
                                         { // OMP SECTIONS BLOCK BEGINS
                                         // copy of firstprivate variables, initialized
                                         // copy of lastprivate variables
                                         // variables to hold result of reduction
                                         boolean amLast=false;
                                         {
                                           // firstprivate variables + init
                                           // [last]private variables
                                           // reduction variables + init to default
                                           // -------------------------------------
                                           __ompName_2: while(true) {
                                           switch((int)jomp.runtime.OMP.getTicket(__omp_me)) {
                                           // OMP SECTION BLOCK 0 BEGINS
                                             case 0: {
                                           // OMP USER CODE BEGINS

						{
							listaCincoUltimosTweets.put(status.getUser().getId(), tc.cincoUltimosTweetsUsuario(status.getUser().getId()));
						}
                                           // OMP USER CODE ENDS
                                             };  break;
                                           // OMP SECTION BLOCK 0 ENDS
                                           // OMP SECTION BLOCK 1 BEGINS
                                             case 1: {
                                           // OMP USER CODE BEGINS

						{
							listaPerfisFacebook.put(status.getUser().getId(), fc.buscaPerfilFacebook(status.getUser().getName()));
						}
                                           // OMP USER CODE ENDS
                                           amLast = true;
                                             };  break;
                                           // OMP SECTION BLOCK 1 ENDS

                                             default: break __ompName_2;
                                           } // of switch
                                           } // of while
                                           // call reducer
                                           jomp.runtime.OMP.resetTicket(__omp_me);
                                           jomp.runtime.OMP.doBarrier(__omp_me);
                                           // copy lastprivate variables out
                                           if (amLast) {
                                           }
                                         }
                                         // update lastprivate variables
                                         if (amLast) {
                                         }
                                         // update reduction variables
                                         if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                                         }
                                         } // OMP SECTIONS BLOCK ENDS

				}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  List listTweetsBruto;
  HashMap listaPerfisFacebook;
  HashMap listaCincoUltimosTweets;
  List listTweetsFiltrado;
  // firstprivate variables
  // variables to hold results of reduction
  int _rd_qtdeTweetsBruto;

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
  int qtdeTweetsBruto = 0;
    // OMP USER CODE BEGINS

		{
			if (OMP.getThreadNum() < listTweetsBruto.size()) {
				if ((tc.isValidTweet((Status)listTweetsBruto.get(OMP.getThreadNum())))) {
                                         // OMP CRITICAL BLOCK BEGINS
                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                         // OMP USER CODE BEGINS

					{
						listTweetsFiltrado.add((Status)listTweetsBruto.get(OMP.getThreadNum()));
					}
                                         // OMP USER CODE ENDS
                                         }
                                         // OMP CRITICAL BLOCK ENDS

				} else {
					listTweetsBruto.set(OMP.getThreadNum(), null);
				}
				qtdeTweetsBruto++;
			}
		}
    // OMP USER CODE ENDS
  // call reducer
  qtdeTweetsBruto = (int) jomp.runtime.OMP.doPlusReduce(__omp_me, qtdeTweetsBruto);
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
    _rd_qtdeTweetsBruto = qtdeTweetsBruto;
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

