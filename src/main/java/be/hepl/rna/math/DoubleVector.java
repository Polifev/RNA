package be.hepl.rna.math;

public class DoubleVector {
	private double[] vector;

	public DoubleVector(int size) {
		this(new double[size]);
	}

	public DoubleVector(double... vector) {
		this.vector = vector;
	}

	public double element(int index) {
		return vector[index];
	}
	
	public double element(int index, double value) {
		return (vector[index] = value);
	}

	public int dimension() {
		return vector.length;
	}

	public double crossProduct(DoubleVector other) {
		if (this.dimension() != other.dimension()) {
			throw new IllegalArgumentException("You cannot cross vectors with different dimensions.");
		}
		double result = 0.0;
		for (int i = 0; i < dimension(); i++) {
			result += this.element(i) * other.element(i);
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = true;
		try {
			DoubleVector other = (DoubleVector) o;
			if (this.dimension() == other.dimension()) {
				for (int i = 0; i < this.dimension(); i++) {
					result &= Math.abs(this.element(i) - other.element(i)) < Constants.EPSILON;
				}
			} else {
				result = false;
			}
		} catch (ClassCastException e) {
			result = false;
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append(String.format("%.02f", element(0)));
		for (int i = 1; i < dimension(); i++) {
			builder.append(String.format("; %.02f", element(i)));
		}
		builder.append(']');
		return builder.toString();
	}
}
