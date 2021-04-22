package be.hepl.rna.api;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.naming.OperationNotSupportedException;

import be.hepl.rna.api.impl.matrix.AccuracyMetric;

/**
 * 
 * @author Pol
 *
 * @param <D1> The one-dimension container representation (typically: vector)
 * @param <D2> the two-dimensions container representation  (typically: matrix)
 */
public interface INeuralNetwork<D1, D2> {
	void addLayer(ILayer<D2> layer);
	void train(int iterationCount, Iterable<ILabeledSample> trainingSamples) throws OperationNotSupportedException;
	ISampleEvaluation<D1> evaluate(ISample sample);
	void registerMetric(String string, AccuracyMetric accuracyMetric);
	void onSampleProcessed(Consumer<ISampleEvaluation<D1>> callback);
	void onIterationStarts(Consumer<Integer> callback);
	void onIterationEnds(Consumer<IIterationEvaluation<D1>> callback);
	void stopWhen(String metricName, Predicate<Double> condition);
	String generateReport();
	
}
