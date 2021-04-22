package be.hepl.rna.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.ISample;

public class TestCsvSampleImporter {
	private InputStream fakeInputStream;

	@Before
	public void setup() throws IOException {
		String csv = "0,0,0\n" + "0,1,1\n" + "1,0,1\n" + "1,1,1\n";
		fakeInputStream = new ByteArrayInputStream(csv.getBytes());
	}

	@Test
	public void importTheRightNumberOfSamples() throws IOException {
		// Given
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", 2);

		// When
		List<ISample> samples = importer.importSamples();

		// Then
		assertEquals(4, samples.size());
	}

	@Test
	public void importTheRightNumberOfLabeledSamples() throws IOException {
		// Given
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", 2);

		// When
		List<ILabeledSample> labeledSamples = importer.importLabeledSamples();

		// Then
		assertEquals(4, labeledSamples.size());
	}

	@Test
	public void importedSamplesHaveTheRightSize() {
		// Given
		final int inputLength = 2;
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", inputLength);

		// When
		List<ISample> samples = importer.importSamples();

		// Then
		ISample sample1 = samples.get(0);
		assertEquals(inputLength, sample1.inputs().length);
	}
	
	@Test
	public void importedLabeledSamplesHaveTheRightInputAndOutputSize() {
		// Given
		final int inputLength = 2;
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", inputLength);

		// When
		List<ILabeledSample> labeledSamples = importer.importLabeledSamples();

		// Then
		ILabeledSample labeledSample1 = labeledSamples.get(0);
		assertEquals(inputLength, labeledSample1.inputs().length);
		assertEquals(1, labeledSample1.expectedOutput().length);
	}
}
