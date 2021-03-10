package be.hepl.rna.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import be.hepl.rna.LabeledSample;
import be.hepl.rna.Sample;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.linalg.Algebra;

public class MatrixNeuralNetwork {
	// Training data
	private List<DoubleMatrix1D> trainingInput = new ArrayList<>();
	private List<DoubleMatrix1D> trainingOutput = new ArrayList<>();

	// Layers (ordered in two ways)
	private List<Layer> layers = new LinkedList<>();
	private List<Layer> reverseLayers = new LinkedList<>();

	// Callbacks
	private Consumer<Iteration> iterationProcessedCallback = i -> {};
	private Consumer<SampleEvaluation> sampleProcessedCallback = s -> {};
	private Predicate<Iteration> earlyStoppingCondition = i -> false;
	

	public void addLayer(Layer layer) {
		layers.add(layer);
		reverseLayers.add(0, layer);
	}

	public void prepareTraining(Iterable<LabeledSample> trainingSamples) {
		trainingInput.clear();
		trainingOutput.clear();
		DoubleMatrix1D imaginaryInput = new DenseDoubleMatrix1D(new double[] { 1.0 });
		for (LabeledSample sample : trainingSamples) {
			trainingInput
					.add(DoubleFactory1D.dense.append(imaginaryInput, DoubleFactory1D.dense.make(sample.inputs())));
			trainingOutput.add(DoubleFactory1D.dense.make(sample.expectedOutput()));
		}
	}

	public void trainPerceptron(int iterationCount) {
		boolean earlyStopped = false;
		
		// For each iterations
		for (int i = 0; i < iterationCount && !earlyStopped; i++) {
			Iteration iteration = new Iteration(i);
			
			// For each sample
			for (int sampleIndex = 0; sampleIndex < trainingInput.size(); sampleIndex++) {
				SampleEvaluation sampleEvaluation = new SampleEvaluation();
				
				DoubleMatrix1D givenInput = trainingInput.get(sampleIndex);
				DoubleMatrix1D expectedOutput = trainingOutput.get(sampleIndex);
				DoubleMatrix1D[] layerPotentials = new DoubleMatrix1D[1 + this.layers.size()];
				DoubleMatrix1D[] layerOutputs = new DoubleMatrix1D[1 + this.layers.size()];
				
				layerPotentials[0] = givenInput;
				layerOutputs[0] = givenInput;

				// 1. >> Propagate forward >>
				int previousIndex = 0;
				for (Layer layer : this.layers) {
					int currentIndex = previousIndex + 1; 
					layerPotentials[currentIndex] = Algebra.DEFAULT.mult(layer.getWeights(), layerOutputs[previousIndex]);
					layerOutputs[currentIndex] =  layerPotentials[currentIndex].assign(layer.getActivationFunction());
					previousIndex = currentIndex;
				}
				sampleEvaluation.setGivenInput(givenInput);
				sampleEvaluation.setExpectedOutput(expectedOutput);
				sampleEvaluation.setLayerPotentials(layerPotentials);
				sampleEvaluation.setLayerOutputs(layerOutputs);
				
				// 2. << Back Propagate error <<
				if (layers.size() > 1) {
					// TODO backpropagation
				} else {
					// TODO normale propagation
				}
				
				sampleProcessedCallback.accept(sampleEvaluation);
			}
			
			iterationProcessedCallback.accept(iteration);
			earlyStopped = earlyStoppingCondition.test(iteration);
		}
	}

	public SampleEvaluation evaluate(Sample sample) {
		SampleEvaluation sampleEvaluation = new SampleEvaluation();
		
		DoubleMatrix1D givenInput = DoubleFactory1D.dense.append(DoubleFactory1D.dense.make(new double[] {1.0}), DoubleFactory1D.dense.make(sample.inputs()));
		DoubleMatrix1D[] layerPotentials = new DoubleMatrix1D[1 + this.layers.size()];
		DoubleMatrix1D[] layerOutputs = new DoubleMatrix1D[1 + this.layers.size()];
		
		layerPotentials[0] = givenInput;
		layerOutputs[0] = givenInput;

		// 1. >> Propagate forward >>
		int previousIndex = 0;
		for (Layer layer : this.layers) {
			int currentIndex = previousIndex + 1; 
			layerPotentials[currentIndex] = Algebra.DEFAULT.mult(layer.getWeights(), layerOutputs[previousIndex]);
			layerOutputs[currentIndex] =  layerPotentials[currentIndex].assign(layer.getActivationFunction());
			previousIndex = currentIndex;
		}
		sampleEvaluation.setGivenInput(givenInput);
		sampleEvaluation.setLayerPotentials(layerPotentials);
		sampleEvaluation.setLayerOutputs(layerOutputs);
		
		return sampleEvaluation;
	}

	public MatrixNeuralNetwork onIterationEnds(Consumer<Iteration> callback) {
		this.iterationProcessedCallback = callback;
		return this;
	}
	
	public MatrixNeuralNetwork onSampleProcessed(Consumer<SampleEvaluation> callback) {
		this.sampleProcessedCallback = callback;
		return this;
	}
	
	public MatrixNeuralNetwork setEarlyStoppignCondition(Predicate<Iteration> condition) {
		this.earlyStoppingCondition = condition;
		return this;
	}
}
