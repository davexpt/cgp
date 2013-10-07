package ec.cgp.functions.numeric;

import java.util.ArrayList;

import org.apache.commons.math3.util.FastMath;

public class SinFn extends NumericFunction {

	public SinFn() {
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
		return FastMath.sin(x);
	}

	@Override
	public String getName() {
		return "sin";
	}

}
