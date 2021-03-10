package be.hepl.rna.common;

import java.util.List;

public interface ITrainingMode<D1, D2> {
	void sampleBasedWeightsCorrection(ISampleEvaluation<D1> sampleEvaluation, List<ILayer<D2>> layers);
	void iterationBasedWeightsCorrection(IIterationEvaluation<D1> iteration, List<ILayer<D2>> layers);
}
