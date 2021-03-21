package be.hepl.rna.examples;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import be.hepl.rna.charts.ClassificationChart;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.matrix.MatrixClassificatorWrapper;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.trainingmode.PerceptronTrainingMode;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class PerceptronAnd {

	// Entrées : 2
	// Sorties : 1
	// Jeu d'apprentissage : 4 exemples

	public PerceptronAnd(int learningRate) {

		// Initializing a list of samples
		List<ILabeledSample> trainingSamples = new ArrayList<>();
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 0.0 }, new double[] { 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 1.0 }, new double[] { 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 0.0 }, new double[] { 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 1.0 }, new double[] { 1.0 }));

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new PerceptronTrainingMode());
		model.addLayer(new MatrixLayer(learningRate, 2, 1, "threshold"));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
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

		ClassificationChart chart = new ClassificationChart("AND classification", new String[] {"False", "True"}, true);
		chart.setClassificator(new MatrixClassificatorWrapper(model, 0.01),
				-0.1, 1.1, 0.01,
				-0.1, 1.1, 0.01);
		chart.setData(trainingSamples);

		SwingUtilities.invokeLater(() -> {
			JFrame chartFrame = chart.asJFrame();
			chartFrame.setSize(800, 600);
			chartFrame.setLocationRelativeTo(null);
			chartFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			chartFrame.setVisible(true);
		});
	}

}