package be.hepl.rna.matrix;

import java.util.ArrayList;
import java.util.List;

public class Iteration {
	private int iterationNumber;
	private List<SampleEvaluation> trainingResults;
	
	public Iteration(int iterationNumber) {
		this.iterationNumber = iterationNumber;
		this.trainingResults = new ArrayList<>();
	}
	
	public int getIterationNumber() {
		return iterationNumber;
	}
	public List<SampleEvaluation> getTrainingResults() {
		return trainingResults;
	}
	public void addTrainingResult(SampleEvaluation r) {
		this.trainingResults.add(r);
	}
}
