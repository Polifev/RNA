package be.hepl.rna.examples;

import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import be.hepl.rna.charts.ClassificationChart;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.impl.GaussianWeightsInitializer;
import be.hepl.rna.common.impl.ZeroWeightsInitializer;
import be.hepl.rna.io.CsvSampleImporter;
import be.hepl.rna.io.ISampleImporter;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.stopconditions.LossCondition;
import be.hepl.rna.matrix.trainingmode.AdalineTrainingMode;
import be.hepl.rna.matrix.wrappers.MatrixClassificatorWrapper;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class MonoLayerExamples {

	public static void table3_1() {
		final int MAX_IT = 10_000;

		// Initializing a list of samples
		ISampleImporter importer = new CsvSampleImporter(
				MultilayerExamples.class.getResourceAsStream("/table_3_1.csv"), ",");
		List<ILabeledSample> trainingSamples = importer.importSample(2);

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.01, 2, 3, "identity", new ZeroWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		model.setEarlyStoppingCondition(new LossCondition(0.07));

		// Start training
		model.prepareTraining(trainingSamples);

		try {
			model.train(MAX_IT);
			System.out.println("======TRAINED======");

			// Show results
			ClassificationChart chart = new ClassificationChart("Three class linear separation",
					new String[] { "No class", "Class 1", "Class 2", "Class 3" }, true);
			chart.setClassificator(new MatrixClassificatorWrapper(model, 0.5), -2, 4, 0.1, -2, 8, 0.1);
			chart.setData(trainingSamples);

			SwingUtilities.invokeLater(() -> {
				JFrame chartFrame = chart.asJFrame();
				chartFrame.setSize(500, 500);
				chartFrame.setLocationRelativeTo(null);
				chartFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				chartFrame.setVisible(true);
			});
		} catch (OperationNotSupportedException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void table3_5() {
		final int MAX_IT = 10_000;

		// Initializing a list of samples
		ISampleImporter importer = new CsvSampleImporter(
				MultilayerExamples.class.getResourceAsStream("/table_3_5.csv"), ",");
		List<ILabeledSample> trainingSamples = importer.importSample(25);

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.01, 25, 4, "identity", new ZeroWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		model.setEarlyStoppingCondition(new LossCondition(0.01));

		// Start training
		model.prepareTraining(trainingSamples);

		try {
			model.train(MAX_IT);
			System.out.println("======TRAINED======");

			System.out.println("CANNOT SHOW GRAPHICAL RESULTS !!!");
			// Show results
			// TODO show error, loss, weights or something else ???
			
		} catch (OperationNotSupportedException e) {
			System.err.println(e.getMessage());
		}
	}

}
