/**
 * 
 */
package monniasza.tradeoffs.generator;

import java.util.function.Consumer;

import monniasza.tradeoffs.TradeoffsFamily;

/**
 * @author oskar
 * Genereates a set of valid values from Tradeoffs objects
 */
public class TradeoffsGenerator {
	private TradeoffsGenerator() {}
	/**
	 * Runs the action for all calculated trade-off options
	 * @param <E> type of output
	 * @param family the Tradeoffs family
	 * @param mapper function, which converts arrays of choices into final objects
	 * @param action action, which will run for every option
	 */
	public static <E> void consumeTradeoffs(TradeoffsFamily<E> family, Mapper<E> mapper, Consumer<E> action) {
		
		
		consumeTradeoffsEngine(family, mapper, action, new Comparable[family.elements()], 0, family.min, family.max);
	}
	/**
	 * Internal implementation of consumeTradeoffs();
	 * @param <E> type of output
	 * @param family the Tradeoffs family
	 * @param mapper function, which converts arrays of choices into final objects
	 * @param action action, which will run for every option
	 * @param iters iteration aid
	 * @param depth depth of recursion
	 * @param lowerBracket lowest score remaining
	 * @param upperBracket highest score remaining
	 */
	private static <E> void consumeTradeoffsEngine(TradeoffsFamily<E> family, Mapper<E> mapper, Consumer<E> action, Comparable<?>[] iters, int depth, int lowerBracket, int upperBracket) {
		//Truncate the barckets
		
		//Calculate boundaries
		int lowerBoundary = family.min(depth);
		int upperBoundary = family.max(depth);
		if(lowerBoundary >= upperBracket) return; //Minimum score is too high
		if(upperBoundary <= lowerBracket) return; //Maximum score is too low
		
		boolean isFinal = depth == family.elements()-1;
		for(int i = lowerBracket; i < upperBracket; i++) {
			iters[i] = family.tradeoffs.choose(depth, i);
			if(isFinal) {
				
			}else {
				consumeTradeoffsEngine(family, mapper, action, iters, depth+1, lowerBracket-i, upperBracket-i);
			}
		}
	}
}
