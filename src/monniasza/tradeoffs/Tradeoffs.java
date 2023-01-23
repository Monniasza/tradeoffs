/**
 * 
 */
package monniasza.tradeoffs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A Tradeoffs domain.
 * A domain stores all combinations of choices
 * @author oskar
 * @param <E> type of output
 */
public class Tradeoffs<E> {
	private final Comparable<?>[][] arrays;
	
	/**
	 * Creates a Tradeoffs domain using data
	 * @param data  
	 */
	public Tradeoffs(Collection<? extends Comparable<?>[]> data) {
		arrays = data.stream().map(Comparable[]::clone).toArray(l -> new Comparable<?>[l][]);
	}
	
	/**
	 * Converts this Tradeoffs domain into a builder
	 * @return a new builder
	 */
	public Tradeoff<E> builder() {
		return new Tradeoff<>(Arrays.asList(arrays));
	}
	
	//Access functions
	/**
	 * Gets an element
	 * @param element index of an element
	 * @throws ArrayIndexOutOfBoundsException when element < 0 or element >= elements();
	 * @return a list describing an element
	 */
	public List<Comparable<?>> getArrays(int element){
		return Collections.unmodifiableList(Arrays.asList(arrays[element]));
	}
	/**
	 * Copies the element into an array
	 * @param <T> type of an array
	 * @param element index of an element
	 * @param dump target array
	 * @return the provided array
	 * @throws ArrayStoreException if T is not a supertype of the array contents
	 * @throws ArrayIndexOutOfBoundsException when array is shorter than lengthArrays(element) OR element < 0 OR element >= elements()
	 */
	public <T> T[] downloadArrays(int element, T[] dump) {
		Comparable<?>[] array = arrays[element];
		System.arraycopy(array, 0, dump, 0, array.length);
		return dump;
	}
	/**
	 * Copies an element into a new array
	 * @param element index of an element
	 * @return a new array
	 * @throws ArrayIndexOutOfBoundsException when element < 0 OR element >= elements()
	 */
	public Comparable<?>[] cloneArrays(int element){
		return arrays[element].clone();
	}
	
	/**
	 * @return number of elements
	 */
	public int elements() {
		return arrays.length;
	}
	/**
	 * Retrieves a number of choices
	 * @param element element index
	 * @return number of choices in an element
	 * @throws ArrayIndexOutOfBoundsException if element < 0 OR element >= elements()
	 */
	public int lengthArrays(int element) {
		return arrays[element].length;
	}
	/**
	 * Retrieves a choice
	 * @param element element index
	 * @param choice choice index
	 * @return a choice
	 * @throws ArrayIndexOutOfBoundsException if element < 0 OR element >= elements() OR choice < 0 OR choice > lengthArrays(element)
	 */
	public Comparable<?> choose(int element, int choice) {
		return arrays[element][choice];
	}
	
	/**
	 * Creates a builder for this domain
	 * @return a new builder
	 */
	public TradeoffsFamily.Builder<E> newFamily(){
		return new TradeoffsFamily.Builder<>(this);
	}
	
}
