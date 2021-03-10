package be.hepl.rna.matrix;

import be.hepl.rna.common.ILayer;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;

public class MatrixLayer implements ILayer<DoubleMatrix2D> {
	private double learningRate;
	private DoubleMatrix2D weights;
	private String activationFunctionName;
	
	/**
	 * @param learningRate
	 * @param inputSize must <strong>not</strong> include the imaginary <em>x0</em> input
	 * @param outputSize
	 * @param activationFunction
	 */
	public MatrixLayer(double learningRate, int inputSize, int outputSize, String activationFunctionName) {
		this.learningRate = learningRate;
		this.weights = DoubleFactory2D.dense.make(outputSize, inputSize+1);
		this.activationFunctionName = activationFunctionName;
	}
	
	@Override
	public double getLearningRate() {
		return learningRate;
	}
	
	@Override
	public DoubleMatrix2D getWeights() {
		return weights;
	}
	
	@Override
	public String getActivationFunctionName() {
		return activationFunctionName;
	}

	@Override
	public int getInputSize() {
		return this.weights.columns();
	}
	
	@Override
	public int getOutputSize() {
		return this.weights.rows();
	}
}
