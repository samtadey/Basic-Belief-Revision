/**
 * 
 */
package distance.revision;

import distance.DistanceMap;
import language.BeliefState;
import language.State;

/**
 * @author sam_t
 *
 */
public class TriangleInequalityResponseNextValid extends TriangleInequalityResponse {

	/**
	 * @param op
	 */
	public TriangleInequalityResponseNextValid(TriangleInequalityOperator op) {
		super(op);
	}

	@Override
	public double handleTriangleInequality(State s1, State s2, BeliefState invalid, DistanceMap map, double current_val, double new_val) {
		if (new_val > current_val)
			return findMaxAvailValue(s1,s2,invalid,map);
		return findMinAvailValue(s1,s2,invalid,map);
	}
	
	
	
	/**
	 * Iterates through all states in the beliefstate that would become invalid by triangle inequality. Produces the max value that 
	 * satisfies triangle inequality for all states in b
	 * 
	 * MinMax
	 * 
	 * @param s
	 * @param u
	 * @param b
	 * @return
	 */
	private double findMaxAvailValue(State s, State u, BeliefState b, DistanceMap map) {
		double min = Double.MAX_VALUE, current;
		
		for (State t : b.getBeliefs())
		{
			current = map.getDistance(s, t) + map.getDistance(t, u);
			System.out.print(current + ",");
			if (current < min)
				min = current;
		}
		System.out.println();
		
		return min;
	}
	
	/**
	 * Desc
	 * 
	 * MaxMin
	 * 
	 * @param s
	 * @param u
	 * @param b
	 * @return
	 */
	private double findMinAvailValue(State s, State u, BeliefState b, DistanceMap map) {
		double max = 0, current;
		
		for (State t : b.getBeliefs())
		{

			current = Math.abs(map.getDistance(s, t) - map.getDistance(t, u));
			System.out.print(current + ",");
			if (current > max)
				max = current;
		}
		System.out.println();
		return max;
	}

}
