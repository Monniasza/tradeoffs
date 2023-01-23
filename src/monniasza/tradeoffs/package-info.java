/**
 * 
 */
/**
 * Tradeoffs core library (without generator and selector) <br>
 * Used glossary:
 * <ul>
 * 	<li><b>Domain</b> - the independent set of all choices</li>
 * 	<li><b>Family</b> - the configurable subset of a domain</li>
 * 	<li><b>Mapper</b> - the function which converts arrays of choices into final results</li>
 * 	<li><b>Backmapper</b> - the function which back-converts results from a mapper back into arrays</li>
 * 	<li><b>Elements</b> - the list of tradeoff choices</li>
 * 	<li><b>Choice</b> - a choice, scored by index.</li>
 * </ul>
 * 
 * @author oskar
 */
@javax.annotation.ParametersAreNonnullByDefault
package monniasza.tradeoffs;

/*
<dependency>
		<groupId>com.google.guava</groupId>
		<artifactId>guava</artifactId>
		<version>31.1-jre</version>
	</dependency>
*/