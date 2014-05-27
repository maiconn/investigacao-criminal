package Bampi;

import jomp.runtime.Lock;
import jomp.runtime.OMP;

public class ComportamentoOmpForComSections {

	public static void main(String[] args) {
		
		OMP.setNumThreads(15);

		//omp parallel
		{
			//omp for
			for (int i = 0; i < 100; i++) {
				String exibirNaTela = "";
				SectionAnal_jomp s = new SectionAnal_jomp();
				exibirNaTela += s.funcaoBesta();
				exibirNaTela += "Após as sections";
				System.out.println(exibirNaTela);
			}
		}
	}

}
