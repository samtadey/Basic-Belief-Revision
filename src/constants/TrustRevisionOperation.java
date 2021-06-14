/**
 * 
 */
package constants;

/**
 * @author sam_t
 *
 * TrustRevisionOperation uses a TrustRevisionOperator to perform a mathematical operation on two values.
 *
 */
public class TrustRevisionOperation {

	
	/*
	 * reviseValue returns a resultant value, combining two values with a mathematical operation, such as ADDITION, SUBTRACTION, MULTIPLICATION, etc.
	 * 
	 * @params
	 * 		double oldval: Value to perform operation on
	 * 		double modifier: Right-hand value being used as a modifer on the oldval
	 * 		TrustRevisionOperator op: Mathematical operation being used to combine the oldval and modifier into a result value.
	 * 
	 * @return
	 * 		double: new value produced by the oldvalue, modifier, and the type of operation
	 */
	public static double reviseValue(double oldval, double modifier, TrustRevisionOperator op) {
		
		double val;
		
		switch(op)
		{
			case ADDITION:
				val = oldval + modifier;
				break;
			case SUBTRACTION:
				val = oldval - modifier;
				break;
			case MULTIPLICATION:
				val = oldval * modifier;
				break;
			case DIVISION:
				val = oldval / modifier;
				break;
			default:
				val = 0;
				break;
		}
		
		return val;
	}
}
