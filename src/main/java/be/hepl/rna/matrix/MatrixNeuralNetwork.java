package be.hepl.rna.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import be.hepl.rna.common.IIterationEvaluation;
import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.ILayer;
import be.hepl.rna.common.INeuralNetwork;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.ISampleEvaluation;
import be.hepl.rna.common.ITrainingMode;
import be.hepl.rna.common.impl.ActivationFunctions;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

public class MatrixNeuralNetwork implements INeuralNetwork<DoubleMatrix1D, DoubleMatrix2D>{
	private static final DoubleMatrix1D IMAGINARY_INPUT = DoubleFactory1D.dense.make(new double[] {1.0});
	
	// Training data
	private List<DoubleMatrix1D> trainingInput = new ArrayList<>();
	private List<DoubleMatrix1D> trainingOutput = new ArrayList<>();

	// Layers (ordered in two ways)
	private List<ILayer<DoubleMatrix2D>> layers = new LinkedList<>();
	private List<ILayer<DoubleMatrix2D>> reverseLayers = new LinkedList<>();

	// Training mode
	private ITrainingMode<DoubleMatrix1D, DoubleMatrix2D> trainingMode;
	
	// Callbacks
	private Consumer<ISampleEvaluation<DoubleMatrix1D>> sampleProcessedCallback = sampleEvaluation -> {};
	private Consumer<Integer> iterationStartsCallback = i -> {};
	private Consumer<IIterationEvaluation<DoubleMatrix1D>> iterationEndsCallback = iterationEvaluation -> {};
	private Predicate<IIterationEvaluation<DoubleMatrix1D>> earlyStoppingCondition = iterationEvaluation -> false;
	
	public MatrixNeuralNetwork(ITrainingMode<DoubleMatrix1D, DoubleMatrix2D> trainingMode) {
		this.trainingMode = trainingMode;
	}
	
	@Override
	public void addLayer(ILayer<DoubleMatrix2D> layer) {
		layers.add(layer);
		reverseLayers.add(0, layer);
	}

	@Override
	public void prepareTraining(Iterable<ILabeledSample> trainingSamples) {
		trainingInput.clear();
		trainingOutput.clear();
		for (ILabeledSample sample : trainingSamples) {
			trainingInput.add(buildInputMatrix(sample.inputs()));
			trainingOutput.add(DoubleFactory1D.dense.make(sample.expectedOutput()));
		}
	}

	@Override
	public void train(int iterationCount) {
		boolean earlyStopped = false;
		
		// For each iterations
		for (int i = 0; i < iterationCount && !earlyStopped; i++) {
			iterationStartsCallback.accept(i);
			MatrixIterationEvaluation iteration = new MatrixIterationEvaluation(i);
			
			// For each sample
			for (int sampleIndex = 0; sampleIndex < trainingInput.size(); sampleIndex++) {
				// Evaluate sample labeling
				MatrixSampleEvaluation sampleEvaluation = evaluate(trainingInput.get(sampleIndex));
				sampleEvaluation.setExpectedOutput(trainingOutput.get(sampleIndex));
				
				// Call the sample based training
				trainingMode.sampleBasedWeightsCorrection(sampleEvaluation, this.layers);
				sampleProcessedCallback.accept(sampleEvaluation);
			}
			
			// Call the iteration based training
			trainingMode.iterationBasedWeightsCorrection(iteration, layers);
			iterationEndsCallback.accept(iteration);
			earlyStopped = earlyStoppingCondition.test(iteration);
		}
	}
	
	@Override
	public MatrixSampleEvaluation evaluate(ISample sample) {
		return evaluate(buildInputMatrix(sample.inputs()));
	}
	
	@Override
	public void onSampleProcessed(Consumer<ISampleEvaluation<DoubleMatrix1D>> callback) {
		this.sampleProcessedCallback = callback;
	}
	
	@Override
	public void onIterationStarts(Consumer<Integer> callback) {
		this.iterationStartsCallback = callback;
	}
	
	@Override
	public void onIterationEnds(Consumer<IIterationEvaluation<DoubleMatrix1D>> callback) {
		this.iterationEndsCallback = callback;
	}
	
	@Override
	public void setEarlyStoppingCondition(Predicate<IIterationEvaluation<DoubleMatrix1D>> condition) {
		this.earlyStoppingCondition = condition;
	}
	
	private MatrixSampleEvaluation evaluate(DoubleMatrix1D input) {
		MatrixSampleEvaluation sampleEvaluation = new MatrixSampleEvaluation();
		
		
		DoubleMatrix1D[] layerPotentials = new DoubleMatrix1D[1 + this.layers.size()];
		DoubleMatrix1D[] layerOutputs = new DoubleMatrix1D[1 + this.layers.size()];
		
		layerPotentials[0] = input;
		layerOutputs[0] = input;

		// 1. >> Propagate forward >>
		int previousIndex = 0;
		for (ILayer<DoubleMatrix2D> layer : this.layers) {
			int currentIndex = previousIndex + 1; 
			layerPotentials[currentIndex] = Algebra.DEFAULT.mult(layer.getWeights(), layerOutputs[previousIndex]);
			layerOutputs[currentIndex] =  layerPotentials[currentIndex].assign((val) -> ActivationFunctions.get(layer.getActivationFunctionName()).apply(val));
			previousIndex = currentIndex;
		}
		sampleEvaluation.setGivenInput(input);
		sampleEvaluation.setLayerPotentials(layerPotentials);
		sampleEvaluation.setLayerOutputs(layerOutputs);
		
		return sampleEvaluation;
	}
	
	private DoubleMatrix1D buildInputMatrix(double[] inputArr) {
		return DoubleFactory1D.dense.append(IMAGINARY_INPUT, DoubleFactory1D.dense.make(inputArr));
	}
}
