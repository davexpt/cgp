package cgp.problems;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;
import ec.cgp.Evaluator;
import ec.cgp.FitnessCGP;
import ec.cgp.eval.CGPSteppableInterpreter;
import ec.cgp.eval.ProblemCGP;
import ec.cgp.genome.CGPIndividual;
import ec.cgp.representation.CGPVectorIndividual;
import ec.cgp.representation.CGPVectorSpecies;
import ec.multiobjective.*;

import java.util.*;

import org.apache.commons.math3.util.FastMath;

/**
 * 
 * A simple class problem to serve as a test to debug symbolic regression.
 * 
 * Regression problem.
 * 
 * @author Davide Nunes
 * 
 *         adapted from the CGP library of David Oranchak
 */
public class Regression extends ProblemCGP {
	private static final long serialVersionUID = 1L;

	/** 50 randomly generated test points used for fitness evaluation */
	static Double[] testPoints = new Double[] { 0.14794326, 0.108998775,
			0.4068538, -0.47665644, 0.80171514, 0.095415235, 0.026154399,
			-0.009217739, 0.029135108, 0.2079128, 0.7251047, -0.23340857,
			0.524932, -0.7481402, 0.382663, -0.7115128, -0.027229786,
			-0.099066496, 0.7357291, 0.33994365, 0.9047879, -0.4072944,
			-0.34821618, -0.044303536, -0.90075254, 0.30938172, 0.988202,
			0.5843065, -0.20018125, 0.73057616, 0.2547536, 0.018245697,
			-0.44960117, 0.10484755, -0.42382956, -0.3190421, 0.78481805,
			-0.4668939, 0.7419597, 0.9006864, -0.9791528, -0.59703183,
			-0.4592893, -0.9315028, -0.073480844, -0.28456664, -0.69468606,
			-0.119933486, -0.31513882, 0.63493156 };

	/** Evaluate the CGP and compute fitness */
	public void evaluate(EvolutionState state, Individual ind,
			int subpopulation, int threadnum) {
		if (ind.evaluated)
			return;

		CGPIndividual individual = (CGPIndividual) ind;

		double diff = 0f;

		Double[] inputs = new Double[3];
		double fn = 0f;

		for (int i = 0; i < testPoints.length; i++) {
			inputs[0] = testPoints[i]; // one of the randomly-generated

			inputs[1] = 1.0; // a hard-coded fixed constant value

			CGPSteppableInterpreter interpreter = new CGPSteppableInterpreter();

			// load the program
			interpreter.load(individual);

			// run the program
			while (!interpreter.finished()) {
				interpreter.step(inputs);
			}

			// get output
			Object[] outputs = interpreter.getOutput();

			fn = polynomial2(testPoints[i]);

			/* compute error */
			diff += FastMath.abs((Double) outputs[0] - fn);
			// state.output.message("current input: " + inputs[0]);
			// state.output.message("current output: " + outputs[0]);
			// state.output.message("real value: " + fn);
		}
		((FitnessCGP) individual.fitness).setFitness(state, (float) diff,
				diff <= 0.01); // stop

		individual.evaluated = true;
	}

	public static double polynomial2(double x) {
		return x * x - 2 * x + 1;
	}

	// load something from the parameters if necessary
	public void setup(EvolutionState state, Parameter base) {
		super.setup(state, base);

		Parameter def = defaultBase();

		state.output.exitIfErrors();
	}

}
