package be.hepl.rna;

import java.util.HashMap;
import java.util.Map;

public class IterationResult {
	private int iterationIndex;
	private Map<String, Double> results;
	
	public IterationResult(int iterationIndex) {
		this.iterationIndex = iterationIndex;
		this.results = new HashMap<String, Double>();
	}
	
	public int getIterationIndex() {
		return this.iterationIndex;
	}
	
	public void setMetric(String metricName, double metric) {
		results.put(metricName, metric);
	}
	
	public double getMetric(String metricName) {
		return results.get(metricName);
	}
}
