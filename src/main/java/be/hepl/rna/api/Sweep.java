package be.hepl.rna.api;

import java.util.stream.DoubleStream;

public class Sweep {
	private final double start, end, step;
	
	public Sweep(double start, double end, double step) {
		this.start = start;
		this.end = end;
		this.step = step;
	}
	
	public DoubleStream getValues() {
		long numberOfValues = (long) ((end - start) / step);
		return DoubleStream.iterate(this.start, d -> d+= this.step).limit(numberOfValues);
	}

}
