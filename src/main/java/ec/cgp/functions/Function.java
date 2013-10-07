package ec.cgp.functions;

public interface Function<Type> {
	/**
	 * Returns the output type of this function this is used to check the type
	 * of the function in runtime since this can't be returned in another way
	 * 
	 * @return a java {@link Class class}
	 */
	Class<Type> getType();

	/**
	 * Returns the arity of this function. This is, the number of arguments this
	 * functions takes.
	 * 
	 * @return
	 */
	int arity();

	/**
	 * Return the type of the argument passed.
	 * 
	 * @param arg
	 *            integer index for the argument
	 * @return
	 */
	Class<? extends Object> getArgType(int arg);

	/**
	 * Evaluates the function given the args
	 * 
	 * @param args
	 * @return
	 */
	Type evaluate(Object[] args);

	String getName();

}
