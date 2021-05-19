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
public class HammingDistance extends Distance {

	public HammingDistance(Set<Character> vars) {
		super(vars);
	}

	public double getDistance(State s1, State s2) {
		double dist = 0;
		
    	if (s1.getState().length() != s2.getState().length())
    	{
    		System.out.println("Error: States are not equal length");
    		return -1;
    	}
    	
    	for (int i = 0; i < s1.getState().length(); i++)
    		if (s1.getState().charAt(i) != s2.getState().charAt(i))
    			dist++;
    	
    	return dist;
	}
	
}
