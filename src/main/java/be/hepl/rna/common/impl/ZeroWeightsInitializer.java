package be.hepl.rna.common.impl;

import be.hepl.rna.common.IWeightsInitializer;

public class ZeroWeightsInitializer implements IWeightsInitializer {

	@Override
	public double getWeight(int row, int col) {
		return 0;
	}

}
