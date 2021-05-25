/**
 * 
 */
package constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sam_t
 *
 */
public final class PropositionalSymbols {
	
	public static final char AND = '&';
	public static final char OR = '|';
	public static final char SPACE = ' ';
	public static final char NEGATION = '~';
	
	//
	Set<Character> symbols = new HashSet<Character>(Arrays.asList(AND, OR, SPACE, NEGATION));
}
