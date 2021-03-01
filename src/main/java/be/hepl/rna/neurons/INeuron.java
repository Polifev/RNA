package be.hepl.rna.neurons;

public interface INeuron<T, U> {
	int inputSize();
	U evaluate(ISample<T> sample);
	void train(Iterable<ILabelledSample<T, U>> trainingSet);
}
