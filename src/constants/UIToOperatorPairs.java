/**
 * 
 */
package constants;

import java.util.HashMap;
import java.util.Map;

import distance.revision.TriangleInequalityOperator;

/**
 * @author sam_t
 *
 */
public class UIToOperatorPairs {

	public static final Map<String, TriangleInequalityOperator> triangle_ineq 
		= Map.of(Strings.constr_value_unchange, TriangleInequalityOperator.VAL_UNCHANGE, 
				Strings.constr_next_avail, TriangleInequalityOperator.NEXT_VALID);

	
}
