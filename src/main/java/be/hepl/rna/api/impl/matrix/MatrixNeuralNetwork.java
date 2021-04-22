package be.hepl.rna.api.impl.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.naming.OperationNotSupportedException;

import be.hepl.rna.api.IIterationEvaluation;
import be.hepl.rna.api.ILabeledSample;
import be.hepl.rna.api.ILayer;
import be.hepl.rna.api.INeuralNetwork;
import be.hepl.rna.api.ISample;
import be.hepl.rna.api.ISampleEvaluation;
import be.hepl.rna.api.ITrainingMode;
import be.hepl.rna.api.impl.ActivationFunctions;
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
	public void train(int iterationCount) throws OperationNotSupportedException {
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
				iteration.addTrainingResult(sampleEvaluation);
				// Call the sample based training
				trainingMode.sampleBasedWeightsCorrection(sampleEvaluation, this.layers);
				sampleProcessedCallback.accept(sampleEvaluation);
			}
			
			// Call the iteration based training
			trainingMode.iterationBasedWeightsCorrection(iteration, layers);
			
			// Try earlyStoppingCondition
			earlyStopped = earlyStoppingCondition.test(iteration);
			
			// Call the iteration end callback
			iterationEndsCallback.accept(iteration);
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
	
	@Override
	public String generateReport() {
		return "";
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
			layerOutputs[currentIndex] =  layerPotentials[currentIndex].copy().assign((val) -> ActivationFunctions.get(layer.getActivationFunctionName()).apply(val));
			
			// Add imaginary input
			if(currentIndex < this.layers.size() ) {
				//layerPotentials[currentIndex] = expandWithImaginary(layerPotentials[currentIndex]);
				layerOutputs[currentIndex] = expandWithImaginary(layerOutputs[currentIndex]);
			}
			
			
			previousIndex = currentIndex;
		}
		sampleEvaluation.setGivenInput(input);
		sampleEvaluation.setLayerPotentials(layerPotentials);
		sampleEvaluation.setLayerOutput(layerOutputs);
		
		return sampleEvaluation;
	}
	
	private DoubleMatrix1D buildInputMatrix(double[] inputArr) {
		return expandWithImaginary(DoubleFactory1D.dense.make(inputArr));
	}
	
	private DoubleMatrix1D expandWithImaginary(DoubleMatrix1D matrix) {
		return DoubleFactory1D.dense.append(IMAGINARY_INPUT, matrix);
	}
}