package ec.cgp.eval;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import ec.cgp.functions.Function;
import ec.cgp.genome.CGPIndividual;
import ec.cgp.genome.CGPSpecies;

/**
 * <p>
 * A program interpreter for a {@link CGPIndividual}. This class is capable of
 * loading {@link CGPIndividual} instances, decoding them and executing the
 * program step by step.
 * </p>
 * 
 * <p>
 * The execution is divided into execution branches for each output node. Each
 * step corresponds to the interpretation of one node in all the execution
 * branches starting from the terminals (inputs or functions without arguments).
 * </p>
 * 
 * @author Davide Nunes
 * 
 */
public class CGPSteppableInterpreter implements
		SteppableInterpreter<CGPIndividual> {
	private static final long serialVersionUID = 1L;

	private int steps;

	CGPIndividual cgpIndividual;
	Object[] outputs;
	Object[] inputs;

	// execution interpreter

	// holds the node coordinates to provide execution order
	ArrayList<Stack<Integer>> nodeStacks;
	// used to store the results of the execution
	ArrayList<Stack<Object>> execStacks;

	// to avoid re-parsing nodes
	public static Map<Integer, Object> nodeMap;

	ArrayList<Stack<Integer>> currentNodeStacks;

	private boolean finished;

	@SuppressWarnings("unchecked")
	public void step(Object[] inputs) {
		this.inputs = inputs;
		if (this.cgpIndividual == null)
			throw new RuntimeException(
					"You have to load an individual into the interpreter before stepping");

		if (steps == 0) {
			// initialize execution stacks
			currentNodeStacks = new ArrayList<>(species.numOutputs);
			execStacks = new ArrayList<>(species.numOutputs);

			for (int i = 0; i < species.numOutputs; i++) {
				currentNodeStacks.add((Stack<Integer>) nodeStacks.get(i)
						.clone());
				execStacks.add(new Stack<>());
			}
		}

		// if there is more to execute this will become false;
		finished = true;
		for (int i = 0; i < currentNodeStacks.size(); i++) {
			Stack<Integer> currentNodeStack = currentNodeStacks.get(i);
			Stack<Object> execStack = execStacks.get(i);

			// we have something to execute
			if (!currentNodeStack.isEmpty()) {
				int node = currentNodeStack.pop();
				// this is an input node
				if (node < species.numInputs) {
					// load the input to the execution stack
					execStack.push(inputs[node]);
				} else {
					// this is a function node
					// get index from node coordinates
					int index = species.positionFromNodeNumber(node);
					// get function id from position
					int fn = species.floatToInt(index, cgpIndividual.genome);

					Object[] args = new Object[species.fnSet.get(fn).arity()];
					// get the arguments from the execution stack
					for (int k = 0; k < species.fnSet.get(fn).arity(); k++) {
						args[k] = execStack.pop();
					}

					Function<? extends Object> function = species.fnSet.get(fn);
					// execute the function
					Object result = function.evaluate(args);

					// store the result in the execution stack
					execStack.push(result);

				}
				if (!currentNodeStack.isEmpty()) {
					finished = false;
				}

			}
		}
		steps++;

	}

	float[] genome = null;
	CGPSpecies species = null;

	@Override
	public void load(CGPIndividual individual) {

		if (!(individual instanceof CGPIndividual))
			throw new UnsupportedOperationException(
					"This interpreted only supports CGPFloatVectorIndividuals");

		this.cgpIndividual = (CGPIndividual) individual;
		this.species = (CGPSpecies) cgpIndividual.species;
		this.genome = cgpIndividual.genome;

		nodeStacks = new ArrayList<>(species.numOutputs);
		for (int i = 0; i < species.numOutputs; i++) {
			nodeStacks.add(i, new Stack<Integer>());
		}

		// revaluate results for each output
		for (int i = 0; i < species.numOutputs; i++) {
			int nodeToLoad = species
					.floatToInt((genome.length - 1 - i), genome);
			loadlNode(i, nodeToLoad);

		}

		reset();
	}

	// TODO node stack needs to be in a map for each output
	private void loadlNode(int outputIndex, int nodeNum) {
		// add the current Node to the stack
		nodeStacks.get(outputIndex).push(nodeNum);

		// if this is not an input
		if (nodeNum >= species.numInputs) {
			// get index from node coordinates
			int index = species.positionFromNodeNumber(nodeNum);

			// get function id from position
			int fn = species.floatToInt(index, genome);

			// evaluate current function (fn) arguments
			for (int i = 0; i < species.fnSet.get(fn).arity(); i++) {
				int currentArg = species.floatToInt(index + i + 1, genome);
				loadlNode(outputIndex, currentArg);
			}
		}
	}

	@Override
	public int getNumSteps() {
		return steps;
	}

	@Override
	public boolean finished() {
		return finished;
	}

	@Override
	public void reset() {
		finished = false;
		steps = 0;
	}

	@Override
	public Object[] getOutput() {
		if (finished) {
			Object[] output = new Object[species.numOutputs];
			for (int i = 0; i < species.numOutputs; i++) {
				output[i] = execStacks.get(i).pop();
			}
			return output;
		}
		return null;
	}

	@Override
	public String getExpression() {
		CGPSpecies species = (CGPSpecies) cgpIndividual.species;
		StringBuilder sb = new StringBuilder();

		sb.append("P = (");
		evalExpression((species.floatToInt(cgpIndividual.genome.length - 1,
				cgpIndividual.genome)), sb);
		sb.append(")");

		return sb.toString();
	}

	private void evalExpression(int nodeNum, StringBuilder sb) {
		CGPSpecies species = (CGPSpecies) cgpIndividual.species;

		// add the current Node to the expression

		// if this is not an input
		if (nodeNum >= species.numInputs) {
			// get index from node coordinates
			int index = species.positionFromNodeNumber(nodeNum);

			// get function id from position
			int fn = species.floatToInt(index, cgpIndividual.genome);

			sb.append("(");
			sb.append(species.fnSet.get(fn).getName());

			// evaluate current function (fn) arguments
			for (int i = 0; i < species.fnSet.get(fn).arity(); i++) {
				sb.append(" ");
				int currentArg = species.floatToInt(index + i + 1,
						cgpIndividual.genome);
				evalExpression(currentArg, sb);
			}
			sb.append(")");
		} else {

			// this is an input
			sb.append("(");
			sb.append("I");
			sb.append(nodeNum);
			sb.append(")");

		}

	}

}
