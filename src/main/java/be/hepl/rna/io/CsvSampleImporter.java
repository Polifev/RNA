package be.hepl.rna.io;

import java.io.BufferedReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.common.impl.CommonSample;

public class CsvSampleImporter implements ISampleImporter {

	private final String path, separator;
	
	public CsvSampleImporter(String path, String separator) {
		this.path = path;
		this.separator = separator;
	}
	
	@Override
	public List<ILabeledSample> importSample(int outputIndex) {
		List<ILabeledSample> samples = new LinkedList<ILabeledSample>();
		try(BufferedReader reader = new BufferedReader(Files.newBufferedReader(Paths.get(path)))){
			while(reader.ready()) {
				String line = reader.readLine();
				String[] values = line.split(separator);
				double[] input = new double[outputIndex];
				double[] output = new double[values.length - outputIndex];
				for(int i = 0 ; i < outputIndex; i++) {
					input[i] = Double.parseDouble(values[i]);
				}
				for(int i = outputIndex ; i < values.length ; i++) {
					output[i-outputIndex] = Double.parseDouble(values[i]);
				}
				samples.add(new CommonLabeledSample(input, output));
			}
		} catch (Exception e) {
			System.out.println("SampleImporter > Erreur lors de la lecture : " + path);
		}
		return samples;
	}
	
	@Override
	public List<ISample> importSample() {
		List<ISample> samples = new LinkedList<ISample>();
		double[] input = null;
		try(BufferedReader reader = new BufferedReader(Files.newBufferedReader(Paths.get(path)))){
			while(reader.ready()) {
				String line = reader.readLine();
				String[] values = line.split(separator);
				input = new double[values.length];
				for(int i = 0 ; i < values.length; i++) {
					input[i] = Double.parseDouble(values[i]);
				}
				samples.add(new CommonSample(input));
			}
		} catch (Exception e) {
			System.out.println("SampleImporter > Erreur lors de la lecture : " + path);
		}
		return samples;
	}
}