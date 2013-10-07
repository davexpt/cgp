package ec.cgp.functions.logic;

import java.util.ArrayList;

/**
 * Returns true if both arguments are true
 * 
 * @author Davide Nunes
 * 
 */
public class EQFn extends LogicFunction {

	/**
	 * Constructor
	 */
	public EQFn() {
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

		types.set(0, Double.class);
		types.set(1, Double.class);

		return types;

	}

	@Override
	protected Boolean eval(Object[] args) {
		Double arg0 = (Double) args[0];
		Double arg1 = (Double) args[1];

		return arg0.equals(arg1);
	}

	@Override
	public String getName() {
		return "EQ";
	}

}
