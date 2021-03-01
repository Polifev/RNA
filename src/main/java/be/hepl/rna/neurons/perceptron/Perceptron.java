package be.hepl.rna.neurons.perceptron;

import be.hepl.rna.neurons.ILabelledSample;
import be.hepl.rna.neurons.INeuron;
import be.hepl.rna.neurons.ISample;

public class Perceptron implements INeuron<Double, Byte>{

	private double w0 = 0;
	private double[] weights;
	private double learningRate;
	
	public Perceptron(int inputSize, double learningRate) {
		this.weights = new double[inputSize];
		this.learningRate = learningRate;
	}
	
	@Override
	public int inputSize() {
		return weights.length;
	}

	@Override
	public Byte evaluate(ISample<Double> sample) {
		if(sample.dimension() != inputSize()) {
			throw new IllegalArgumentException("Invalid sample size. Expected: " + inputSize() + ", actual: " + sample.dimension());
		}
		double total = w0;
		for(int i = 0; i < inputSize(); i++) {
			total += sample.elementAt(i) * weights[i];
		}
		return (byte)(total > 0 ? 1 : 0);
	}

	@Override
	public void train(Iterable<ILabelledSample<Double, Byte>> trainingSet) {
		int nErr = 0;
		for(ILabelledSample<Double, Byte> sample : trainingSet) {
			byte actualOutput = evaluate(sample);
			byte expectedOutput = sample.expectedOutput();
			if(actualOutput != expectedOutput) {
				nErr++;
				double error = expectedOutput - actualOutput;
				w0 += this.learningRate * error;
				for(int i = 0; i < inputSize(); i++) {
					weights[i] += this.learningRate * error * sample.elementAt(i);
				}
			}
		}
	}
}
