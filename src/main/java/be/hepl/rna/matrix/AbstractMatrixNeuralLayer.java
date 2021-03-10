package be.hepl.rna.matrix;

import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

public abstract class AbstractMatrixNeuralLayer {
	private double learningRate;
	private DoubleMatrix2D weights;
	private DoubleFunction activationFunction;
	
	public AbstractMatrixNeuralLayer(int input, int output, DoubleFunction activationFunction, double learningRate){
		this.weights = DoubleFactory2D.dense.make(output, input+1);
		this.activationFunction = activationFunction;
		this.learningRate = learningRate;
	}
	
	protected DoubleMatrix2D getWeights() {
		return weights;
	}
	
	protected DoubleFunction getActivationFunction() {
		return activationFunction;
	}
	
	protected double getLearningRate() {
		return learningRate;
	}
	
	public DoubleMatrix1D computeRaw(DoubleMatrix1D input) {
		return Algebra.DEFAULT.mult(weights, input);
	}
	
	public DoubleMatrix1D compute(DoubleMatrix1D input) {
		return computeRaw(input).assign(activationFunction);
	}
	
	public void correctWeights(DoubleMatrix1D input, DoubleMatrix1D error) {
		DoubleMatrix2D expanded = DoubleFactory2D.dense.make(error.size(), input.size());
		expanded = Algebra.DEFAULT.multOuter(error, input, expanded);
		expanded.assign(new ScalarMultiplication(learningRate));
		this.weights = this.weights.assign(expanded, CommonFunctions.ADDITION);
	}
}
