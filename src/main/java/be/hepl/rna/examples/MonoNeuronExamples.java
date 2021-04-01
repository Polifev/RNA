package be.hepl.rna.examples;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import be.hepl.rna.charts.ClassificationChart;
import be.hepl.rna.charts.RegressionChart;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.common.impl.ZeroWeightsInitializer;
import be.hepl.rna.io.CsvSampleImporter;
import be.hepl.rna.matrix.MatrixClassificatorWrapper;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixModelWrapper;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.trainingmode.AdalineTrainingMode;
import be.hepl.rna.matrix.trainingmode.BasicPerceptronTrainingMode;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class MonoNeuronExamples {

	public static void and() {
		// Initializing a list of samples
		List<ILabeledSample> trainingSamples = new ArrayList<>();
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 0.0 }, new double[] { 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 1.0 }, new double[] { 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 0.0 }, new double[] { 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 1.0 }, new double[] { 1.0 }));

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(
				new BasicPerceptronTrainingMode());
		model.addLayer(new MatrixLayer(0.8, 2, 1, "threshold", new ZeroWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		// Start training
		model.prepareTraining(trainingSamples);
		try {
			model.train(1000);
			System.out.println("======TRAINED======");

			ClassificationChart chart = new ClassificationChart("AND", new String[] { "False", "True" }, true);
			chart.setClassificator(new MatrixClassificatorWrapper(model, 0.01), -0.1, 1.1, 0.01, -0.1, 1.1, 0.01);
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

	public static void table2_9() {
		// Initializing a list of samples
		List<ILabeledSample> trainingSamples = new CsvSampleImporter(MonoNeuronExamples.class.getResourceAsStream("/table_2_9.csv"), ",").importSample(2);

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(
				new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.0011, 2, 1, "identity", new ZeroWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		// Start training
		model.prepareTraining(trainingSamples);
		try {
			model.train(10000);
			System.out.println("======TRAINED======");

			ClassificationChart chart = new ClassificationChart("Table 2.9", new String[] { "-1", "1" }, true);
			chart.setClassificator(new MatrixClassificatorWrapper(model, 0.0), 0.0, 8, 0.05, 4, 12, 0.05);
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
	
	public static void table2_10() {
		// Initializing a list of samples
		List<ILabeledSample> trainingSamples = new CsvSampleImporter(MonoNeuronExamples.class.getResourceAsStream("/table_2_10.csv"), ",").importSample(2);

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(
				new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.0015, 2, 1, "identity", new ZeroWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		// Start training
		model.prepareTraining(trainingSamples);
		try {
			model.train(10000);
			System.out.println("======TRAINED======");

			ClassificationChart chart = new ClassificationChart("Table 2.10", new String[] { "-1", "1" }, true);
			chart.setClassificator(new MatrixClassificatorWrapper(model, 0.0), 0.0, 8, 0.05, 0, 8, 0.05);
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
	
	public static void table2_11() {
		// Initializing a list of samples
		List<ILabeledSample> trainingSamples = new CsvSampleImporter(MonoNeuronExamples.class.getResourceAsStream("/table_2_11.csv"), ",").importSample(1);

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(
				new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.00016, 1, 1, "identity", new ZeroWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		// Start training
		model.prepareTraining(trainingSamples);
		try {
			model.train(10000);
			System.out.println("======TRAINED======");

			RegressionChart chart = new RegressionChart("Table 2.11");
			chart.setLinearModel(new MatrixModelWrapper(model), 0.0, 35, 0.2);
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