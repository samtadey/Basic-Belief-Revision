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
public class TriangleInequalityResponseIgnore extends TriangleInequalityResponse {

	/**
	 * @param op
	 */
	public TriangleInequalityResponseIgnore(TriangleInequalityOperator op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double handleTriangleInequality(State s1, State s2, BeliefState invalid, DistanceMap map, double current_val,
			double new_val) {
		// TODO Auto-generated method stub
		return new_val;
	}

}
