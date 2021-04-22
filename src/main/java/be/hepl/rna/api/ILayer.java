package be.hepl.rna.api;

/**
 * 
 * @author Pol
 *
 * @param <D2> A 2D strucutre representing the weights
 */
public interface ILayer<D2> {
	double getLearningRate();
	ILayer<D2> initWeights(IWeightsInitializer initializer);
	D2 getWeights();
	String getActivationFunctionName();
	int getInputSize();
	int getOutputSize();
}
