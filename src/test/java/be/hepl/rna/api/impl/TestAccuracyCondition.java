package be.hepl.rna.api.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.hepl.rna.api.IIterationEvaluation;
import be.hepl.rna.api.ISampleEvaluation;
import be.hepl.rna.api.impl.matrix.AccuracyCondition;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;

public class TestAccuracyCondition {

	private IIterationEvaluation<DoubleMatrix1D> iterationEvaluation;

	@Before
	public void setup() {
		List<ISampleEvaluation<DoubleMatrix1D>> samples = new ArrayList<ISampleEvaluation<DoubleMatrix1D>>();
		samples.add(mockSample(0, 0, 0, 0.2));
		samples.add(mockSample(0, 1, 1, 0.4));
		samples.add(mockSample(1, 0, 1, 0.6));
		samples.add(mockSample(1, 1, 1, 0.8));
		iterationEvaluation = mockIteration(samples);
	}

	@Test
	public void testAccuracyWithNoTolerance() {
		assertFalse(new AccuracyCondition(1.0, 0.0).test(iterationEvaluation));
		assertFalse(new AccuracyCondition(0.25, 0.0).test(iterationEvaluation));
		assertTrue(new AccuracyCondition(0.0, 0.0).test(iterationEvaluation));
	}

	@Test
	public void testAccuracyWithLittleTolerance() {
		assertFalse(new AccuracyCondition(1.0, 0.2).test(iterationEvaluation));
		assertFalse(new AccuracyCondition(0.75, 0.2).test(iterationEvaluation));
		assertTrue(new AccuracyCondition(0.5, 0.2).test(iterationEvaluation));
		assertTrue(new AccuracyCondition(0.25, 0.2).test(iterationEvaluation));
	}

	@Test
	public void testAccuracyWithHighTolerance() {
		assertFalse(new AccuracyCondition(1.0, 0.5).test(iterationEvaluation));
		assertTrue(new AccuracyCondition(0.75, 0.5).test(iterationEvaluation));
		assertTrue(new AccuracyCondition(0.5, 0.5).test(iterationEvaluation));
		assertTrue(new AccuracyCondition(0.25, 0.5).test(iterationEvaluation));
	}

	@SuppressWarnings("unchecked")
	private IIterationEvaluation<DoubleMatrix1D> mockIteration(List<ISampleEvaluation<DoubleMatrix1D>> samples) {
		IIterationEvaluation<DoubleMatrix1D> iterationEvaluation = mock(IIterationEvaluation.class);
		when(iterationEvaluation.getIterationNumber()).thenReturn(0);
		when(iterationEvaluation.getSampleEvaluations()).thenReturn(samples);
		return iterationEvaluation;
	}

	@SuppressWarnings("unchecked")
	private ISampleEvaluation<DoubleMatrix1D> mockSample(double x1, double x2, double y, double expected) {
		ISampleEvaluation<DoubleMatrix1D> sample = mock(ISampleEvaluation.class);
		when(sample.getGivenInput()).thenReturn(DoubleFactory1D.dense.make(new double[] { x1, x2 }));
		when(sample.getExpectedOutput()).thenReturn(DoubleFactory1D.dense.make(new double[] { expected }));
		when(sample.getLayerOutputs())
				.thenReturn(new DoubleMatrix1D[] { DoubleFactory1D.dense.make(new double[] { y }), });
		return sample;
	}
}
