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
