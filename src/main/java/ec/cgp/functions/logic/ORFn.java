package ec.cgp.functions.logic;

import java.util.ArrayList;

/**
 * Returns true if both arguments are true
 * 
 * @author Davide Nunes
 * 
 */
public class ORFn extends LogicFunction {

	/**
	 * Constructor
	 */
	public ORFn() {
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

		types.set(0, Boolean.class);
		types.set(1, Boolean.class);

		return types;

	}

	@Override
	protected Boolean eval(Object[] args) {
		Boolean arg0 = (Boolean) args[0];
		Boolean arg1 = (Boolean) args[1];

		return arg0 || arg1;
	}

	@Override
	public String getName() {
		return "OR";
	}

}
