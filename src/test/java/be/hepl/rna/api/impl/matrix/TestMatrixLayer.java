package be.hepl.rna.api.impl.matrix;

import static org.mockito.ArgumentMatchers.any;
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
	
	@Test
	public void canInitWeights() {
		IWeightsInitializer mockInitializer = mock(IWeightsInitializer.class);		
		when(mockInitializer.getWeight(anyInt(), anyInt())).thenReturn(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
		
		ILayer<DoubleMatrix2D> layer = new MatrixLayer(0.2, 2, 3, "sigmoid");
		layer.initWeights(mockInitializer);
		
		verify(mockInitializer, times(9)).getWeight(anyInt(), anyInt());
	}

}
