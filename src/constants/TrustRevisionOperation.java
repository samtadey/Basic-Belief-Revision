/**
 * 
 */
package constants;

/**
 * @author sam_t
 *
 */
public class TrustRevisionOperation {

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
