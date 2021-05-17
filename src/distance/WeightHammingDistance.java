/**
 * 
 */
package distance;

import java.util.HashMap;
import java.util.Set;

import language.State;

/**
 * @author sam_t
 *
 */
public class WeightHammingDistance extends Distance {

	private HashMap<Character, Double> var_weights;
	
	public WeightHammingDistance(Set<Character> vars, HashMap<Character, Double> weights) {
		super(vars);
		this.var_weights = weights;
	}

	@Override
	public int getDistance(State s1, State s2) {
		
		if (s1.getState().length() != s2.getState().length())
		{
			System.out.println("States not same length");
			return -1;
		}
		
		//all variables must be the same size, the size of the states
		if (s1.getState().length() != this.var_weights.size() && s1.getState().length() != super.propvars.size())
		{
			System.out.println("Variable Set not same length as States");
			return -1;
		}
		
		//assuming the set and states are ordered in the same way?
		// [A, B, C](Set) or 011 (State)
		int i = 0;
		int dist = 0;
		for (Character c: super.propvars)
		{
			//
			if (s1.getState().charAt(i) != s1.getState().charAt(i))
				dist += this.var_weights.get(c);
			i++;
		}
		
		
		//dont like this
		//how will ordering work
		
		return 0;
	}

}
