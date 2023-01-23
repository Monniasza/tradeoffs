/**
 * 
 */
package monniasza.tradeoffs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

/**
 * @author oskar
 * A builder for Tradeoffs domain
 * @param <E> type of output
 */
public class Tradeoff<E> {
	@Nonnull private final ArrayList<Comparable<?>[]> arrays = new ArrayList<>();
	
	/** Create an empty Tradeoffs builder */
	public Tradeoff() {}
	
	/**
	 * Creates a Tradeoffs builder from a collection.
	 * The collections should guarantee an order
	 * @param data data collections
	 */
	public Tradeoff(Collection<? extends Comparable<?>[]> data) {
		arrays.addAll(data);
	}
	
	/**
	 * Declares an element. Order of both the element and elements is kept
	 * @param elements choices for an element
	 * @return this
	 */
	@Nonnull public Tradeoff<E> element(Comparable<?>... elements) {
		arrays.add(Arrays.copyOf(elements, elements.length));
		return this;
	}
	
	/**
	 * Completes the construction. Do not reuse builders.
	 * @return new domain
	 */
	@Nonnull public Tradeoffs<E> finish() {
		return new Tradeoffs<>(arrays);
	}
}
