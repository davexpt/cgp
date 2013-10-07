package cgp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import ec.EvolutionState;
import ec.Evolve;
import ec.Individual;
import ec.cgp.eval.CGPSteppableInterpreter;
import ec.cgp.genome.CGPIndividual;
import ec.simple.SimpleStatistics;
import ec.util.Output;
import ec.util.ParameterDatabase;

/**
 * Simple test run for the developed implementation of CGP integrated in ECJ.
 * These tests are no exhaustive but rather for debugging purposes as this
 * library has just started being developed.
 * 
 * @author Davide Nunes
 * 
 */
public class ExternalEvoTest {

	@Test
	public void test() throws FileNotFoundException, IOException {

		File parameterFile = new File(Thread.currentThread()
				.getContextClassLoader().getResource("cgp.params")
				.getPath().toString());

		assertTrue(parameterFile.exists());

		ParameterDatabase dbase = new ParameterDatabase(parameterFile,
				new String[] { "-file", parameterFile.getCanonicalPath() });

		assertNotNull(dbase);

		Output out = Evolve.buildOutput();

		EvolutionState evaluatedState = Evolve.initialize(dbase, 0, out);
		evaluatedState.startFresh();
		int result = EvolutionState.R_NOTDONE;

		while (result == EvolutionState.R_NOTDONE) {
			result = evaluatedState.evolve();
		}

		Individual[] inds = ((SimpleStatistics) (evaluatedState.statistics))
				.getBestSoFar();

		CGPSteppableInterpreter interpreter = new CGPSteppableInterpreter();
		interpreter.load((CGPIndividual) inds[0]);
		evaluatedState.output.message(interpreter.getExpression());

		// System.out.println("Testing a single individual now");
		// // test individual
		// Object[] inputs = new Object[] { 0.63493156, 1.0 };
		//
		// interpreter.load((CGPIndividual) inds[0]);
		//
		// while (!interpreter.finished()) {
		// interpreter.step(inputs);
		// }
		// Object[] outputs = interpreter.getOutput();
		//
		// evaluatedState.output.message(outputs[0].toString());
		// evaluatedState.output.message(interpreter.getExpression());

		Evolve.cleanup(evaluatedState);
	}

}
