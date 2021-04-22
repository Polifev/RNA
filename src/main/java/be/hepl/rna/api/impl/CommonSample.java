package be.hepl.rna.api.impl;

import be.hepl.rna.api.ISample;

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
