package ec.cgp.functions;

import java.util.ArrayList;
import java.util.Set;

/**
 * Extend this and provide a set of functions in the constructor to get the
 * functionality of a FunctionSet necessary to the CGP evolutino process.
 * 
 * @author Davide Nunes
 * 
 * @param <E>
 *            The type of the functions in this set
 * 
 *            Note: at the moment, this CGP implementation only supports one
 *            type of functions, in the function multi-type function sets will
 *            be added-
 */
public abstract class AbstractFunctionSet<E> implements FunctionSet<E> {

	private ArrayList<Function<E>> functions;

	private int maxArity;

	/**
	 * Constructor
	 * 
	 * @param functions
	 */
	public AbstractFunctionSet() {
		Set<Function<E>> fnSet = loadFunctions();
		this.functions = new ArrayList<>(fnSet.size());

		int maxArity = 0;
		for (Function<E> function : fnSet) {
			this.functions.add(function);
			if (function.arity() > maxArity)
				maxArity = function.arity();
		}
		this.maxArity = maxArity;
	}

	protected abstract Set<Function<E>> loadFunctions();

	public Function<E> get(int index) {
		return functions.get(index);
	}

	public int size() {
		return functions.size();
	}

	public int maxArity() {
		return maxArity;
	}
}
