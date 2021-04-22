package be.hepl.rna.api;

import java.util.function.Predicate;

/**
 * 
 * @author Pol
 *
 * @param <D1> The one-dimension container representation (typically: vector)
 * @param <D2> the two-dimensions container representation  (typically: matrix)
 */
public interface INeuralNetwork<D1, D2> {
	void addLayer(ILayer<D2> layer);
	void setTrainingParameters(int batchSize, ITrainingMode<D1, D2> trainingMode);
	void registerMetric(String string, IMetric<D1> accuracyMetric);
	void stopWhen(String metricName, Predicate<Double> condition);
	
	void train(int iterationCount, Iterable<ILabeledSample> trainingSamples);
	ISampleEvaluation<D1> evaluate(ISample sample);
}
