package cgp.functions;

import static org.junit.Assert.*;

import org.junit.Test;

import ec.cgp.functions.numeric.AddFn;
import ec.cgp.functions.numeric.DivFn;
import ec.cgp.functions.numeric.MulFn;
import ec.cgp.functions.numeric.SubFn;

/**
 * JUnit test class to test out a couple of CGP functions
 * 
 * @author Davide Nunes
 * 
 */
public class TestFunctions {

	@Test
	public void testAdd() {
		AddFn fn = new AddFn();
		double arg0 = 1;
		double arg1 = 2.5;

		double result = fn.evaluate(new Object[] { arg0, arg1 });
		assertTrue(result == 3.5);
	}

	@Test
	public void testSub() {
		SubFn fn = new SubFn();
		double arg0 = 1;
		double arg1 = 2.5;

		double result = fn.evaluate(new Object[] { arg0, arg1 });
		assertTrue(result == -1.5);
	}

	@Test
	public void testMul() {
		MulFn fn = new MulFn();
		double arg0 = 1;
		double arg1 = 2.5;

		double result = fn.evaluate(new Object[] { arg0, arg1 });
		assertTrue(result == 2.5);
	}

	@Test
	public void testDiv() {
		DivFn fn = new DivFn();
		double arg0 = 1;
		double arg1 = 2.5;

		double result = fn.evaluate(new Object[] { arg0, arg1 });
		assertTrue(result == (1 / 2.5));

		double arg2 = 0;

		result = fn.evaluate(new Object[] { arg0, arg2 });

		assertTrue(result == 1);
	}
}
