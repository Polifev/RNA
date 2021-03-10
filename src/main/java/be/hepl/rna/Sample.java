package be.hepl.rna;

public class Sample {
	private double[] sample;
	
	public Sample(double...sample) {
		this.sample = sample;
	}
	
	public double[] inputs() {
		return sample;
	}
}
