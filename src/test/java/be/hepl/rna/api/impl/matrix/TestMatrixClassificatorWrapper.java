package be.hepl.rna.api.impl.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import be.hepl.rna.api.IClassificator;
import be.hepl.rna.api.INeuralNetwork;
import be.hepl.rna.api.ISampleEvaluation;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class TestMatrixClassificatorWrapper {

	@SuppressWarnings("unchecked")
	@Test
	public void returnsMinusOneIfNoClassIsAboveThreshold() {
		ISampleEvaluation<DoubleMatrix1D> fakeEvaluation = mock(ISampleEvaluation.class);
		when(fakeEvaluation.getLayerOutputs()).thenReturn(new DoubleMatrix1D[] {
			DoubleFactory1D.dense.make(new double[] {0.0, 0.0}), // input
			DoubleFactory1D.dense.make(new double[] {0.0, 0.1, 0.0}) // fake output
		});
				
		
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network = (INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D>) mock(INeuralNetwork.class);
		when(network.evaluate(any())).thenReturn(fakeEvaluation);
		
		IClassificator classificator = new MatrixClassificatorWrapper(network, 0.2);
		assertEquals(-1, classificator.classify(new double[] {0.0, 0.0}));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMaxClassThatIsAboveThreshold() {
		ISampleEvaluation<DoubleMatrix1D> fakeEvaluation = mock(ISampleEvaluation.class);
		when(fakeEvaluation.getLayerOutputs()).thenReturn(new DoubleMatrix1D[] {
			DoubleFactory1D.dense.make(new double[] {0.0, 0.0}), // input
			DoubleFactory1D.dense.make(new double[] {0.4, 0.5, 0.3}) // fake output
		});
				
		
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network = (INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D>) mock(INeuralNetwork.class);
		when(network.evaluate(any())).thenReturn(fakeEvaluation);
		
		IClassificator classificator = new MatrixClassificatorWrapper(network, 0.2);
		assertEquals(1, classificator.classify(new double[] {0.0, 0.0}));
	}

}
