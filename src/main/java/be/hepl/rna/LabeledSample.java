package be.hepl.rna;

public class LabeledSample extends Sample{
	private double[] expectedOutputs;
	
	public LabeledSample(double[] sample, double[] expectedOutputs) {
		super(sample);
		this.expectedOutputs = expectedOutputs;
	}
	
	public double[] expectedOutput() {
		return expectedOutputs;
	}
}
