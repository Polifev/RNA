package be.hepl.rna.api.impl.matrix;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import be.hepl.rna.api.ILabeledSample;
import be.hepl.rna.api.INeuralNetwork;
import be.hepl.rna.api.ITrainingMode;
import be.hepl.rna.api.impl.CommonLabeledSample;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

@SuppressWarnings("unchecked")
public class TestMatrixNeuralNetwork {
	private List<ILabeledSample> samples;
	private ITrainingMode<DoubleMatrix1D, DoubleMatrix2D> mockTrainingMode;
	
	@Before
	public void setup() {
		samples = new ArrayList<>();
		samples.add(new CommonLabeledSample(new double[] {0, 0}, new double[] {0}));
		samples.add(new CommonLabeledSample(new double[] {0, 1}, new double[] {1}));
		samples.add(new CommonLabeledSample(new double[] {1, 0}, new double[] {1}));
		samples.add(new CommonLabeledSample(new double[] {1, 1}, new double[] {1}));
		
		mockTrainingMode = mock(ITrainingMode.class);	
	}
	
	@Test
	public void canDoFullBatchTraining() throws OperationNotSupportedException {
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network = new MatrixNeuralNetwork();
		network.setTrainingParameters(4, mockTrainingMode);		
		network.train(10, samples);
		verify(mockTrainingMode, times(10)).updateWeights(any(), any());
	}
	
	@Test
	public void canDoStochasticTraining() throws OperationNotSupportedException {
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network = new MatrixNeuralNetwork();
		network.setTrainingParameters(1, mockTrainingMode);		
		network.train(10, samples);
		verify(mockTrainingMode, times(40)).updateWeights(any(), any());
	}
	
	@Test
	public void canDoSmallBatchTraining() throws OperationNotSupportedException {
		INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> network = new MatrixNeuralNetwork();
		network.setTrainingParameters(2, mockTrainingMode);		
		network.train(10, samples);
		verify(mockTrainingMode, times(20)).updateWeights(any(), any());
	}
}
