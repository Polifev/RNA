package be.hepl.rna.matrix;

import java.util.ArrayList;
import java.util.List;

import be.hepl.rna.common.IIterationEvaluation;
import be.hepl.rna.common.ISampleEvaluation;
import cern.colt.matrix.DoubleMatrix1D;

public class MatrixIterationEvaluation implements IIterationEvaluation<DoubleMatrix1D>{
	private int iterationNumber;
	private List<ISampleEvaluation<DoubleMatrix1D>> sampleEvaluations;
	
	MatrixIterationEvaluation(int iterationNumber) {
		this.iterationNumber = iterationNumber;
		this.sampleEvaluations = new ArrayList<>();
	}
	
	public int getIterationNumber() {
		return iterationNumber;
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
