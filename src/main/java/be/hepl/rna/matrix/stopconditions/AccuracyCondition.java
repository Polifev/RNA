package be.hepl.rna.matrix.stopconditions;

import java.util.function.Predicate;

import be.hepl.rna.common.IIterationEvaluation;
import be.hepl.rna.common.ISampleEvaluation;
import be.hepl.rna.matrix.MatrixFunctions;
import cern.colt.matrix.DoubleMatrix1D;

/**
 * This class checks the ratio of correct answers
 * @author Pol
 *
 */
public class AccuracyCondition implements Predicate<IIterationEvaluation<DoubleMatrix1D>>{

	private double threshold;
	private double tolerance;
	
	public AccuracyCondition(double threshold, double tolerance) {
		this.threshold = threshold;
		this.tolerance = tolerance;
	}
	
	@Override
	public boolean test(IIterationEvaluation<DoubleMatrix1D> t) {
		double accuracy = 0;
		
		for (ISampleEvaluation<DoubleMatrix1D> sample : t.getSampleEvaluations()) {
			int outputIndex = sample.getLayerOutputs().length - 1;
			DoubleMatrix1D expected = sample.getExpectedOutput();
			DoubleMatrix1D actual = sample.getLayerOutputs()[outputIndex];
			
			boolean error = false;
			DoubleMatrix1D clean = expected.copy().assign(actual, MatrixFunctions.SUBSTRACTION).assign(MatrixFunctions.ABS);
			for(int i = 0; i < clean.size() && !error; i++) {
				error = clean.getQuick(i) > tolerance;
			}
			if(!error) {
				accuracy ++;
			}
		}
		accuracy /= t.getSampleEvaluations().size();
		System.out.println("Accuracy:" + accuracy);
		return accuracy >= threshold;
	}
}