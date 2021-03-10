package be.hepl.rna.matrix;

import java.util.function.Function;

import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;

public class Layer {
	private double learningRate;
	private DoubleMatrix2D weights;
	private DoubleFunction activationFunction;
	
	public Layer(double learningRate, int inputSize, int outputSize, Function<Double, Double> activationFunction) {
		this.learningRate = learningRate;
		this.weights = DoubleFactory2D.dense.make(outputSize, inputSize+1);
		this.activationFunction = d -> activationFunction.apply(d);
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	public DoubleMatrix2D getWeights() {
		return weights;
	}
	public DoubleFunction getActivationFunction() {
		return activationFunction;
	}
}
