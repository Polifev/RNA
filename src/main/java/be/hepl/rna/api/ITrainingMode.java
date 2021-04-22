package be.hepl.rna.api;

import java.util.List;

import javax.naming.OperationNotSupportedException;

public interface ITrainingMode<D1, D2> {
	void sampleBasedWeightsCorrection(ISampleEvaluation<D1> sampleEvaluation, List<ILayer<D2>> layers) throws OperationNotSupportedException;
	void iterationBasedWeightsCorrection(IIterationEvaluation<D1> iteration, List<ILayer<D2>> layers) throws OperationNotSupportedException;
}
