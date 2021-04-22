package be.hepl.rna.api;

public interface IMetric<D1> {
	double compute(IIterationEvaluation<D1> iteration);
}
