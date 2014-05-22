package Bampi;

import jomp.runtime.Lock;
import jomp.runtime.OMP;


public class OmpValidacao_jomp {

	
	private Object[] vetorTweetsBruto;
	
	public OmpValidacao_jomp(Object[] vetorTweetsBruto) {
		this.vetorTweetsBruto = vetorTweetsBruto;
	}
	
	public void validaTweets(Lock jompLock) {
		OMP.setNumThreads(vetorTweetsBruto.length);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.jompLock = jompLock;
  __omp_Object0.vetorTweetsBruto = vetorTweetsBruto;
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
  vetorTweetsBruto = __omp_Object0.vetorTweetsBruto;
}
// OMP PARALLEL BLOCK ENDS

		
		jompLock.unset();
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  Lock jompLock;
  Object [ ] vetorTweetsBruto;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

		{
			System.out.println(vetorTweetsBruto[OMP.getThreadNum()]);
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

