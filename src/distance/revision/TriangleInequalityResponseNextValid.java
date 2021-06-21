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
	
	
	
}
