package be.hepl.rna;

import java.util.ArrayList;
import java.util.List;

import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.PerceptronLayer;
import cern.colt.Arrays;

public class App {
	public static void main(String[] args) {
		MatrixNeuralNetwork model = new MatrixNeuralNetwork();
		model.addLayer(new PerceptronLayer(2, 4, 0.2));

		List<LabeledSample> trainingSamples = new ArrayList<>();
		trainingSamples.add(new LabeledSample(new double[] { 0.0, 0.0 }, new double[] { 0.0, 0.0, 1.0, 0.0 }));
		trainingSamples.add(new LabeledSample(new double[] { 0.0, 1.0 }, new double[] { 1.0, 0.0, 1.0, 1.0 }));
		trainingSamples.add(new LabeledSample(new double[] { 1.0, 0.0 }, new double[] { 1.0, 0.0, 0.0, 0.0 }));
		trainingSamples.add(new LabeledSample(new double[] { 1.0, 1.0 }, new double[] { 1.0, 1.0, 0.0, 1.0 }));

		model.onIterationEnds(itResult -> {
			System.out.printf("Iteration %d ended : %d error(s)\n", itResult.getIterationIndex() + 1,
					(int) itResult.getMetric(Metrics.ERROR_COUNT));
		});

		model.prepareTraining(trainingSamples);
		model.trainPerceptron(10, (itResult) -> itResult.getMetric(Metrics.ERROR_COUNT) == 0);

		System.out.println("======TRAINED======");
		for (Sample s : trainingSamples) {
			System.out.println(Arrays.toString(s.inputs()));
			System.out.println(Arrays.toString(model.evaluate(s)));
		}
	}
}
