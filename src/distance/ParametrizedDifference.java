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
public class ParametrizedDifference extends Distance {
	
	private HashMap<Character, Double> weights;
	
	public ParametrizedDifference(Set<Character> vars, HashMap<Character, Double> weights) {
		super(vars);
		this.weights = weights;
	}

	@Override
	public double getDistance(State s1, State s2) {  
		
		if (s1.getState().length() != s2.getState().length())
		{
			System.out.println("States not same length");
			return -1;
		}
		
		//all variables must be the same size, the size of the states
		if (s1.getState().length() != this.weights.size() && s1.getState().length() != super.propvars.size())
		{
			System.out.println("Variable Set not same length as States");
			return -1;
		}
		
		//levels are as such
		//B = 1
		//C = 2
		//D = 3
		//A = 4
		//weights are assigned to the hashmap in preprossessing
		
		int i = 0;
		double dist = 0;
		for (Character c: super.propvars)
		{
			if (s1.getState().charAt(i) != s2.getState().charAt(i))
				dist += this.weights.get(c);
			i++;
		}
		
		
		return dist;
	}

}
