package ec.cgp.functions.logic;

import java.util.ArrayList;

/**
 * Returns true if both arguments are true
 * 
 * @author Davide Nunes
 * 
 */
public class NOTFn extends LogicFunction {

	/**
	 * Constructor
	 */
	public NOTFn() {
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

		types.set(0, Boolean.class);

		return types;

	}

	@Override
	protected Boolean eval(Object[] args) {
		Boolean arg0 = (Boolean) args[0];

		return !arg0;
	}

	@Override
	public String getName() {
		return "NOT";
	}

}
