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


//	public double getModifier() {
//		return modifier;
//	}
//
//	public void setModifier(double modifier) {
//		this.modifier = modifier;
//	}
	
	
	public abstract double handleTriangleInequality(State s1, State s2, BeliefState invalid, DistanceMap map, double current_val, double new_val);

}
