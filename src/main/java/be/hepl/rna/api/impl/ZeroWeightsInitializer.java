package be.hepl.rna.api.impl;

import be.hepl.rna.api.IWeightsInitializer;

public class ZeroWeightsInitializer implements IWeightsInitializer {

	@Override
	public double getWeight(int row, int col) {
		return 0;
	}

}
