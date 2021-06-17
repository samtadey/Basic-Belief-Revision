/**
 * 
 */
package distance.revision;

/**
 * @author sam_t
 *
 */
public class TriangleInequalityResponse {

	private TriangleInequalityOperator op;
	private double modifier;
	
	public TriangleInequalityResponse(TriangleInequalityOperator op, double op_val) {
		this.setOp(op);
		this.setModifier(op_val);
	}

	public TriangleInequalityOperator getOp() {
		return op;
	}

	public void setOp(TriangleInequalityOperator op) {
		this.op = op;
	}


	public double getModifier() {
		return modifier;
	}

	public void setModifier(double modifier) {
		this.modifier = modifier;
	}
	
	
	//positives only right now
	//if that
	//there will be much more to the logic of this method
	public double handleTriangleInequality(double oldval, double next_avail) {
		//System.out.println("oldval: " + oldval + ", invalid: " + invalid);
		//definitely not working properly
		switch (this.op)
		{
			case VAL_UNCHANGE:
				return oldval;
			case NEXT_AVAILABLE:
				return next_avail;
//			case NEXT_VALID:
//				//check that the new value is not less than the previous value
//				if (invalid - this.modifier > oldval)
//					return invalid - this.modifier;
//				else 
//					return oldval;
			default:
				return oldval;
		}
		
	}

	
}
