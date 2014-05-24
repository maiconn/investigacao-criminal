package Bampi;

import jomp.runtime.OMP;

public class OmpVariasVezes {

	public static void main(String[] args) {
		
		OMP.setNumThreads(15);
		
		for (int i = 0; i < 500; i++) {
		
			
			//omp parallel
			{
				System.out.print(OMP.getThreadNum() + ", ");
			}
			System.out.println("");
		}
	}

}
