package be.hepl.rna.api.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.hepl.rna.api.IBatchEvaluation;
import be.hepl.rna.api.ISampleEvaluation;
import be.hepl.rna.api.impl.matrix.AccuracyMetric;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;

@SuppressWarnings("unchecked")
public class TestAccuracyMetric {
	private static final double EPSILON = 0.00_000_1;
	private IBatchEvaluation<DoubleMatrix1D> iterationEvaluation;

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
		assertEquals(0.0, new AccuracyMetric(0.0).compute(iterationEvaluation), EPSILON);
	}

	@Test
	public void testAccuracyWithLittleTolerance() {
		assertEquals(0.5, new AccuracyMetric(0.2).compute(iterationEvaluation), EPSILON);
	}

	@Test
	public void testAccuracyWithHighTolerance() {
		assertEquals(0.75, new AccuracyMetric(0.5).compute(iterationEvaluation), EPSILON);
	}

	private IBatchEvaluation<DoubleMatrix1D> mockIteration(List<ISampleEvaluation<DoubleMatrix1D>> samples) {
		IBatchEvaluation<DoubleMatrix1D> iterationEvaluation = mock(IBatchEvaluation.class);
		when(iterationEvaluation.getSampleEvaluations()).thenReturn(samples);
		return iterationEvaluation;
	}

	private ISampleEvaluation<DoubleMatrix1D> mockSample(double x1, double x2, double y, double expected) {
		ISampleEvaluation<DoubleMatrix1D> sample = mock(ISampleEvaluation.class);
		when(sample.getGivenInput()).thenReturn(DoubleFactory1D.dense.make(new double[] { x1, x2 }));
		when(sample.getExpectedOutput()).thenReturn(DoubleFactory1D.dense.make(new double[] { expected }));
		when(sample.getLayerOutputs())
				.thenReturn(new DoubleMatrix1D[] { DoubleFactory1D.dense.make(new double[] { y }), });
		return sample;
	}
}
