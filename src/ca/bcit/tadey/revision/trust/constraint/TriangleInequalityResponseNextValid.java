/**
 * 
 */
package ca.bcit.tadey.revision.trust.constraint;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.trust.DistanceMap;

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
