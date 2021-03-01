package be.hepl.rna;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import be.hepl.rna.neurons.ILabelledSample;
import be.hepl.rna.neurons.INeuron;
import be.hepl.rna.neurons.ISample;
import be.hepl.rna.neurons.perceptron.Perceptron;
import be.hepl.rna.neurons.perceptron.PerceptronSample;

public class App {
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		
		List<ILabelledSample<Double, Byte>> samples = new ArrayList<ILabelledSample<Double, Byte>>();
		samples.add(new PerceptronSample((byte)0, 0.0, 0.0));
		samples.add(new PerceptronSample((byte)1, 0.0, 1.0));
		samples.add(new PerceptronSample((byte)1, 1.0, 0.0));
		samples.add(new PerceptronSample((byte)1, 1.0, 1.0));
		
		INeuron<Double, Byte> perceptron = new Perceptron(2, 0.2);
		for(int i = 0; i < 10; i++) {
			System.out.println("Iteration " + (i+1));
			System.out.println("Test:");
			for(ISample<Double> sample : samples) {
				System.out.println(perceptron.evaluate(sample));
				
			}
			System.out.println("====================");
			perceptron.train(samples);
		}
	}
}
