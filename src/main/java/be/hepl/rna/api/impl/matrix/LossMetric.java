package be.hepl.rna.api.impl.matrix;

import be.hepl.rna.api.IBatchEvaluation;
import be.hepl.rna.api.IMetric;
import be.hepl.rna.api.ISampleEvaluation;
import cern.colt.matrix.DoubleMatrix1D;

public class LossMetric implements IMetric<DoubleMatrix1D> {

	@Override
	public double compute(IBatchEvaluation<DoubleMatrix1D> iterationEvaluation) {
		double loss = 0;
		for (ISampleEvaluation<DoubleMatrix1D> sample : iterationEvaluation.getSampleEvaluations()) {
			int outputIndex = sample.getLayerOutputs().length - 1;
			DoubleMatrix1D sampleError = sample.getExpectedOutput().copy().assign(sample.getLayerOutputs()[outputIndex],
					MatrixFunctions.SUBSTRACTION);
			double squaredError = sampleError.aggregate(MatrixFunctions.ADDITION, MatrixFunctions.SQUARE);
			loss += squaredError;
		}
		loss /= iterationEvaluation.getSampleEvaluations().size();
		return loss;
	}

}
