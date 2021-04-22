package be.hepl.rna.api.impl.matrix;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import be.hepl.rna.api.IIterationEvaluation;
import be.hepl.rna.api.ILayer;
import be.hepl.rna.api.ISampleEvaluation;
import be.hepl.rna.api.ITrainingMode;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

public class BasicPerceptronTrainingMode implements ITrainingMode<DoubleMatrix1D, DoubleMatrix2D> {

	@Override
	public void sampleBasedWeightsCorrection(ISampleEvaluation<DoubleMatrix1D> sampleEvaluation,
			List<ILayer<DoubleMatrix2D>> layers) throws OperationNotSupportedException {
		
		if(layers.size() > 1) {
			throw new OperationNotSupportedException("Basic perceptron does not support multi-layer.");
		} else {
			ILayer<DoubleMatrix2D> l = layers.get(0);
			
			// Compute the error
			DoubleMatrix1D error = sampleEvaluation.getExpectedOutput().copy().assign(sampleEvaluation.getLayerOutputs()[1], MatrixFunctions.SUBSTRACTION);
			
			// Create the corrected weights matrix
			DoubleMatrix2D correction = l.getWeights().like();
			correction = Algebra.DEFAULT.multOuter(error, sampleEvaluation.getGivenInput(), correction);
			correction.assign(new ScalarMultiplication(l.getLearningRate()));
			
			// Apply the correction to the layer's weights
			l.getWeights().assign(correction, MatrixFunctions.ADDITION);
		}
	}

	@Override
	public void iterationBasedWeightsCorrection(IIterationEvaluation<DoubleMatrix1D> iteration,
			List<ILayer<DoubleMatrix2D>> layers) {
		// Do nothing
	}

}
