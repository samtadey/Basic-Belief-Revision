/**
 * 
 */
package constants;


import java.util.Map;

import distance.constraint.TriangleInequalityOperator;
import distance.revision.RevisionOperator;


/**
 * @author sam_t
 *
 */
public class UIToOperatorPairs {

	public static final Map<String, TriangleInequalityOperator> triangle_ineq 
		= Map.of(Strings.constr_value_unchange, TriangleInequalityOperator.NO_CHANGE, 
				Strings.constr_next_avail, TriangleInequalityOperator.NEXT_VALID,
				Strings.constr_minimax_dist, TriangleInequalityOperator.MINI_MAX_DIST);


	public static final Map<String, RevisionOperator> revision
		= Map.of(Strings.revision_general, RevisionOperator.GENERAL,
				Strings.revision_naive, RevisionOperator.NAIVE);
	
}
