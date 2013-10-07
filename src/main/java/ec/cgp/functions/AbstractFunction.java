package ec.cgp.functions;
import java.util.ArrayList;

public abstract class AbstractFunction<Type> implements Function<Type> {

	

	
	private ArrayList<Class<? extends Object>> typeMap;
	private int arity;

	/**
	 * Constructor receives the arity of the function and the map that maps an
	 * argument index to its respective type.
	 * 
	 * @throws Exception
	 */
	public AbstractFunction() {
		this.typeMap = setTypeMap();
		this.arity = arity();
		if (arity < 0 || typeMap == null) {
			throw new RuntimeException(
					"you must provide a correct arity and type map");
		}

		if (arity > typeMap.size())
			throw new RuntimeException(
					"the arity of the function is bigger than the type map provided");

	}

	public Class<? extends Object> getArgType(int arg) {
		Class<? extends Object> type = null;
		try {
			type = typeMap.get(arg);

		} catch (Exception e) {
			throw new IndexOutOfBoundsException(
					"You have to supply an argument index between " + 0
							+ " and " + (arity - 1));
		}
		return type;
	}

	/**
	 * Make so that implementations of this class provide a typeMap for the
	 * arguments of the function
	 * 
	 * @return
	 */
	protected abstract ArrayList<Class<? extends Object>> setTypeMap();

	/**
	 * Checks for the correctness of the function arguments and forwards the
	 * method to the {@link #eval(Object[]))} method so that the implementing
	 * classes don't have to manage the argument type correctness or compliance
	 * with function arity.
	 */
	public Type evaluate(Object[] args) {
		if (args == null) {
			throw new RuntimeException("args are null");
		}
		if (args.length != arity) {
			throw new RuntimeException(
					"the number of arguments does not satisfy the function arity");
		}
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (!arg.getClass().equals(typeMap.get(i))) {
				throw new RuntimeException(
						"The sypplied argument type does not satisfy the function specification: argument "
								+ i
								+ " has the type "
								+ arg.toString()
								+ ", the expected type was "
								+ typeMap.get(i).toString());
			}

		}

		return eval(args);
	}

	protected abstract Type eval(Object[] args);

}
