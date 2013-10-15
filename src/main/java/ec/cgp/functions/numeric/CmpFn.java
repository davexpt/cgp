package ec.cgp.functions.numeric;

import java.util.ArrayList;

/**
 * Compares two arguments. Returns 1 if the first is greater than the second.
 * Returns -1 if the first is less than the second. Returns 0 if they are equal.
 * The comparison is done according to {@link Double#compareTo(Double)}
 * 
 * @author Davide Nunes
 * 
 */
public class CmpFn extends NumericFunction {

	public CmpFn() {
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

		return arg0.compareTo(arg1) * 1.0;
	}

	@Override
	public String getName() {
		return "cmp";
	}

}
