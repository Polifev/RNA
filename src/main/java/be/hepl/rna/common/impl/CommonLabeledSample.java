package be.hepl.rna.common.impl;

import be.hepl.rna.common.ILabeledSample;

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
