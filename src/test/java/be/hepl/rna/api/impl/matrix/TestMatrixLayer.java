package be.hepl.rna.api.impl.matrix;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Test;

import be.hepl.rna.api.ILayer;
import be.hepl.rna.api.IWeightsInitializer;
import cern.colt.matrix.DoubleMatrix2D;

public class TestMatrixLayer {
	
	private static final double ZERO = 0.0;
	
	@Test
	public void initWeightsCorrectly() {
		IWeightsInitializer mockInitializer = mock(IWeightsInitializer.class);		
		when(mockInitializer.getWeight(anyInt(), anyInt())).thenReturn(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
		
		ILayer<DoubleMatrix2D> layer = new MatrixLayer(0.2, 2, 3, "sigmoid");
		layer.initWeights(mockInitializer);
		
		verify(mockInitializer, times(9)).getWeight(anyInt(), anyInt());
		assertEquals(1, layer.getWeights().getQuick(0, 0), ZERO);
		assertEquals(2, layer.getWeights().getQuick(0, 1), ZERO);
		assertEquals(3, layer.getWeights().getQuick(0, 2), ZERO);
		assertEquals(4, layer.getWeights().getQuick(1, 0), ZERO);
		assertEquals(5, layer.getWeights().getQuick(1, 1), ZERO);
		assertEquals(6, layer.getWeights().getQuick(1, 2), ZERO);
		assertEquals(7, layer.getWeights().getQuick(2, 0), ZERO);
		assertEquals(8, layer.getWeights().getQuick(2, 1), ZERO);
		assertEquals(9, layer.getWeights().getQuick(2, 2), ZERO);
	}

	@Test
	public void holdItsData() {
		ILayer<DoubleMatrix2D> layer = new MatrixLayer(0.2, 2, 3, "sigmoid");
		assertEquals("sigmoid", layer.getActivationFunctionName());
		assertEquals(0.2, layer.getLearningRate(), ZERO);
		assertEquals(2 + 1, layer.getInputSize());
		assertEquals(3, layer.getOutputSize());
	}
}
