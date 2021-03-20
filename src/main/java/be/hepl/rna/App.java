package be.hepl.rna;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import be.hepl.rna.charts.RegressionChart;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.examples.PerceptronAnd;
import be.hepl.rna.matrix.MatrixLayer;
import be.hepl.rna.matrix.MatrixModelWrapper;
import be.hepl.rna.matrix.MatrixNeuralNetwork;
import be.hepl.rna.matrix.trainingmode.AdalineTrainingMode;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class App {
	public static void main(String[] args) {
		// Initializing a list of samples
		List<ILabeledSample> trainingSamples = new ArrayList<>();
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0 }, new double[] { 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.2 }, new double[] { 0.45 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.3 }, new double[] { 0.55 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.31 }, new double[] { 0.6 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.4 }, new double[] { 0.79 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.5 }, new double[] { 0.95 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.1 }, new double[] { 0.21 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.22 }, new double[] { 0.44 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.31 }, new double[] { 0.67 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.38 }, new double[] { 0.69 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.44 }, new double[] { 0.79 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.55 }, new double[] { 1.1 }));
		
		/*List<CommonLabeledSample> trainingSamples = new ArrayList<>();
		//																4 neurones =>     1         2        3    4
		//																		     (x1 || x2) (x1 && x2) (!x1) (x2)
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 0.0 }, new double[] { 0.0, 0.0, 1.0, 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 0.0, 1.0 }, new double[] { 1.0, 0.0, 1.0, 1.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 0.0 }, new double[] { 1.0, 0.0, 0.0, 0.0 }));
		trainingSamples.add(new CommonLabeledSample(new double[] { 1.0, 1.0 }, new double[] { 1.0, 1.0, 0.0, 1.0 }));*/
		
		// Setting up the model
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> model = new MatrixNeuralNetwork(new AdalineTrainingMode());
		model.addLayer(new MatrixLayer(0.05, 1, 1, "identity"));

		model.onIterationStarts(i -> System.out.printf("Iteration %d...\n", i+1));
		model.onIterationEnds(it -> System.out.println("...finished\n"));
		
		// Start training
		model.prepareTraining(trainingSamples);
		model.train(10_000);
		System.out.println("======TRAINED======");
		
		// Check results
		for (ISample s : trainingSamples) {
			DoubleMatrix1D[] m = model.evaluate(s).getLayerOutputs();
			System.out.println(m[m.length - 1]);
		}
		
		RegressionChart chart = new RegressionChart("Scatter Chart Example");
		chart.setData(trainingSamples);
		chart.setLinearModel(new MatrixModelWrapper((MatrixNeuralNetwork) model), -0.5, 1.5, 0.01);
		
		SwingUtilities.invokeLater(() -> {
		      JFrame example = chart.asJFrame();
		      example.setSize(800, 600);  
		      example.setLocationRelativeTo(null);  
		      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
		      example.setVisible(true);  
		    });
		new PerceptronAnd(1);
	}
}
