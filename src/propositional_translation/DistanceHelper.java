/**
 * 
 */
package propositional_translation;

import language.BeliefState;
import language.State;

/**
 * @author sam_t
 *
 */
public class DistanceHelper {
	
	
	public static int getDistanceHamming(State s1, State s2) {
		int dist = 0;
		
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
	
	public static int getMinDistanceHamming(BeliefState b1, State s) {
		int min, curr;
		
		if (b1.getBeliefs().size() < 1)
			return -1;
		
		min = getDistanceHamming(b1.getBeliefs().get(0), s);
		
		for (State bstate: b1.getBeliefs())
		{
			curr = getDistanceHamming(bstate, s);
			if (curr < min)
				min = curr;
		}
		
		return min;
	}
}
