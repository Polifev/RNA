package be.hepl.rna;

import cern.colt.function.DoubleFunction;

public class ActivationFunctions {
	public static final DoubleFunction IDENTITY = val -> val;
	public static final DoubleFunction IDENTITY_DER = val -> 1;

	public static final DoubleFunction THRESHOLD = val -> val > 0 ? 1.0 : 0.0;

	public static final DoubleFunction get(String name) {
		switch (name.toLowerCase()) {
		case "identity":
			return IDENTITY;
		case "threshold":
			return THRESHOLD;
		default:
			throw new IllegalArgumentException();
		}
	}

	public static final DoubleFunction getDerivated(String name) {
		switch (name.toLowerCase()) {
		case "identity":
			return IDENTITY_DER;
		default:
			throw new IllegalArgumentException();
		}
	}
}
