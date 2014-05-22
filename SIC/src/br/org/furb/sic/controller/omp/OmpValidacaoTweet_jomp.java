package br.org.furb.sic.controller.omp;

import java.util.List;

import jomp.runtime.Lock;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet_jomp {

	
	public void validaTweets(List listTweetsBruto, Lock jompLock) {
		OMP.setNumThreads(listTweetsBruto.size());

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.jompLock = jompLock;
  __omp_Object0.listTweetsBruto = listTweetsBruto;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  jompLock = __omp_Object0.jompLock;
  listTweetsBruto = __omp_Object0.listTweetsBruto;
}
// OMP PARALLEL BLOCK ENDS

		
		System.out.println("\n\nLista Completa\n\n");
		
		jompLock.unset();
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  Lock jompLock;
  List listTweetsBruto;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

		{
			System.out.println(listTweetsBruto.get(OMP.getThreadNum()));
			System.out.println("N. da thread = " + OMP.getThreadNum() + "\n tamanho da lista = " + listTweetsBruto.size() + "\n");
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

