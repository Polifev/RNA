package be.hepl.rna.api.impl;

import java.util.Random;

import be.hepl.rna.api.IWeightsInitializer;

public class GaussianWeightsInitializer implements IWeightsInitializer{
	
	private Random rand;
	private double mu, sigma;
	
	public GaussianWeightsInitializer() {
		this(0.0, 1.0);
	}
	
	public GaussianWeightsInitializer(double mu, double sigma) {
		this.rand = new Random();
		this.mu = mu;
		this.sigma = sigma;
	}

	@Override
	public double getWeight(int row, int col) {
		return (rand.nextGaussian() * this.sigma + this.mu);
	}
	
}
