package be.hepl.rna.api.impl;

import java.util.function.Predicate;

public class Predicates {
	
	public static Predicate<Double> greaterThan(double threshold) {
		return (v) -> v > threshold;
	}
	
	public static Predicate<Double> greaterOrEqualTo(double threshold) {
		return (v) -> v >= threshold;
	}

	public static Predicate<Double> lessThan(double threshold) {
		return (v) -> v < threshold;
	}

	public static Predicate<Double> lessOrEqualTo(double threshold) {
		return (v) -> v <= threshold;
	}

}
