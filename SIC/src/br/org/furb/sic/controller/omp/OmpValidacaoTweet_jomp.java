package br.org.furb.sic.controller.omp;

import java.util.List;

import br.org.furb.sic.controller.TwitterController;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet_jomp {

	
	//private TwitterController tc;
	private List listTweetsFiltrado;
	
	public OmpValidacaoTweet_jomp(List listTweetsFiltrado) {
		//this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
	}
	
	public void validaTweets(List listTweetsBruto) {
		OMP.setNumThreads(listTweetsBruto.size());

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.listTweetsBruto = listTweetsBruto;
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
  listTweetsBruto = __omp_Object0.listTweetsBruto;
  listTweetsFiltrado = __omp_Object0.listTweetsFiltrado;
}
// OMP PARALLEL BLOCK ENDS

		System.out.println("");
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  List listTweetsBruto;
  List listTweetsFiltrado;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

		{
			System.out.print(OMP.getThreadNum() + ", ");
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

