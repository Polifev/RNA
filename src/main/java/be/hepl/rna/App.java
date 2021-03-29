package be.hepl.rna;

import be.hepl.rna.examples.MonoNeuronExamples;
import be.hepl.rna.examples.MultilayerExamples;

public class App {
	public static void main(String[] args) {
		MonoNeuronExamples.and();
		MultilayerExamples.xor();
		MultilayerExamples.twoClassesSeparation();
		MonoNeuronExamples.table2_9();
		MonoNeuronExamples.table2_10();
		MonoNeuronExamples.table2_11();
	}
}
