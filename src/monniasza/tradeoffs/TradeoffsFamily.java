/**
 * 
 */
package monniasza.tradeoffs;

import java.lang.reflect.Array;

/**
 * @author oskar
 *
 */
public class TradeoffsFamily<E> {
	/**
	 * The Tradeoffs domain
	 */
	public final Tradeoffs<E> tradeoffs;
	public final int min;
	public final int max;
	public final int best;
	public final int worst;
	private final int[] mins;
	private final int[] maxs;
	TradeoffsFamily(Builder<E> builder) {
		tradeoffs = builder.tradeoffs;
		min = builder.minn;
		max = builder.maxx;
		int min0 = min;
		int max0 = max;
		mins = builder.mins;
		maxs = builder.maxs;
		for(int i = 0; i < builder.mins.length; i++) {
			min0 += mins[i];
			max0 += maxs[0];
		}
		best = max0;
		worst = min0;
	}
	
	/**
	 * @return number of elements
	 */
	public int elements() {
		return mins.length;
	}
	/**
	 * Retrieves a minimum value
	 * @param element element index
	 * @return minimum choice of an element
	 * @throws ArrayIndexOutOfBoundsException if element < 0 OR element >= elements()
	 */
	public int min(int element) {
		return mins[element];
	}
	/**
	 * Retrieves a maximum value
	 * @param element element index
	 * @return maximum choice of an element
	 * @throws ArrayIndexOutOfBoundsException if element < 0 OR element >= elements()
	 */
	public int max(int element) {
		return maxs[element];
	}
	/**
	 * Copies an array of minimums
	 * @param dump target array
	 * @return the provided array
	 * @throws ArrayIndexOutOfBoundsException when array is shorter than elements();
	 */
	public int[] downloadMins(int[] dump) {
		System.arraycopy(mins, 0, dump, 0, mins.length);
		return dump;
	}
	/**
	 * Copies an array of maximums
	 * @param dump target array
	 * @return the provided array
	 * @throws ArrayIndexOutOfBoundsException when array is shorter than elements();
	 */
	public int[] downloadMaxs(int[] dump) {
		System.arraycopy(maxs, 0, dump, 0, mins.length);
		return dump;
	}
	/**
	 * Copies a mins arrays into a new array
	 * @return a new array
	 */
	public int[] mins() {
		return mins.clone();
	}
	/**
	 * Copies a maxs arrays into a new array
	 * @return a new array
	 */
	public int[] maxs() {
		return maxs.clone();
	}
	
	/**
	 * A Tradeoffs family builder, obtained by {@link Tradeoffs#newFamily()}
	 * @param <E>
	 * @author oskar
	 */
	public static class Builder<E>{
		private final Tradeoffs<E> tradeoffs;
		private final int[] mins;
		private final int[] maxs;
		private int minn;
		private int maxx;
		/**
		 * Creates a builder
		 * @param tradeoffs domain
		 */
		public Builder(Tradeoffs<E> tradeoffs) {
			this.tradeoffs = tradeoffs;
			int n  = tradeoffs.elements();
			int[] min0 = new int[n];
			int[] max0 = new int[n];
			for(int i = 0; i < n; i++) {
				max0[i] = tradeoffs.lengthArrays(n);
			}
			mins = min0;
			maxs = max0;
			int max1 = 0;
			for(int i = 0; i < n; i++){
				max1 += tradeoffs.lengthArrays(i);
			}
			minn = 0;
			maxx = max1 - n + 1;
		}
		/**
		 * Copies a family into a builder
		 * @param tradeoffs family
		 */
		public Builder(TradeoffsFamily<E> tradeoffs) {
			this.tradeoffs = tradeoffs.tradeoffs;
			mins = tradeoffs.mins.clone();
			maxs = tradeoffs.maxs.clone();
			minn = tradeoffs.min;
			maxx = tradeoffs.max;
		}
		
		//Modifiers
		/**
		 * Require that given element be an exact value
		 * @param element element to adjust
		 * @param value value to set
		 * @return this
		 * @throw ArrayIndexOutOfBoundsException if element < 0 OR element >= length() OR value < 0 OR value > lengthArrays(element)
		 */
		public Builder<E> elementExact(int element, int value){
			return elementRange(element, value, value+1);
		}
		/**
		 * Require that given element be higher or equal
		 * @param element element to adjust
		 * @param value value to set
		 * @return this
		 * @throw ArrayIndexOutOfBoundsException if element < 0 OR element >= length() OR value < 0 OR value > lengthArrays(element)
		 */
		public Builder<E> elementHigher(int element, int value){
			return elementRange(element, value, tradeoffs.lengthArrays(element));
		}
		/**
		 * Require that given element be lower
		 * @param element element to adjust
		 * @param value value to set
		 * @return this
		 * @throw ArrayIndexOutOfBoundsException if element < 0 OR element >= length() OR value < 0 OR value > lengthArrays(element)
		 */
		public Builder<E> elementLower(int element, int value){
			return elementRange(element, 0, value);
		}
		/**
		 * Require that given element be within range [min, max)
		 * @param element element to adjust
		 * @param min minimum value
		 * @param max maximum value
		 * @return this
		 * @throw ArrayIndexOutOfBoundsException
		 * if element < 0 OR element >= length()
		 * @throw IllegalArgumentException
		 * if min < 0 OR min >= lengthArrays(element)
		 * OR max < 0 OR max >= lengthArrays(element)
		 */
		public Builder<E> elementRange(int element, int min, int max){
			if(min < 0) throw new IllegalArgumentException("min < 0: "+min);
			if(max < 0) throw new IllegalArgumentException("max < 0: "+max);
			int l = tradeoffs.lengthArrays(element);
			if(min > l) throw new IllegalArgumentException("min > length: "+min+", length: "+l);
			if(max > l) throw new IllegalArgumentException("max > length: "+max+", length: "+l);
			mins[element] = min;
			maxs[element] = max;
			return this;
		}
		
		/**
		 * Require that the sum will be a specific value
		 * @param value value to set
		 * @return this
		 */
		public Builder<E> totalExact(int value){
			minn = value;
			maxx = value;
			return this;
		}
		/**
		 * Require that the sum will be higher or equal than some value
		 * @param value minimum value
		 * @return this
		 */
		public Builder<E> totalHigher(int value){
			minn = value;
			return this;
		}
		/**
		 * Require that the sum will be lower than some value
		 * @param value maximum value
		 * @return this
		 */
		public Builder<E> totalLower(int value){
			maxx = value;
			return this;
		}
		/**
		 * Require that the sum will be within range be within range [min, max)
		 * @param min minimum value
		 * @param max maximum value
		 * @return this
		 */
		public Builder<E> totalRange(int min, int max){
			this.minn = min;
			this.maxx = max;
			return this;
		}
		
		/**
		 * Completes the construction. Do not reuse builders.
		 * @return new family
		 */
		public TradeoffsFamily<E> finish() {
			return new TradeoffsFamily<>(this);
		}
	}
}
