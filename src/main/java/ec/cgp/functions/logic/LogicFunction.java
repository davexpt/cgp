package ec.cgp.functions.logic;

import ec.cgp.functions.AbstractFunction;




public abstract class LogicFunction extends AbstractFunction<Boolean> {

	public Double evaluateDouble(Object[] args) {
		Boolean result = evaluate(args);
		return result ? 1.0 : 0.0;
	}

	public Class<Boolean> getType() {
		return Boolean.class;
	}
}
