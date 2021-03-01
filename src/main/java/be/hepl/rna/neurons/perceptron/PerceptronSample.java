package be.hepl.rna.neurons.perceptron;

import be.hepl.rna.neurons.ILabelledSample;

public class PerceptronSample extends DoubleSample implements ILabelledSample<Double, Byte> {
	
	private byte expectedOutput; 
	
	public PerceptronSample(byte expectedOutput, double...inputs) {
		super(inputs);
		this.expectedOutput = expectedOutput;
	}
	
	@Override
	public Byte expectedOutput() {
		return this.expectedOutput;
	}

}
