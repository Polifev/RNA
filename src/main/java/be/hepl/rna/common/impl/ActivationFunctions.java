package be.hepl.rna.common.impl;

import java.util.function.Function;

public class ActivationFunctions {
	public static final Function<Double, Double> IDENTITY = val -> val;
	public static final Function<Double, Double> IDENTITY_DER = val -> 1.0;
	
	public static final Function<Double, Double> THRESHOLD = val -> val > 0 ? 1.0 : 0.0;

	public static final Function<Double, Double> get(String name) {
		switch (name.toLowerCase()) {
		case "identity":
			return IDENTITY;
		case "threshold":
			return THRESHOLD;
		default:
			throw new IllegalArgumentException("No function called '" + name + "'");
		}
	}

	public static final Function<Double, Double> getDerivated(String name) {
		switch (name.toLowerCase()) {
		case "identity":
			return IDENTITY_DER;
		default:
			throw new IllegalArgumentException("No derivated function for '" + name + "'");
		}
	}
}
