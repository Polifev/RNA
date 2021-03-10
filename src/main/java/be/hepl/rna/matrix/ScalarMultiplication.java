package be.hepl.rna.matrix;

import cern.colt.function.DoubleFunction;

public class ScalarMultiplication implements DoubleFunction {
	private double n;

	public ScalarMultiplication(double n) {
		this.n = n;
	}

	@Override
	public double apply(double arg0) {
		return arg0 * n;
	}
}
