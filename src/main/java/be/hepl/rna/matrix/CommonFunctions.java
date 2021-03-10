package be.hepl.rna.matrix;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;

public class CommonFunctions {
	public static final DoubleDoubleFunction SUBSTRACTION = new Substraction();
	public static final DoubleDoubleFunction ADDITION = new Addition();
	public static final DoubleFunction ABS = new Abs();
	
	private static class Addition implements DoubleDoubleFunction {
		@Override
		public double apply(double arg0, double arg1) {
			return arg0 + arg1;
		}
	}
	
	private static class Substraction implements DoubleDoubleFunction {
		@Override
		public double apply(double arg0, double arg1) {
			return arg0 - arg1;
		}
	}
	
	private static class Abs implements DoubleFunction{

		@Override
		public double apply(double arg0) {
			return Math.abs(arg0);
		}
		
	}
}
