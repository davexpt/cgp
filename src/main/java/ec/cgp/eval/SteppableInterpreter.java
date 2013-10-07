package ec.cgp.eval;

import java.io.Serializable;

import ec.Individual;

/**
 * An interpreter is responsible for loading a program and run it step by step.
 * Different GP representations should provide different Interpreters to run the
 * programs and evaluate the solutions.
 * 
 * @author Davide Nunes
 */
public interface SteppableInterpreter<I extends Individual> extends
		Serializable {
	/**
	 * Loads an Individual into the Interpreter
	 * 
	 * @param individual
	 *            the individual being transformed into a program execution
	 */
	public void load(I individual);

	/**
	 * Return the number of steps taken by this interpreter
	 * 
	 * @return steps the number of steps since the last individual was loaded
	 */
	public int getNumSteps();

	/**
	 * Returns true if this interpreter finished with a program execution
	 * 
	 * @return
	 */
	public boolean finished();

	/**
	 * Resets the program execution to the initial state and with
	 * {@link #getNumSteps()} = 0.
	 */
	public void reset();

	public Object[] getOutput();

	/**
	 * Returns an expression for the currently loaded individual
	 * 
	 * @return expression string
	 */
	public String getExpression();

	/**
	 * Executes one step of the program
	 */
	public void step(Object[] inputs);

}
