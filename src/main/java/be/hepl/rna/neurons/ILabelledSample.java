package be.hepl.rna.neurons;

public interface ILabelledSample<T, U> extends ISample<T>{
	U expectedOutput();
}
