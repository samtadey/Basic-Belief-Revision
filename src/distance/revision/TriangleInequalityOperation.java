/**
 * 
 */
package distance.revision;

/**
 * @author sam_t
 *
 */
public class TriangleInequalityOperation {

	
	/**
	 * 
	 * @param oldval
	 * @param invalid must be the min value that triggers triangle inequality
	 * @param mod
	 * @param op
	 * @return
	 */
	public static double handleTriangleInequality(double oldval, double invalid, double mod, TriangleInequalityOperator op) {
		
		switch (op)
		{
			case VAL_UNCHANGE:
				return oldval;
			case NEXT_VALID:
				if (invalid - mod > oldval)
					return invalid - mod;
				else 
					return oldval;
			default:
				return oldval;
		}
		
	}
	
}
