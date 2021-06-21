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
public abstract class TriangleInequalityResponse {

	private TriangleInequalityOperator op;
	//private double modifier;
	
	
	public TriangleInequalityResponse(TriangleInequalityOperator op) {
		this.setOp(op);
		//this.setModifier(op_val);
	}

	public TriangleInequalityOperator getOp() {
		return op;
	}

	public void setOp(TriangleInequalityOperator op) {
		this.op = op;
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
	protected double findMaxAvailValue(State s, State u, BeliefState b, DistanceMap map) {
		double min = Double.MAX_VALUE, current;
		
		for (State t : b.getBeliefs())
		{
			if (!s.equals(t) && !u.equals(t))
			{
				current = map.getDistance(s, t) + map.getDistance(t, u);
				//System.out.print(current + ",");
				if (current < min)
					min = current;
			}
		}
		//System.out.println();
		
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
	protected double findMinAvailValue(State s, State u, BeliefState b, DistanceMap map) {
		double max = 0, current;
		
		for (State t : b.getBeliefs())
		{
			if (!s.equals(t) && !u.equals(t))
			{
				current = Math.abs(map.getDistance(s, t) - map.getDistance(t, u));
				System.out.print(current + ",");
				if (current > max)
					max = current;
			}
		}
		System.out.println();
		return max;
	}
	
	
	public abstract double handleTriangleInequality(State s1, State s2, BeliefState invalid, DistanceMap map, double current_val, double new_val);

}
