/**
 * 
 */
package distance;

import java.util.Set;

import language.State;

/**
 * @author sam_t
 *
 */
public abstract class Distance {

	Set<Character> propvars;
	
	public Distance(Set<Character> vars) {
		this.propvars = vars;
	}
		
	public abstract int getDistance(State s1, State s2);
}
