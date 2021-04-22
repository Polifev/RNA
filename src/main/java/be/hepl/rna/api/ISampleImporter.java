package be.hepl.rna.api;

import java.util.List;

public interface ISampleImporter {
	public List<ISample> importSamples();
	public List<ILabeledSample> importLabeledSamples();
}