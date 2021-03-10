package be.hepl.rna;

import java.util.ArrayList;
import java.util.List;

import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.trainingmode.PerceptronTrainingMode;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class App {
	public static void main(String[] args) {
		
		// Initializing a list of samples
		List<CommonLabeledSample> trainingSamples = new ArrayList<>();
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 0.0 }, new double[] { 0.0, 0.0, 1.0, 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 1.0 }, new double[] { 1.0, 0.0, 1.0, 1.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 0.0 }, new double[] { 1.0, 0.0, 0.0, 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 1.0 }, new double[] { 1.0, 1.0, 0.0, 1.0 }));
		
		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new PerceptronTrainingMode());
		model.addLayer(new MatrixLayer(0.2, 2, 4, "threshold"));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i+1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));
		
		
		// Start training
		model.prepareTraining(trainingSamples);
		model.train(10);
		System.out.println("======TRAINED======");
		
		// Check results
		for (ISample s : trainingSamples) {
			DoubleMatrix1D[] m = model.evaluate(s).getLayerOutputs();
			System.out.println(m[m.length - 1]);
		}
	}
}
