package be.hepl.rna.examples;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import be.hepl.rna.charts.ClassificationChart;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.common.impl.GaussianWeightsInitializer;
import be.hepl.rna.matrix.MatrixClassificatorWrapper;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.trainingmode.AdalineTrainingMode;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class MultilayerXor {
	public MultilayerXor() {	
			// Initializing a list of samples
			List<ILabeledSample> trainingSamples = new ArrayList<ILabeledSample>();
			trainingSamples.add(new CommonLabeledSample(new double[] {0, 0}, new double[] {0}));
			trainingSamples.add(new CommonLabeledSample(new double[] {0, 1}, new double[] {1}));
			trainingSamples.add(new CommonLabeledSample(new double[] {1, 0}, new double[] {1}));
			trainingSamples.add(new CommonLabeledSample(new double[] {1, 1}, new double[] {0}));
			
			// Setting up the model
			INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new AdalineTrainingMode());
			model.addLayer(new MatrixLayer(0.5, 2, 2, "sigmoid", new GaussianWeightsInitializer()));
			model.addLayer(new MatrixLayer(0.5, 2, 1, "sigmoid", new GaussianWeightsInitializer()));
	
			model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i + 1));
			
			model.onIterationEnds(it -> System.out.println("...finished\n"));
	
			// Start training
			model.prepareTraining(trainingSamples);
			model.train(10000);
			
			System.out.println("======TRAINED======");
			
			for(int i = 0; i < trainingSamples.size(); i++) {
				System.out.println(model.evaluate(trainingSamples.get(i)).getLayerPotentials()[2]);
				System.out.println(model.evaluate(trainingSamples.get(i)).getLayerOutputs()[2]);
			}
			
	
			// Check results
	
			ClassificationChart chart = new ClassificationChart("Données à 2 classes non linéairement séparables", new String[] {"0", "1"}, true);
			chart.setClassificator(new MatrixClassificatorWrapper(model, 0.5),-0.5, 1.5, 0.01,-0.5, 1.5, 0.01);
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
