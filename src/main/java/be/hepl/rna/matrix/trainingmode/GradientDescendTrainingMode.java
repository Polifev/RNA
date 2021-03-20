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

/**
 * Update weights for each iteration
 */
public class GradientDescendTrainingMode implements ITrainingMode<DoubleMatrix1D, DoubleMatrix2D>{
	
	@Override
	public void sampleBasedWeightsCorrection(ISampleEvaluation<DoubleMatrix1D> sampleEvaluation, List<ILayer<DoubleMatrix2D>> layers) {
		// Not in GradientDescend
	}

	@Override
	public void iterationBasedWeightsCorrection(IIterationEvaluation<DoubleMatrix1D> iteration, List<ILayer<DoubleMatrix2D>> layers) {
		if(layers.size() > 1) {
			//Backpropagation
			
		}else {
			ILayer<DoubleMatrix2D> l = layers.get(0);
			DoubleMatrix2D globalCorrection = layers.get(0).getWeights().like();//Dwi
			
			//For each sample
			for(ISampleEvaluation<DoubleMatrix1D> sample : iteration.getSampleEvaluations()) {
				//Compute the error
				DoubleMatrix1D error = sample.getExpectedOutput().copy().assign(sample.getLayerOutputs()[1], MatrixFunctions.SUBSTRACTION);
				
				//Compute local correction
				DoubleMatrix2D correction = l.getWeights().like();//dwi
				correction = Algebra.DEFAULT.multOuter(error, sample.getGivenInput(), correction);
				correction.assign(new ScalarMultiplication(l.getLearningRate()));
				
				//Add to global correction
				globalCorrection.assign(correction, MatrixFunctions.ADDITION);
			}
			
			// Apply the global correction to the layer's weights
			l.getWeights().assign(globalCorrection, MatrixFunctions.ADDITION);
		}
	}

}