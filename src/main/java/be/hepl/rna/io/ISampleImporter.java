package be.hepl.rna.io;

import java.util.List;

import be.hepl.rna.common.ILabeledSample;
import be.hepl.rna.common.ISample;

public interface ISampleImporter {

	public List<ILabeledSample> importSample(int outputIndex);
	
	public List<ISample> importSample();
}