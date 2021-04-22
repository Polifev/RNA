package be.hepl.rna.api.impl.matrix;

import be.hepl.rna.api.ILinearModel;
import be.hepl.rna.api.INeuralNetwork;
import be.hepl.rna.api.ISample;
import be.hepl.rna.api.ISampleEvaluation;
import be.hepl.rna.api.impl.CommonSample;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class MatrixModelWrapper implements ILinearModel {

	private INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> neuralNetwork;
	
	public MatrixModelWrapper(INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D> neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}
	
	@Override
	public double predict(double input) {
		ISample s = new CommonSample(input);
		ISampleEvaluation<DoubleMatrix1D> result = neuralNetwork.evaluate(s);
		return result.getLayerOutputs()[result.getLayerOutputs().length - 1].get(0);
	}

}