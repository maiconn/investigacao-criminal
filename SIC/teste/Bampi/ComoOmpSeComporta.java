package Bampi;

import jomp.runtime.OMP;

public class ComoOmpSeComporta {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		OMP.setNumThreads(0);
		
		//omp parallel
		{
			System.out.println(OMP.getThreadNum());
		}

	}

}
