package be.hepl.rna.api.impl.matrix;

import be.hepl.rna.api.IIterationEvaluation;
import be.hepl.rna.api.IMetric;
import be.hepl.rna.api.ISampleEvaluation;
import cern.colt.matrix.DoubleMatrix1D;

/**
 * This class checks the ratio of correct answers
 * 
 * @author Pol
 *
 */
public class AccuracyMetric implements IMetric<DoubleMatrix1D> {
	private double tolerance;

	public AccuracyMetric(double tolerance) {
		this.tolerance = tolerance;
	}

	@Override
	public double compute(IIterationEvaluation<DoubleMatrix1D> iterationEvaluation) {
		double accuracy = 0;

		for (ISampleEvaluation<DoubleMatrix1D> sample : iterationEvaluation.getSampleEvaluations()) {
			int outputIndex = sample.getLayerOutputs().length - 1;
			DoubleMatrix1D expected = sample.getExpectedOutput();
			DoubleMatrix1D actual = sample.getLayerOutputs()[outputIndex];

			boolean error = false;
			DoubleMatrix1D clean = expected.copy().assign(actual, MatrixFunctions.SUBSTRACTION)
					.assign(MatrixFunctions.ABS);
			for (int i = 0; i < clean.size() && !error; i++) {
				error = clean.getQuick(i) > tolerance;
			}
			if (!error) {
				accuracy++;
			}
		}
		accuracy /= iterationEvaluation.getSampleEvaluations().size();
		return accuracy;
	}
}