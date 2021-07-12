/**
 * 
 */
package ca.bcit.tadey.revision.constants;


import java.util.Map;

import ca.bcit.tadey.revision.RevisionOperator;
import ca.bcit.tadey.revision.trust.constraint.TriangleInequalityOperator;


/**
 * The UIToOperatorPairs class maps String constant's used by users to enum values used throughout the system. 
 * 
 * @author sam_t
 */
public class UIToOperatorPairs {

	/*
	 * A Map of values used to handle triangle inequality
	 */
	public static final Map<String, TriangleInequalityOperator> triangle_ineq 
		= Map.of(Strings.constr_value_unchange, TriangleInequalityOperator.NO_CHANGE, 
				Strings.constr_next_avail, TriangleInequalityOperator.NEXT_VALID,
				Strings.constr_minimax_dist, TriangleInequalityOperator.MINI_MAX_DIST);

	
	/*
	 * A Map of values used for the types of Belief Revision
	 */
	public static final Map<String, RevisionOperator> revision
		= Map.of(Strings.revision_general, RevisionOperator.GENERAL,
				Strings.revision_naive, RevisionOperator.NAIVE);
	
}
