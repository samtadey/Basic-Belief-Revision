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
public class TriangleInequalityResponseMiniMaxDist extends TriangleInequalityResponse {

	/**
	 * @param op
	 */
	public TriangleInequalityResponseMiniMaxDist(TriangleInequalityOperator op) {
		super(op);
	}
	
	

	@Override
	public double handleTriangleInequality(State s1, State s2, BeliefState invalid, DistanceMap map, double current_val,
			double new_val) {
		//I believe invalid will be unused
		
		if (new_val > current_val)
			return findMaxAvailValue(s1,s2,map.getPossibleStates(), map);
		return findMinAvailValue(s1,s2,map.getPossibleStates(), map);
		
		
	}
	


}
