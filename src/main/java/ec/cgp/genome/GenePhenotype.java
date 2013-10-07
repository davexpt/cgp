package ec.cgp.genome;

/**
 * Enumeration that represents the types of genes we can have in a genotype. It
 * also interprets the gene phenotype and from a gene index, it returns one of
 * the possible Gene phenotypes its function given information about the
 * genotype.
 * 
 * @author Davide Nunes
 * 
 */
public enum GenePhenotype {

	FUNCTION_GENE, ARGUMENT_COOR_GENE, OUTPUT_GENE;

	public static GenePhenotype eval(int index, CGPSpecies species) {
		if (index >= species.numNodes() * (species.maxArity() + 1))
			return OUTPUT_GENE;

		int maxArity = species.maxArity();

		int phenotype = index % (maxArity + 1);

		return phenotype == 0 ? FUNCTION_GENE : ARGUMENT_COOR_GENE;
	}
}
