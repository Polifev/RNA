package be.hepl.rna.matrix.stopconditions;

import java.util.function.Predicate;

import be.hepl.rna.common.IIterationEvaluation;
import be.hepl.rna.common.ISampleEvaluation;
import be.hepl.rna.matrix.MatrixFunctions;
import cern.colt.matrix.DoubleMatrix1D;

/**
 * This class checks the sum squared error (= loss)
 * @author Pol
 *
 */
public class LossCondition implements Predicate<IIterationEvaluation<DoubleMatrix1D>>{

	private double threshold;
	
	public LossCondition(double threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public boolean test(IIterationEvaluation<DoubleMatrix1D> t) {
		double loss = 0;
		
		for (ISampleEvaluation<DoubleMatrix1D> sample : t.getSampleEvaluations()) {
			int outputIndex = sample.getLayerOutputs().length - 1;
			DoubleMatrix1D sampleError = sample.getExpectedOutput().copy().assign(sample.getLayerOutputs()[outputIndex], MatrixFunctions.SUBSTRACTION);
			double squaredError = sampleError.aggregate(MatrixFunctions.ADDITION, MatrixFunctions.SQUARE);
			loss += squaredError;
		}
		loss /= t.getSampleEvaluations().size();
		System.out.println("Loss:" + loss);
		return loss < threshold;
	}

}
