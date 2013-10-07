package ec.cgp.functions;

/**
 * FunctionSet is implemented as a List of Functions so that functions can be
 * returned by index, the index of each function is irrelevant for the CGP
 * evolution.
 * 
 * We just need an indexable set of functions a preferrable implementation could
 * be for instance an Array List. Each function in the list should also be
 * unique.
 * 
 * An abstract implementation of this interface is available to be extended.
 * 
 * @author Davide Nunes
 * 
 * @param <E>
 *            the type of functions considered in this function set
 * 
 *            Note: for future versions of this where we can consider Typed CGP
 *            each function coordinate space will be assigned with a function
 *            set
 */
public interface FunctionSet<E> {

	/**
	 * Returns the max arity of the functions contained in this function set.
	 * 
	 * @return max arity value (maximum number of parameters from all the
	 *         included functions)
	 */
	int maxArity();

	/**
	 * Number of functions in the function set
	 * 
	 * @return number of functions
	 */
	int size();

	Function<E> get(int index);

	Class<E> getType();

}
