package be.hepl.rna.api;

import java.util.List;

/**
 * 
 * @author Pol
 *
 * @param <D1> The representation of vectors
 */
public interface IIterationEvaluation<D1> {	
	int getIterationNumber();
	List<ISampleEvaluation<D1>> getSampleEvaluations();
	void addTrainingResult(ISampleEvaluation<D1> sampleEvaluation);
}
