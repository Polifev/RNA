package be.hepl.rna.neurons;

public interface ISample<T> {
	int dimension();
	T elementAt(int index);
}
