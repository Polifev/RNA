package be.hepl.rna.examples;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import be.hepl.rna.charts.ClassificationChart;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.common.impl.GaussianWeightsInitializer;
import be.hepl.rna.io.CsvSampleImporter;
import be.hepl.rna.io.ISampleImporter;
import be.hepl.rna.matrix.MatrixClassificatorWrapper;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.trainingmode.AdalineTrainingMode;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class MultilayerExamples {
	public static void xor() {
		// Initializing a list of samples
		List<ILabeledSample> trainingSamples = new ArrayList<ILabeledSample>();
		trainingSamples.add(new CommonLabeledSample(new double[] { 0, 0 }, new double[] { 0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0, 1 }, new double[] { 1 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1, 0 }, new double[] { 1 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1, 1 }, new double[] { 0 }));

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.8, 2, 2, "sigmoid", new GaussianWeightsInitializer()));
		model.addLayer(new MatrixLayer(0.8, 2, 1, "sigmoid", new GaussianWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));

		model.onIterationEnds(it -> System.out.println("...finished\n"));

		// Start training
		model.prepareTraining(trainingSamples);

		try {
			model.train(2000);
			System.out.println("======TRAINED======");
			// Check results
			ClassificationChart chart = new ClassificationChart("XOR", new String[] { "False", "True" }, true);
			chart.setClassificator(new MatrixClassificatorWrapper(model, 0.5), -0.2, 1.2, 0.01, -0.2, 1.2, 0.01);
			chart.setData(trainingSamples);
			SwingUtilities.invokeLater(() -> {
				JFrame chartFrame = chart.asJFrame();
				chartFrame.setSize(800, 600);
				chartFrame.setLocationRelativeTo(null);
				chartFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				chartFrame.setVisible(true);
			});
		} catch (OperationNotSupportedException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void twoClassesSeparation() {
		final int MAX_IT = 10_000;

		// Initializing a list of samples
		ISampleImporter importer = new CsvSampleImporter(
				MultilayerExamples.class.getResourceAsStream("/table_4_12.csv"), ",");
		List<ILabeledSample> trainingSamples = importer.importSample(2);

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.50, 2, 15, "sigmoid", new GaussianWeightsInitializer()));
		model.addLayer(new MatrixLayer(0.50, 15, 1, "sigmoid", new GaussianWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		// TODO condition d'arrêt spécifique

		// Start training
		model.prepareTraining(trainingSamples);

		try {
			model.train(MAX_IT);
			System.out.println("======TRAINED======");

			// Show results
			ClassificationChart chart = new ClassificationChart("Non-linearly-dependent, two-classes segregation",
					new String[] { "1", "0" }, true);
			chart.setClassificator(new MatrixClassificatorWrapper(model, 0.5), -2, 2, 0.03, -2, 2, 0.03);
			chart.setData(trainingSamples);

			SwingUtilities.invokeLater(() -> {
				JFrame chartFrame = chart.asJFrame();
				chartFrame.setSize(800, 600);
				chartFrame.setLocationRelativeTo(null);
				chartFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				chartFrame.setVisible(true);
			});
		} catch (OperationNotSupportedException e) {
			System.err.println(e.getMessage());
		}
	}

}
