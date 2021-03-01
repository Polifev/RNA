package be.hepl.rna.neurons.perceptron;

import be.hepl.rna.neurons.ISample;

public class DoubleSample implements ISample<Double> {
	private double[] sample;

	public DoubleSample(double...sample) {
		this.sample = sample;
	}

	@Override
	public int dimension() {
		return sample.length;
	}

	@Override
	public Double elementAt(int index) {
		return sample[index];
	}
}
