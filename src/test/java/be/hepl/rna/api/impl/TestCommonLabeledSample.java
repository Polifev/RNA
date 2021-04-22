package be.hepl.rna.api.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import be.hepl.rna.api.ILabeledSample;

public class TestCommonLabeledSample {

	private double[] inputs;
	private double[] expectedOutput;

	@Before
	public void setup() {
		inputs = new double[] { 0, 0 };
		expectedOutput = new double[] { 0 };
	}

	@Test
	public void test() {
		// Given
		ILabeledSample s = new CommonLabeledSample(inputs, expectedOutput);

		// Then
		assertEquals(inputs, s.inputs());
		assertEquals(expectedOutput, s.expectedOutput());
	}

}
