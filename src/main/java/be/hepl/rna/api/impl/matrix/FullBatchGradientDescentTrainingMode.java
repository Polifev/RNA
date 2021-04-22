package be.hepl.rna.api.impl.matrix;

import java.util.List;

import be.hepl.rna.api.IIterationEvaluation;
import be.hepl.rna.api.ILayer;
import be.hepl.rna.api.ISampleEvaluation;
import be.hepl.rna.api.ITrainingMode;
import be.hepl.rna.api.impl.ActivationFunctions;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

/**
 * Update weights for each iteration
 */
public class FullBatchGradientDescentTrainingMode implements ITrainingMode<DoubleMatrix1D, DoubleMatrix2D> {

	@Override
	public void sampleBasedWeightsCorrection(ISampleEvaluation<DoubleMatrix1D> sampleEvaluation,
			List<ILayer<DoubleMatrix2D>> layers) {
		// Not in GradientDescend
	}

	@Override
	public void iterationBasedWeightsCorrection(IIterationEvaluation<DoubleMatrix1D> iteration,
			List<ILayer<DoubleMatrix2D>> layers) {
		DoubleMatrix2D[] globalCorrection = new DoubleMatrix2D[layers.size()];
		for (int i = 0; i < globalCorrection.length; i++) {
			globalCorrection[i] = layers.get(i).getWeights().like();
		}

		// Back-propagation
		for (ISampleEvaluation<DoubleMatrix1D> sampleEvaluation : iteration.getSampleEvaluations()) {
			// Start from last layer
			int lastLayerIndex = layers.size() - 1;
			ILayer<DoubleMatrix2D> lastLayer = layers.get(lastLayerIndex);

			// Compute Error
			DoubleMatrix1D error = sampleEvaluation.getExpectedOutput().copy()
					.assign(sampleEvaluation.getLayerOutputs()[lastLayerIndex + 1], MatrixFunctions.SUBSTRACTION);

			// Compute signal error = (err * f'(potential) )
			DoubleMatrix1D signalError = sampleEvaluation.getLayerPotentials()[lastLayerIndex + 1].copy();
			signalError.assign(v -> ActivationFunctions.getDerivated(lastLayer.getActivationFunctionName()).apply(v));
			signalError.assign(error, MatrixFunctions.PRODUCT);

			DoubleMatrix1D[] signalErrors = new DoubleMatrix1D[layers.size()];
			signalErrors[lastLayerIndex] = signalError;// Array of signal error corresponding to each layer

			// Hidden layers
			for (int i = lastLayerIndex - 1; i >= 0; i--) {
				// Compute error in hidden layer
				ILayer<DoubleMatrix2D> nextLayer = layers.get(i + 1);
				DoubleMatrix2D weights = nextLayer.getWeights().copy();
				DoubleMatrix2D transposedweights = Algebra.DEFAULT
						.transpose(weights.viewPart(0, 1, weights.rows(), weights.columns() - 1));
				DoubleMatrix1D hiddenError = Algebra.DEFAULT.mult(transposedweights, signalError);

				// Compute potentials in derivated function
				signalError = sampleEvaluation.getLayerPotentials()[i + 1].copy();
				signalError
						.assign(v -> ActivationFunctions.getDerivated(lastLayer.getActivationFunctionName()).apply(v));
				signalError.assign(hiddenError, MatrixFunctions.PRODUCT);

				signalErrors[i] = signalError;
			}

			// Updating Weights
			for (int i = lastLayerIndex; i >= 0; i--) {
				ILayer<DoubleMatrix2D> layer = layers.get(i);
				DoubleMatrix2D weights = layer.getWeights();

				DoubleMatrix1D input = sampleEvaluation.getLayerOutputs()[i];

				// Update Weights
				DoubleMatrix2D correction = weights.like();
				correction = Algebra.DEFAULT.multOuter(signalErrors[i], input, correction);
				correction.assign(new ScalarMultiplication(layer.getLearningRate()));

				globalCorrection[i].assign(correction, MatrixFunctions.ADDITION);
			}
		}
		
		for(int i = 0; i < globalCorrection.length; i++) {
			layers.get(i).getWeights().assign(globalCorrection[i], MatrixFunctions.ADDITION);
		}
	}

}