package be.hepl.rna.common;

/**
 * 
 * @author Pol
 *
 * @param <D2> The weights table representation
 */
public interface ILayer<D2> {
	double getLearningRate();
	D2 getWeights();
	String getActivationFunctionName();
	int getInputSize();
	int getOutputSize();
}
