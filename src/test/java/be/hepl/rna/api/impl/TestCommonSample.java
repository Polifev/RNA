package be.hepl.rna.api.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import be.hepl.rna.api.ISample;

public class TestCommonSample {

	private double[] inputs;

	@Before
	public void setup() {
		inputs = new double[] { 0, 0 };
	}

	@Test
	public void knowsItsInput() {
		// Given
		ISample s = new CommonSample(inputs);

		// Then
		assertEquals(inputs, s.inputs());
	}

}
