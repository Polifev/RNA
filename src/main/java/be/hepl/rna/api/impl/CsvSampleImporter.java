package be.hepl.rna.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import be.hepl.rna.api.ILabeledSample;
import be.hepl.rna.api.ISample;
import be.hepl.rna.api.ISampleImporter;

public class CsvSampleImporter implements ISampleImporter {

	private final InputStream in;
	private final String separator;
	private final int inputSize;
	
	public CsvSampleImporter(InputStream in, String separator, int inputSize) {
		this.in = in;
		this.separator = separator;
		this.inputSize = inputSize;
	}
	
	@Override
	public List<ISample> importSamples() {
		return readAndMap(line ->{
			String[] parts = line.split(this.separator);
			double[] input = extractPart(parts, 0, this.inputSize);
			return new CommonSample(input);
		});
	}
	
	@Override
	public List<ILabeledSample> importAsLabeledSamples() {
		return readAndMap(line ->{
			String[] parts = line.split(this.separator);
			double[] input = extractPart(parts, 0, this.inputSize);
			double[] output = extractPart(parts, this.inputSize, parts.length);
			return new CommonLabeledSample(input, output);
		});
	}
	
	private <T> List<T> readAndMap(Function<String, T> mapper){
		List<T> result = new ArrayList<T>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
			String line;
			while((line = reader.readLine()) != null) {
				result.add(mapper.apply(line));
			}
		} catch (IOException ex) {
			result.clear();
		}
		return result;
	}
	
	private double[] extractPart(String[] lineParts, int beginIndex, int endIndex) {
		double[] result = new double[endIndex - beginIndex];
		for(int i = beginIndex ; i < endIndex; i++) {
			result[i - beginIndex] = Double.parseDouble(lineParts[i]);
		}
		return result;
	}
}
