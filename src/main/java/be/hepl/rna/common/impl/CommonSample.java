package be.hepl.rna.common.impl;

import be.hepl.rna.common.ISample;

public class CommonSample implements ISample {
	private double[] sample;
	
	public CommonSample(double...sample) {
		this.sample = sample;
	}
	
	@Override
	public double[] inputs() {
		return sample;
	}
}
