package be.hepl.rna.api;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.naming.OperationNotSupportedException;

/**
 * 
 * @author Pol
 *
 * @param <D1> The one-dimension container representation (typically: vector)
 * @param <D2> the two-dimensions container representation  (typically: matrix)
 */
public interface INeuralNetwork<D1, D2> {
	void addLayer(ILayer<D2> layer);
	void prepareTraining(Iterable<ILabeledSample> trainingSamples);
	void train(int iterationCount) throws OperationNotSupportedException;
	ISampleEvaluation<D1> evaluate(ISample sample);
	void onSampleProcessed(Consumer<ISampleEvaluation<D1>> callback);
	void onIterationStarts(Consumer<Integer> callback);
	void onIterationEnds(Consumer<IIterationEvaluation<D1>> callback);
	void setEarlyStoppingCondition(Predicate<IIterationEvaluation<D1>> condition);
	String generateReport();
}
