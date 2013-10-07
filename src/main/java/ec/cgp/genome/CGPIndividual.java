package ec.cgp.genome;

import ec.EvolutionState;
import ec.util.MersenneTwisterFast;
import ec.util.Parameter;
import ec.vector.FloatVectorIndividual;
import ec.vector.VectorDefaults;
import ec.vector.VectorIndividual;

public class CGPIndividual extends FloatVectorIndividual {
	private static final long serialVersionUID = 1L;

	public static final String P_CGP_INDIVIDUAL = "cgp-individual";

	// public float[] genome;

	@Override
	public Parameter defaultBase() {
		return VectorDefaults.base().push(P_CGP_INDIVIDUAL);
	}

	/**
	 * Mutate the genome. Adapted from FloatVectorIndividual.
	 * 
	 * TODO this code had mutation probability for the whole chromosome before
	 */
	@Override
	public void defaultMutate(EvolutionState state, int thread) {
		CGPSpecies s = (CGPSpecies) species;
		if (!(s.mutationProbability(0) > 0.0)) {
			return;
		}
		MersenneTwisterFast rng = state.random[thread];

		for (int x = 0; x < genome.length; x++) {
			if (rng.nextBoolean(s.mutationProbability(0))) {
				genome[x] = rng.nextFloat();
			}
		}

	}

	/**
	 * Convex (or "arithmetic") crossover for real-valued genomes. It is shown
	 * to yield improved convergence for regression problems (see Clegg et. al.,
	 * "A new crossover technique for Cartesian genetic programming").
	 */
	@Override
	public void defaultCrossover(EvolutionState state, int thread,
			VectorIndividual ind) {

		float[] p1 = ((CGPIndividual) ind).genome;
		float[] p2 = genome;
		float tmp;

		float[] r;
		do {
			r = new float[] { state.random[thread].nextFloat(),
					state.random[thread].nextFloat() };
		} while (r[0] == 0 || r[1] == 0);

		for (int i = 0; i < genome.length; i++) {
			tmp = p1[i];
			p1[i] = (1 - r[0]) * p1[i] + r[0] * p2[i];
			p2[i] = (1 - r[1]) * tmp + r[1] * p2[i];
		}

	}

	/**
	 * Initializes the individual by randomly choosing float values uniformly
	 * 
	 */
	@Override
	public void reset(EvolutionState state, int thread) {
		CGPSpecies s = (CGPSpecies) this.species;
		genome = new float[s.genomeSize];

		for (int x = 0; x < genome.length; x++) {
			genome[x] = (float) (state.random[thread].nextFloat());
		}
	}

	@Override
	public boolean equals(Object ind) {
		if (!(this.getClass().equals(ind.getClass()))) {
			return false;
		}
		CGPIndividual i = (CGPIndividual) ind;
		if (genome.length != i.genome.length) {
			return false;
		}
		for (int j = 0; j < genome.length; j++) {
			if (genome[j] != i.genome[j]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = this.getClass().hashCode();

		hash = (hash << 1 | hash >>> 31);
		for (int x = 0; x < genome.length; x++) {
			hash = (hash << 1 | hash >>> 31) ^ Float.floatToIntBits(genome[x]);
		}

		return hash;
	}

	/**
	 * Clones the genome
	 */
	public Object clone() {
		CGPIndividual clone = (CGPIndividual) (super.clone());

		// clone the genome
		clone.genome = (float[]) (genome.clone());

		return clone;
	}

	/**
	 * Returns the genome of the current individual
	 */
	public float[] getGenome() {
		return genome;
	}

	@Override
	public void setGenome(Object gen) {
		genome = (float[]) gen;
	}

}
