package be.hepl.rna.api;

import java.util.List;

public interface ITrainingMode<D1, D2> {
	void updateWeights(IBatchEvaluation<D1> iteration, List<ILayer<D2>> layers);
}
