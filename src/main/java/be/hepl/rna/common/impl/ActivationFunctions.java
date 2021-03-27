package be.hepl.rna.common.impl;

import java.util.function.Function;

public class ActivationFunctions {
	public static final Function<Double, Double> IDENTITY = val -> val;
	public static final Function<Double, Double> IDENTITY_DER = val -> 1.0;
	
	public static final Function<Double, Double> THRESHOLD = val -> val > 0 ? 1.0 : 0.0;
	
	public static final Function<Double,Double> SIGMOID = val -> 1.0 / (1 + Math.exp(-val));
	public static final Function<Double,Double> SIGMOID_DER = val -> {
		double sig = SIGMOID.apply(val);
		return sig * (1 - sig);
	};
	
	
	public static final Function<Double, Double> get(String name) {
		switch (name.toLowerCase()) {
		case "identity":
			return IDENTITY;
		case "threshold":
			return THRESHOLD;
		case "sigmoid": 
			return SIGMOID;
		default:
			throw new IllegalArgumentException("No function called '" + name + "'");
		}
	}

	public static final Function<Double, Double> getDerivated(String name) {
		switch (name.toLowerCase()) {
		case "identity":
			return IDENTITY_DER;
		case "sigmoid":
			return SIGMOID_DER;
		default:
			throw new IllegalArgumentException("No derivated function for '" + name + "'");
		}
	}
}
