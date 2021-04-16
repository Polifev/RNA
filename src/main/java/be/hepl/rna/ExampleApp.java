package be.hepl.rna;

import be.hepl.rna.examples.ImageExample;
import be.hepl.rna.examples.MonoLayerExamples;
import be.hepl.rna.examples.MonoNeuronExamples;
import be.hepl.rna.examples.MultilayerExamples;

public class ExampleApp {
	public static void main(String[] args) {		
		MonoNeuronExamples.and();//BasicPerceptron
		MonoNeuronExamples.table2_9();//Adaline
		MonoNeuronExamples.table2_10();//Full Batch
		MonoNeuronExamples.table2_11();//Adaline
		
		MonoLayerExamples.table3_1();//Adaline
		MonoLayerExamples.table3_5();//Adaline
		
		MultilayerExamples.xor();//Full Batch
		MultilayerExamples.table4_12();//Adaline
		MultilayerExamples.table4_14();//Adaline
		MultilayerExamples.table4_17();//Full Batch
		MultilayerExamples.test_sacha();
		
		ImageExample.processImages();// Résultat en console + voir jeu de test
	}
}
