package be.hepl.rna.io;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.hepl.rna.api.ILabeledSample;
import be.hepl.rna.api.ISample;
import be.hepl.rna.api.ISampleImporter;
import be.hepl.rna.api.impl.CsvSampleImporter;

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
	
	@Test
	public void importedSamplesHaveTheRightData() {
		// Given
		final int inputLength = 2;
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", inputLength);

		// When
		List<ILabeledSample> samples = importer.importLabeledSamples();

		// Then
		ILabeledSample sample1 = samples.get(0);
		assertEquals(0, sample1.inputs()[0], 0.00_000_1);
		assertEquals(0, sample1.inputs()[1], 0.00_000_1);
		assertEquals(0, sample1.expectedOutput()[0], 0.00_000_1);
		
		ILabeledSample sample4 = samples.get(3);
		assertEquals(1, sample4.inputs()[0], 0.00_000_1);
		assertEquals(1, sample4.inputs()[1], 0.00_000_1);
		assertEquals(1, sample4.expectedOutput()[0], 0.00_000_1);
	}
	
	@Test
	public void importedLabeledSamplesHaveTheRightData() {
		// Given
		final int inputLength = 2;
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", inputLength);

		// When
		List<ISample> samples = importer.importSamples();

		// Then
		ISample sample1 = samples.get(0);
		assertEquals(0, sample1.inputs()[0], 0.00_000_1);
		assertEquals(0, sample1.inputs()[1], 0.00_000_1);
		
		ISample sample2 = samples.get(1);
		assertEquals(0, sample2.inputs()[0], 0.00_000_1);
		assertEquals(1, sample2.inputs()[1], 0.00_000_1);
	}
	
	@Test
	public void inputStreamIsFullyReadAfterAfterSamplesImport() throws IOException {
		// Given
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", 2);
	
		// When
		importer.importSamples();
	
		// Then
		assertEquals(0, fakeInputStream.available());
	}
	
	@Test
	public void inputStreamIsFullyReadAfterLabeledSamplesImport() throws IOException {
		// Given
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", 2);
	
		// When
		importer.importLabeledSamples();
	
		// Then
		assertEquals(0, fakeInputStream.available());
	}
	
	@Test
	public void importedSamplesListIsEmptyIfIOExceptionOccurs() throws IOException {
		// Given
		fakeInputStream = mock(InputStream.class);
		when(fakeInputStream.read()).thenThrow(IOException.class);
		
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", 2);
	
		// When
		List<ISample> samples = importer.importSamples();
	
		// Then
		assertEquals(0, samples.size());
	}
	
	@Test
	public void importedLabeledSamplesListIsEmptyIfIOExceptionOccurs() throws IOException {
		// Given
		fakeInputStream = mock(InputStream.class);
		when(fakeInputStream.read()).thenThrow(IOException.class);
		
		ISampleImporter importer = new CsvSampleImporter(fakeInputStream, ",", 2);
	
		// When
		List<ILabeledSample> samples = importer.importLabeledSamples();
	
		// Then
		assertEquals(0, samples.size());
	}
}
