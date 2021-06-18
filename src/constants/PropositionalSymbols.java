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
	
	public static final String AND = "&";
	public static final String OR = "|";
	public static final String SPACE = " ";
	public static final String NEGATION = "~";
	public static final String IMPLICATIONP1 = "-";
	public static final String IMPLICATIONP2 = ">";
	public static final String L_PARENTHESIS = "(";
	public static final String R_PARENTHESIS = ")";
	
	//
	public static final Set<String> symbols = new HashSet<String>(Arrays.asList(AND, OR, SPACE, NEGATION, IMPLICATIONP1, IMPLICATIONP2, L_PARENTHESIS, R_PARENTHESIS));
}
