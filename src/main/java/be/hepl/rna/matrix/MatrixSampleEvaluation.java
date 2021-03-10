package be.hepl.rna.matrix;

import be.hepl.rna.common.ISampleEvaluation;
import cern.colt.matrix.DoubleMatrix1D;

public class MatrixSampleEvaluation implements ISampleEvaluation<DoubleMatrix1D>{
	private DoubleMatrix1D givenInput;
	private DoubleMatrix1D expectedOutput;
	private DoubleMatrix1D[] layerPotentials;
	private DoubleMatrix1D[] layerOutputs;
	
	MatrixSampleEvaluation() {}
	
	public DoubleMatrix1D getGivenInput() {
		return givenInput;
	}
	public DoubleMatrix1D getExpectedOutput() {
		return expectedOutput;
	}
	public DoubleMatrix1D[] getLayerPotentials() {
		return layerPotentials;
	}
	public DoubleMatrix1D[] getLayerOutputs() {
		return layerOutputs;
	}
	public void setGivenInput(DoubleMatrix1D givenInput) {
		this.givenInput = givenInput;
	}
	public void setExpectedOutput(DoubleMatrix1D expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
	public void setLayerPotentials(DoubleMatrix1D[] layerPotentials) {
		this.layerPotentials = layerPotentials;
	}
	public void setLayerOutputs(DoubleMatrix1D[] layerOutputs) {
		this.layerOutputs = layerOutputs;
	}
}
