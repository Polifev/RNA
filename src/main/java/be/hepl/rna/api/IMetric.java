package be.hepl.rna.api;

public interface IMetric<D1> {
	double compute(IBatchEvaluation<D1> iteration);
}
