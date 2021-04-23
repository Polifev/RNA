package be.hepl.rna.api.impl;

import java.util.Iterator;
import java.util.stream.DoubleStream;

public class Sweep implements Iterable<Double> {
	private final double start, end, step;

	public static Iterable<Double> sweep(double start, double end, double step) {
		return new Sweep(start, end, step);
	}

	private Sweep(double start, double end, double step) {
		this.start = start;
		this.end = end;
		this.step = step;
	}

	@Override
	public Iterator<Double> iterator() {
		long numberOfValues = (long) ((end - start) / step);
		return DoubleStream.iterate(this.start, d -> d += this.step).limit(numberOfValues).iterator();
	}

}
