package be.hepl.rna.matrix;

import cern.colt.matrix.DoubleMatrix1D;

public class SampleEvaluation {
	private DoubleMatrix1D givenInput;
	private DoubleMatrix1D expectedOutput;
	private DoubleMatrix1D[] layerPotentials;
	private DoubleMatrix1D[] layerOutputs;
	
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
