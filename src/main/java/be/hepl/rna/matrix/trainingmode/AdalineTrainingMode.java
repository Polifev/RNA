package be.hepl.rna.matrix.trainingmode;

import java.util.List;

import be.hepl.rna.common.IIterationEvaluation;
import be.hepl.rna.common.ILayer;
import be.hepl.rna.common.ISampleEvaluation;
import be.hepl.rna.common.ITrainingMode;
import be.hepl.rna.common.impl.ActivationFunctions;
import be.hepl.rna.matrix.MatrixFunctions;
import be.hepl.rna.matrix.ScalarMultiplication;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

/**
 * Update weights for each samples
 */
public class AdalineTrainingMode implements ITrainingMode<DoubleMatrix1D, DoubleMatrix2D> {
	
	
	@Override
	public void sampleBasedWeightsCorrection(ISampleEvaluation<DoubleMatrix1D> sampleEvaluation, List<ILayer<DoubleMatrix2D>> layers) {
		if(layers.size() > 1) {
			// Backpropagation
			
			//Start from last layer
			int index = layers.size() - 1;
			ILayer<DoubleMatrix2D> lastLayer = layers.get(index);
			
			//Compute Error
			DoubleMatrix1D error = sampleEvaluation.getExpectedOutput().copy().assign(sampleEvaluation.getLayerOutputs()[index + 1], MatrixFunctions.SUBSTRACTION);
			double E = 1.0/2 * error.aggregate(MatrixFunctions.ADDITION, MatrixFunctions.SQR);// E = 1/2 * SUM(error^2)
			//System.out.println("Loss: " + E);
			
			//Compute error signal on last layer
			DoubleMatrix1D signalError = error.like();
			
			//Compute potentials in derivated function
			DoubleMatrix1D potentialsValues = sampleEvaluation.getLayerPotentials()[index + 1];
			potentialsValues.assign(v -> ActivationFunctions.getDerivated(lastLayer.getActivationFunctionName()).apply(v));
			
			signalError = potentialsValues.assign(error, MatrixFunctions.PRODUCT);
			DoubleMatrix1D[] signalErrors = new DoubleMatrix1D[layers.size()];
			signalErrors[index] = signalError;// Array of signal error corresponding to each layer
			
			//Hidden layers
			for(int i = index-1 ; i >= 0 ; i--) {
				//Compute error in hidden layer
				ILayer<DoubleMatrix2D> nextLayer = layers.get(i+1);
				DoubleMatrix2D weights = nextLayer.getWeights();
				DoubleMatrix2D transposedweights = Algebra.DEFAULT.transpose(weights.copy().viewPart(0, 1, weights.rows(), weights.columns()-1));
				DoubleMatrix1D hiddenError = Algebra.DEFAULT.mult(transposedweights, signalError);
				
				//Compute potentials in derivated function
				potentialsValues = sampleEvaluation.getLayerPotentials()[i + 1];
				potentialsValues.assign(v -> ActivationFunctions.getDerivated(lastLayer.getActivationFunctionName()).apply(v));
				
				DoubleMatrix1D hiddenSignalError = hiddenError.assign(potentialsValues, MatrixFunctions.PRODUCT);
				
				signalError = hiddenSignalError;
				signalErrors[i] = hiddenSignalError;
			}
			
			//Updating Weights
			for(int i = index ; i >= 0 ; i--) {
				ILayer<DoubleMatrix2D> layer = layers.get(i);
				DoubleMatrix2D weights = layer.getWeights();
				
				DoubleMatrix1D input = sampleEvaluation.getLayerOutputs()[i];
				
				//Update Weights
				DoubleMatrix2D correction = weights.like();
				correction = Algebra.DEFAULT.multOuter(signalErrors[i], input, correction);
				correction.assign(new ScalarMultiplication(layer.getLearningRate()));
				
				weights.assign(correction, MatrixFunctions.ADDITION);
			}
			
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
	public void iterationBasedWeightsCorrection(IIterationEvaluation<DoubleMatrix1D> iteration, List<ILayer<DoubleMatrix2D>> layers) {
		// Not in Adaline
	}

}