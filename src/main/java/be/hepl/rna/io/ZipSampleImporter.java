package be.hepl.rna.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.ISample;
import be.hepl.rna.common.impl.CommonLabeledSample;
import be.hepl.rna.common.impl.CommonSample;

public class ZipSampleImporter implements ISampleImporter {

	private InputStream inputStream;
	private String separator;
	
	public ZipSampleImporter(InputStream inputStream, String separator) {
		this.separator = separator;
		this.inputStream = inputStream;
	}
	
	@Override
	public List<ILabeledSample> importSamples(int outputIndex) {
		List<ILabeledSample> result = new ArrayList<ILabeledSample>();
		ZipInputStream zip = new ZipInputStream(this.inputStream);
		List<String> labels = new ArrayList<String>();
		Map<Integer, BufferedImage> images = new HashMap<Integer, BufferedImage>();
		
		try {
			ZipEntry entry = zip.getNextEntry();
			while(entry != null) {
				tryReadIndex(zip, labels, entry);
				tryReadSample(zip, images, entry);
				entry = zip.getNextEntry();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < labels.size(); i++) {
			double[] in = imageToDoubleArray(images.get(i));
			double[] out = csvToLabel(labels.get(i));
			result.add(new CommonLabeledSample(in, out));
		}
		
		return result;
	}

	private void tryReadSample(ZipInputStream zip, Map<Integer, BufferedImage> images, ZipEntry entry)
			throws IOException {
		if(entry.getName().startsWith("sample_") && entry.getName().endsWith(".png")) {
			BufferedImage image = ImageIO.read(zip);
			int index = Integer.parseInt(entry.getName().substring("sample_".length(), entry.getName().indexOf(".")));
			images.put(index, image);
		}
	}

	private void tryReadIndex(ZipInputStream zip, List<String> labels, ZipEntry entry) {
		if(entry.getName().equals("index.csv")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(zip));
			reader.lines().forEach(line -> labels.add(line));
		}
	}

	private double[] imageToDoubleArray(BufferedImage image) {
		double[] result = new double[image.getHeight() * image.getWidth()];
		for(int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Color c = new Color(image.getRGB(x, y));
				result[x + y*image.getWidth()] = 1 - ((c.getBlue() + c.getRed() + c.getGreen()) / 765.0); // /255 * 3
			}
		}
		return result;
	}
	
	private double[] csvToLabel(String csv) {
		String[] parts = csv.split(this.separator);
		double[] result = new double[parts.length];
		for(int i = 0; i < parts.length; i++) {
			result[i] = Double.parseDouble(parts[i].trim());
		}
		return result;
	}
	
	@Override
	public List<ISample> importSamples() {
		List<ISample> result = new ArrayList<ISample>();
		ZipInputStream zip = new ZipInputStream(this.inputStream);
		Map<Integer, BufferedImage> images = new HashMap<Integer, BufferedImage>();
		
		try {
			ZipEntry entry = zip.getNextEntry();
			while(entry != null) {
				tryReadSample(zip, images, entry);
				entry = zip.getNextEntry();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Integer i : images.keySet()) {
			double[] in = imageToDoubleArray(images.get(i));
			result.add(new CommonSample(in));
		}
		
		return result;
	}
}
