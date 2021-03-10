package be.hepl.rna;

import java.util.ArrayList;
import java.util.List;

import be.hepl.rna.matrix.Layer;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import cern.colt.matrix.DoubleMatrix1D;

public class App {
	public static void main(String[] args) {
		MatrixNeuralNetwork model = new MatrixNeuralNetwork();
		model.addLayer(new Layer(0.2, 2, 4, (d) -> d > 0 ? 1.0 : 0.0));

		List<LabeledSample> trainingSamples = new ArrayList<>();
		trainingSamples.add(new LabeledSample(new double[] { 0.0, 0.0 }, new double[] { 0.0, 0.0, 1.0, 0.0 }));
		trainingSamples.add(new LabeledSample(new double[] { 0.0, 1.0 }, new double[] { 1.0, 0.0, 1.0, 1.0 }));
		trainingSamples.add(new LabeledSample(new double[] { 1.0, 0.0 }, new double[] { 1.0, 0.0, 0.0, 0.0 }));
		trainingSamples.add(new LabeledSample(new double[] { 1.0, 1.0 }, new double[] { 1.0, 1.0, 0.0, 1.0 }));

		model.onIterationEnds(it -> System.out.println("Iteration " + it.getIterationNumber() + " finished"));
		model.onSampleProcessed(s -> System.out.println("Sample processed"));
		
		model.prepareTraining(trainingSamples);
		model.trainPerceptron(10);
		System.out.println("======TRAINED======");
		
		
		for (Sample s : trainingSamples) {
			DoubleMatrix1D[] m = model.evaluate(s).getLayerOutputs();
			System.out.println(m[m.length - 1]);
		}
	}
}
