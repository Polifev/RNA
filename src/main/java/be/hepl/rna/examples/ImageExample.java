package be.hepl.rna.examples;

import java.util.List;

import be.hepl.rna.ExampleApp;
import be.hepl.rna.common.IClassificator;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.impl.GaussianWeightsInitializer;
import be.hepl.rna.io.ISampleImporter;
import be.hepl.rna.io.ZipSampleImporter;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.stopconditions.LossCondition;
import be.hepl.rna.matrix.trainingmode.FullBatchGradientDescentTrainingMode;
import be.hepl.rna.matrix.wrappers.MatrixClassificatorWrapper;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class ImageExample {

	/**
	 * Cet exemple utilise deux sets d'images de taille 16x16 (1 pour l'entrainement et 1 pour valider le modèle)
	 * Les images sont groupées dans un fichier .zip
	 */
	public static void processImages() {
		final int MAX_IT = 10_000;

		// Initializing a list of samples
		ISampleImporter importer = new ZipSampleImporter(ExampleApp.class.getResourceAsStream("/image/symbols.zip"), ",");
		List<ILabeledSample> trainingSamples = importer.importSample(-1);
		System.out.println("Training on " + trainingSamples.size() + " training samples");

		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(
				new FullBatchGradientDescentTrainingMode());
		model.addLayer(new MatrixLayer(0.15, 256, 128, "sigmoid", new GaussianWeightsInitializer()));
		model.addLayer(new MatrixLayer(0.15, 128, 4, "sigmoid", new GaussianWeightsInitializer()));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));

		model.setEarlyStoppingCondition(new LossCondition(0.00005));

		// Start training
		model.prepareTraining(trainingSamples);

		try {
			model.train(MAX_IT);
			System.out.println("======TRAINED======");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] classes = new String[] { "INCONNU", "PLUS", "MOINS", "FOIS", "DIVISER" };
		IClassificator classificator = new MatrixClassificatorWrapper(model, 0.5);
		
		ISampleImporter testImporter = new ZipSampleImporter(ExampleApp.class.getResourceAsStream("/image/symbols_test.zip"), ",");
		List<ISample> testSamples = testImporter.importSample();
		System.out.println("Testing on " + testSamples.size() + " test samples");
		for(ISample sample : testSamples) {
			int classNumber = classificator.classify(sample.inputs()) + 1; // Tenir compte d'une erreur de classification
			System.out.println(classes[classNumber]);
		}
	}
}
