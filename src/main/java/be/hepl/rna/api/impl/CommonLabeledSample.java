package be.hepl.rna.api.impl;

import be.hepl.rna.api.ILabeledSample;

public class CommonLabeledSample extends CommonSample implements ILabeledSample{
	private double[] expectedOutputs;
	
	public CommonLabeledSample(double[] sample, double[] expectedOutputs) {
		super(sample);
		this.expectedOutputs = expectedOutputs;
	}
	
	@Override
	public double[] expectedOutput() {
		return expectedOutputs;
	}
}
