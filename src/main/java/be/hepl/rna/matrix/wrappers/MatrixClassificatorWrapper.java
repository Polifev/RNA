package be.hepl.rna.matrix.wrappers;

import be.hepl.rna.common.IClassificator;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.ISampleEvaluation;
import be.hepl.rna.common.impl.CommonSample;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

/**
 * A class that classifies elements using a MatrixNeuralNetwork.
 * Pay attention that if no class matches (no output breaks the threshold), the MINUS-ONE class is returned
 * @author Pol
 *
 */
public class MatrixClassificatorWrapper implements IClassificator{

	private double threshold;
	private INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network;
	
	public MatrixClassificatorWrapper(INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network, double threshold) {
		this.network = network;
		this.threshold = threshold;
	}

	
	@Override
	public int classify(double... inputs) {
		ISample s = new CommonSample(inputs);
		ISampleEvaluation<DoubleMatrix1D> result = network.evaluate(s);
		
		double max = Double.MIN_VALUE;
		int maxIndex = -1;
		DoubleMatrix1D c = result.getLayerOutputs()[result.getLayerOutputs().length - 1];
		for(int i = 0; i < c.size(); i++) {
			double elt = c.getQuick(i);
			if(elt > threshold && elt > max) {
				max = elt;
				maxIndex = i;
			}
		}
		return maxIndex;
	}

}
