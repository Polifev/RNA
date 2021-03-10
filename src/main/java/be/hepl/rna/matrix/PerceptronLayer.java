package be.hepl.rna.matrix;

import be.hepl.rna.ActivationFunctions;

public class PerceptronLayer extends AbstractMatrixNeuralLayer{
	
	public PerceptronLayer(int input, int neuronsCount, double learningRate){
		super(input, neuronsCount, ActivationFunctions.THRESHOLD, learningRate);
	}

}
