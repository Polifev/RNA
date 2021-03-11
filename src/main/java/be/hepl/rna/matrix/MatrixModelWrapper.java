package be.hepl.rna.matrix;

import be.hepl.rna.common.ILinearModel;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.impl.CommonSample;

public class MatrixModelWrapper implements ILinearModel {

	private MatrixNeuralNetwork neuralNetwork;
	
	public MatrixModelWrapper(MatrixNeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}
	
	@Override
	public double predict(double input) {
		ISample s = new CommonSample(input);
		MatrixSampleEvaluation result = neuralNetwork.evaluate(s);
		return result.getLayerOutputs()[result.getLayerOutputs().length - 1].get(0);
	}

}