package be.hepl.rna.api;

/**
 * 
 * @author Pol
 *
 * @param <D1> The representation of vectors
 */
public interface ISampleEvaluation<D1> {
	public D1 getGivenInput();
	public D1 getExpectedOutput();
	public D1[] getLayerPotentials();
	public D1[] getLayerOutputs();
	public void setGivenInput(D1 givenInput);
	public void setExpectedOutput(D1 expectedOutput);
	public void setLayerPotentials(D1[] layerPotentials);
	public void setLayerOutput(D1[] layerOutputs);
}
