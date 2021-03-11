package be.hepl.rna.matrix.trainingmode;

import java.util.List;

import be.hepl.rna.common.IIterationEvaluation;
import be.hepl.rna.common.ILayer;
import be.hepl.rna.common.ISampleEvaluation;
import be.hepl.rna.common.ITrainingMode;
import be.hepl.rna.matrix.MatrixFunctions;
import be.hepl.rna.matrix.ScalarMultiplication;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

public class PerceptronTrainingMode implements ITrainingMode<DoubleMatrix1D, DoubleMatrix2D> {
	
	@Override
	public void sampleBasedWeightsCorrection(ISampleEvaluation<DoubleMatrix1D> sampleEvaluation,
			List<ILayer<DoubleMatrix2D>> layers) {
		if(layers.size() > 1) {
			// TODO backpropagation
		} else {
			ILayer<DoubleMatrix2D> l = layers.get(0);
			
			// Compute the error
			DoubleMatrix1D error = sampleEvaluation.getExpectedOutput().copy().assign(sampleEvaluation.getLayerOutputs()[1], MatrixFunctions.SUBSTRACTION);
			
			// Create the corrected weights matrix
			DoubleMatrix2D expanded = l.getWeights().like();
			expanded = Algebra.DEFAULT.multOuter(error, sampleEvaluation.getGivenInput(), expanded);
			expanded.assign(new ScalarMultiplication(l.getLearningRate()));
			
			// Apply the correction to the layer's weights
			l.getWeights().assign(expanded, MatrixFunctions.ADDITION);
		}
	}

	@Override
	public void iterationBasedWeightsCorrection(IIterationEvaluation<DoubleMatrix1D> iteration,
			List<ILayer<DoubleMatrix2D>> layers) {
		// TODO Auto-generated method stub
		
	}

}
