package ec.cgp.functions.numeric;

import java.util.ArrayList;

import org.apache.commons.math3.util.FastMath;

public class SigmoidFn extends NumericFunction {

	public SigmoidFn() {
		super();
	}

	@Override
	public int arity() {
		return 1;
	}

	@Override
	protected ArrayList<Class<? extends Object>> setTypeMap() {
		ArrayList<Class<? extends Object>> types = new ArrayList<Class<? extends Object>>(
				arity());

		types.set(0, Double.class);

		return types;
	}

	@Override
	protected Double eval(Object[] args) {
		Double x = (Double) args[0];
		return 1 / (1 + (FastMath.exp(-x)));
	}

	@Override
	public String getName() {
		return "sig";
	}

}
