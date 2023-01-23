/**
 * 
 */
package monniasza.tradeoffs.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

/**
 * @author oskar
 * A caching and mapping function used by domains and families (for the generator).
 * The mapper features a configurable map factory for optional thread safety.
 * The function must cast the arguments individually to work
 * @param <E> type of return values
 */
public class Mapper<E> implements Function<@Nonnull List<Comparable<?>>, E>{
	/**
	 * Creates a non-thread-safe mapper
	 * @param f mapping function
	 */
	public Mapper(Function<@Nonnull List<Comparable<?>>, E>  f) {
		this(f, HashMap::new);
	}

	private final Map<@Nonnull List<Comparable<?>>, E> map;
	private final Function<@Nonnull List<Comparable<?>>, E> f;
	
	/**
	 * Creates a mapper, whose thread safety and performance is dictated by a map
	 * @param f mapping function
	 * @param ctor the map factory method
	 */
	public Mapper(Function<@Nonnull List<Comparable<?>>, E> f, Supplier<Map<@Nonnull List<Comparable<?>>, E>> ctor) {
		map = ctor.get();
		this.f = f;
	}

	@Override
	public E apply(List<Comparable<?>> t) {
		return map.computeIfAbsent(t, f);
	}
}
