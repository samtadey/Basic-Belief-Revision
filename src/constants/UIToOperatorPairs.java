/**
 * 
 */
package constants;


import java.util.Map;

import distance.revision.TriangleInequalityOperator;


/**
 * @author sam_t
 *
 */
public class UIToOperatorPairs {

	public static final Map<String, TriangleInequalityOperator> triangle_ineq 
		= Map.of(Strings.constr_value_unchange, TriangleInequalityOperator.NO_CHANGE, 
				Strings.constr_next_avail, TriangleInequalityOperator.NEXT_VALID);


}
