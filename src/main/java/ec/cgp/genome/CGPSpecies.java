package ec.cgp.genome;

import ec.EvolutionState;
import ec.cgp.functions.FunctionSet;
import ec.util.Parameter;
import ec.vector.FloatVectorSpecies;
import ec.vector.VectorDefaults;

/**
 * Handles the representations of Cartesian Genetic Programs with typed layers.
 * 
 * 
 * @author Davide Nunes
 * 
 */
public class CGPSpecies extends FloatVectorSpecies {
	private static final long serialVersionUID = 1L;

	// configuration names used in the parameter files
	public static final String P_VECTORSPECIES = "species";
	public static final String P_NUM_INPUTS = "num-inputs";
	public static final String P_NUM_NODES = "num-nodes";
	public static final String P_NUM_OUTPUTS = "num-outputs";
	public static final String P_FUNCTION_SET = "function-set";

	// number of function nodes
	public int numNodes;
	public int numOutputs;
	public int numInputs;
	public FunctionSet fnSet;

	/**
	 * Computes the number of genes necessary in all the rows for the CGP
	 * program each function node is encoded as: [FN, ARG0, ... ARGn, this is
	 * <b>function name</b>, <b>arguments</b>]
	 * 
	 * @return total number of genes for all the layers of the individual
	 */
	public int numGenes() {
		int numGenes = numNodes * (1 + fnSet.maxArity()) + numOutputs;
		return numGenes;
	}

	/**
	 * From the given gene position, interprets whether the gene represents a
	 * function name or argument coordinate
	 */
	public GenePhenotype phenotype(int index) {
		return GenePhenotype.eval(index, this);
	}

	/**
	 * Returns the coordinate (index) of a function to which the current index
	 * belongs being it the function name or the functino argument gene.
	 * 
	 * 
	 * 
	 * @param index
	 *            the index of the gene being interpreted
	 * @param genome
	 *            the genome where the gene is located
	 * @return index of the corresponding function
	 */
	public int getFunctionCoor(int index, float[] genome) {
		return positionFromNodeNumber(nodeNumber(index, genome));
	}

	/**
	 * Interprets the float at a given index, returning the max int
	 * corresponding to the float value.
	 * 
	 * if the index is of an output the int can be any node number if the index
	 * is of a function, the int can be any function index from the function set
	 * if the index is of a function argument, the int can be any node number to
	 * the left of the current node.
	 * 
	 * @param index
	 *            gene index with a float value to be interpreted
	 * @param genome
	 * @return
	 */
	public int floatToInt(int index, float[] genome) {
		return scale(genome[index], computeMaxGeneValue(index, genome) + 1);
	}

	/**
	 * Computes max possible gene value value for the given position. Min gene
	 * value is assumed to always be zero. The max value depends on the
	 * phenotype of the given position.
	 * 
	 * <ul>
	 * <li>If we are referring to a output gene, its arguments can only be
	 * mapped to nodes with the values [0..numNodes-1]</li>
	 * <li>If we are referring to a function gene, the max value that this can
	 * have is number of functions -0 (functions are indexed like an array.
	 */
	public int computeMaxGeneValue(int index, float[] genome) {
		GenePhenotype phenotype = phenotype(index);

		if (phenotype == GenePhenotype.OUTPUT_GENE)
			return numInputs + numNodes - 1;
		if (phenotype == GenePhenotype.FUNCTION_GENE) {
			return fnSet.size() - 1;
		}
		/*
		 * Otherwise, this is a function argument. Since this is a feed-forward
		 * CGP, the max gene value must refer only to input nodes, or program
		 * nodes to the left of the current gene.
		 */
		return nodeNumber(index, genome) - 1;
	}

	/**
	 * Determine the node number from the given genome position. Function
	 * arguments refer to node numbers. Inputs are numbered from [0, numInputs -
	 * 1]. Function nodes are numbered from [numInputs, numInputs + numNodes -
	 * 1]. Output nodes are numbered from [numInputs + numNodes, numInputs +
	 * numNodes + numOuputs - 1].
	 */
	public int nodeNumber(int index, float[] genome) {
		if (phenotype(index) == GenePhenotype.OUTPUT_GENE)
			return numInputs + numNodes
					+ (index - (numNodes * (maxArity() + 1)));
		return numInputs + (index / (maxArity() + 1));
	}

	/**
	 * Determine the start position (index) of the given node number.
	 * 
	 * Note: inputs are not represented in the genome
	 * 
	 * @param nodeNumber
	 *            a function or output node number, input node numbers are not
	 *            represented in the genome.
	 * 
	 */
	public int positionFromNodeNumber(int nodeNumber) {
		if (nodeNumber < numInputs)
			throw new IllegalArgumentException("Sorry, nodeNumber ("
					+ nodeNumber + ") must refer to function for output nodes");
		return (nodeNumber - numInputs) * (maxArity() + 1);
	}

	/**
	 * Default parameter base for vector species
	 */
	@Override
	public Parameter defaultBase() {
		return VectorDefaults.base().push(P_VECTORSPECIES);
	}

	/**
	 * Get the parameters from the parameter base
	 */
	public void setup(EvolutionState state, Parameter base) {
		System.out.println("species setup");

		Parameter defaultP = defaultBase();
		numNodes = state.parameters.getInt(base.push(P_NUM_NODES),
				defaultP.push(P_NUM_NODES), 1);
		numInputs = state.parameters.getInt(base.push(P_NUM_INPUTS),
				defaultP.push(P_NUM_INPUTS), 1);
		numOutputs = state.parameters.getInt(base.push(P_NUM_OUTPUTS),
				defaultP.push(P_NUM_OUTPUTS), 1);

		this.fnSet = (FunctionSet) state.parameters.getInstanceForParameter(
				base.push(P_FUNCTION_SET), defaultP.push(P_FUNCTION_SET),
				FunctionSet.class);

		state.parameters.set(new Parameter("pop.subpop.0.species.genome-size"),
				"" + this.numGenes());
		state.parameters.set(new Parameter("pop.subpop.0.species.min-gene"),
				"0");
		state.parameters.set(new Parameter("pop.subpop.0.species.max-gene"),
				"1");

		// preserve memory
		state.output.setStore(false);

		state.output.exitIfErrors();
		super.setup(state, base);

	}

	public int numNodes() {
		return numNodes;
	}

	public int maxArity() {
		return fnSet.maxArity();
	}

	/**
	 * Return the {@code int} in the range [0,max-1] represented by the given
	 * float which is in the range [0.0,1.0).
	 * 
	 * @param val
	 *            float value to scale
	 * @param max
	 *            the maximum integer to generate
	 * @return scaled integer
	 */
	private int scale(float val, int max) {
		if (val == 1) {
			return max - 1;
		}
		return (int) (val * max);
	}
}
