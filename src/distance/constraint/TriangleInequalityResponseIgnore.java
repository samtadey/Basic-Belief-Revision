/**
 * 
 */
package distance.constraint;

import distance.DistanceMap;
import language.BeliefState;
import language.State;

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
