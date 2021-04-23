package be.hepl.rna.examples;

import static be.hepl.rna.api.impl.Predicates.greaterOrEqualTo;
import static be.hepl.rna.api.impl.Sweep.sweep;

import java.util.List;

import be.hepl.rna.api.IClassificator;
import be.hepl.rna.api.ILabeledSample;
import be.hepl.rna.api.INeuralNetwork;
import be.hepl.rna.api.ISampleImporter;
import be.hepl.rna.api.impl.CsvSampleImporter;
import be.hepl.rna.api.impl.matrix.AccuracyMetric;
import be.hepl.rna.api.impl.matrix.GradientDescentTrainingMode;
import be.hepl.rna.api.impl.matrix.LossMetric;
import be.hepl.rna.api.impl.matrix.MatrixClassificatorWrapper;
import be.hepl.rna.api.impl.matrix.MatrixLayer;
import be.hepl.rna.api.impl.matrix.MatrixNeuralNetwork;
import be.hepl.rna.charts.ClassificationChart;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class LaunchExamples {

	public static void main(String[] args) {
		ISampleImporter importer = new CsvSampleImporter(LaunchExamples.class.getResourceAsStream("/table_2_9.csv"), ",", 2);
		List<ILabeledSample> samples = importer.importAsLabeledSamples();
		
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network = new MatrixNeuralNetwork();
		network.addLayer(new MatrixLayer(0.5, 2, 1, "sigmoid"));
		network.setTrainingParameters(samples.size(), new GradientDescentTrainingMode());
		network.registerMetric("accuracy", new AccuracyMetric(0.1));
		network.registerMetric("loss", new LossMetric());
		network.stopWhen("accuracy", greaterOrEqualTo(1.0));
		
		network.train(100, samples);
		
		IClassificator classificator = new MatrixClassificatorWrapper(network, 0.5);
		ClassificationChart chart = new ClassificationChart("test", new String[] {"0", "1"},  true);
		chart.setData(samples);
		chart.setClassificator(classificator, sweep(-0.1, 1.1, 0.1), sweep(-0.1, 1.1, 0.1));
		
		System.out.println(classificator.classify(0, 0));
		System.out.println(classificator.classify(0, 1));
		System.out.println(classificator.classify(1, 0));
		System.out.println(classificator.classify(1, 1));
	}
}
