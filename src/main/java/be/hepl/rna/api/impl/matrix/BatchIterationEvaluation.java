package be.hepl.rna.api.impl.matrix;

import java.util.ArrayList;
import java.util.List;

import be.hepl.rna.api.IBatchEvaluation;
import be.hepl.rna.api.ISampleEvaluation;
import cern.colt.matrix.DoubleMatrix1D;

public class BatchIterationEvaluation implements IBatchEvaluation<DoubleMatrix1D>{
	private List<ISampleEvaluation<DoubleMatrix1D>> sampleEvaluations;
	
	BatchIterationEvaluation(int batchSize) {
		this.sampleEvaluations = new ArrayList<>(batchSize);
	}
	
	@Override
	public List<ISampleEvaluation<DoubleMatrix1D>> getSampleEvaluations() {
		return sampleEvaluations;
	}

	@Override
	public void addTrainingResult(ISampleEvaluation<DoubleMatrix1D> sampleEvaluation) {
		this.sampleEvaluations.add(sampleEvaluation);
	}
}
