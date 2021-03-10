package be.hepl.rna.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import be.hepl.rna.IterationResult;
import be.hepl.rna.LabeledSample;
import be.hepl.rna.Metrics;
import be.hepl.rna.Sample;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

public class MatrixNeuralNetwork {	
	// Training data
	private List<DoubleMatrix1D> trainingInput = new ArrayList<>();
	private List<DoubleMatrix1D> trainingOutput = new ArrayList<>();
	
	// Layers (ordered in two ways)
	private List<AbstractMatrixNeuralLayer> layers = new LinkedList<>();
	private List<AbstractMatrixNeuralLayer> reverseLayers = new LinkedList<>();
	
	private Consumer<IterationResult> iterationEndsCallback;
	
	public void addLayer(AbstractMatrixNeuralLayer layer) {
		layers.add(layer);
		reverseLayers.add(0, layer);
	}
	
	public void prepareTraining(Iterable<LabeledSample> trainingSamples) {
		trainingInput.clear();
		trainingOutput.clear();
		DoubleMatrix1D imaginaryInput = new DenseDoubleMatrix1D(new double[] {1.0});
		for(LabeledSample sample : trainingSamples) {
			trainingInput.add( DoubleFactory1D.dense.append(imaginaryInput, DoubleFactory1D.dense.make(sample.inputs())) );
			trainingOutput.add( DoubleFactory1D.dense.make(sample.expectedOutput()) );
		}
	}
	
	public void trainPerceptron(int iterationCount, Predicate<IterationResult> earlyStoppingCondition) {
		boolean earlyStopped = false;
		
		// For each iterations
		for(int iteration = 0; iteration < iterationCount && !earlyStopped; iteration++) {	
			IterationResult result = new IterationResult(iteration);
			
			DoubleMatrix1D iterationError = DoubleFactory1D.dense.make(this.layers.get(0).getWeights().rows());
			
			// For each sample
			for(int sampleIndex = 0; sampleIndex < trainingInput.size(); sampleIndex++) {
				DoubleMatrix1D input = trainingInput.get(sampleIndex);
				DoubleMatrix1D expectedOutput = trainingOutput.get(sampleIndex);
				DoubleMatrix1D[] results = new DoubleMatrix1D[1 + this.layers.size()];
				results[0] = input;
				
				// 1. >> Propagate forward >>
				int i = 0;
				for(AbstractMatrixNeuralLayer layer : this.layers) {
					results[i+1] = layer.compute(results[i]);
					i++;
				}
				
				DoubleMatrix1D sampleError = expectedOutput.copy().assign(results[results.length - 1], CommonFunctions.SUBSTRACTION);
				iterationError = iterationError.assign(sampleError.copy().assign(CommonFunctions.ABS), CommonFunctions.ADDITION);
				
				if(layers.size() > 1) {
					// TODO backpropagation
				} else {
					layers.get(0).correctWeights(results[results.length - 2], sampleError);
				}
			}
			
			result.setMetric(Metrics.ERROR_COUNT, ((int)iterationError.aggregate(CommonFunctions.ADDITION, a -> a)));
			
			if(iterationEndsCallback != null) {
				iterationEndsCallback.accept(result);
			}
			
			if(earlyStoppingCondition != null) {
				earlyStopped = earlyStoppingCondition.test(result);
			}
		}
	}

	public double[] evaluate(Sample sample) {
		DoubleMatrix1D input = DoubleFactory1D.dense.append(new DenseDoubleMatrix1D(new double[] {1.0}), DoubleFactory1D.dense.make(sample.inputs()));		
		DoubleMatrix1D[] results = new DoubleMatrix1D[1 + this.layers.size()];
		results[0] = input;
		
		// 1. >> Propagate forward >>
		int i = 0;
		for(AbstractMatrixNeuralLayer layer : this.layers) {
			results[i+1] = layer.compute(results[i]);
			i++;
		}
		return results[results.length - 1].toArray();
	}
	
	public MatrixNeuralNetwork onIterationEnds(Consumer<IterationResult> callback) {
		this.iterationEndsCallback = callback;
		return this;
	}
}
