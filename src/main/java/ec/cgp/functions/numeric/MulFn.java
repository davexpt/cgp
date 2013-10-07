package ec.cgp.functions.numeric;

import java.util.ArrayList;

public class MulFn extends NumericFunction {

	public MulFn() {
		super();
	}

	@Override
	public int arity() {
		return 2;
	}

	@Override
	protected ArrayList<Class<? extends Object>> setTypeMap() {
		ArrayList<Class<? extends Object>> types = new ArrayList<Class<? extends Object>>(
				arity());

		types.add(Double.class);
		types.add(Double.class);

		return types;
	}

	@Override
	protected Double eval(Object[] args) {
		Double arg0 = (Double) args[0];
		Double arg1 = (Double) args[1];

		return arg0.doubleValue() * arg1.doubleValue();
	}

	@Override
	public String getName() {
		return "*";
	}

}
