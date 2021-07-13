/**
 * 
 */
package ca.bcit.tadey.revision.trust.constraint;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.trust.DistanceMap;

/**
 * A response to Triangle Inequality validation errors. This implementation of Triangle Inequality Handling simply resets the value 
 * to the value before any attempted change was made
 * 
 * @author sam_t
 */
public class TriangleInequalityResponseNoChange extends TriangleInequalityResponse {

	/**
	 * @param op
	 */
	public TriangleInequalityResponseNoChange(TriangleInequalityOperator op) {
		super(op);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Handle Triangle Inequality by returning the old value as the value to be set
	 */
	@Override
	public double handleTriangleInequality(State s1, State s2, BeliefState invalid, DistanceMap map, double current_val, double new_val) {
		return current_val;
	}

}
