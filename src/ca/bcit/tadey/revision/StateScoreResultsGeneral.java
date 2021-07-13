/**
 * 
 */
package ca.bcit.tadey.revision;

import ca.bcit.tadey.revision.state.BeliefState;

/**
 * GeneralRevision implementation of a StateScoreResult object. General revision is done by taking the sum of the rank and the minimum distance 
 * value to the revision states.
 * 
 * @author sam_t
 */
public class StateScoreResultsGeneral extends StateScoreResults {

	/**
	 * StateScoreResultsGeneral Constructor
	 */
	public StateScoreResultsGeneral(BeliefState sentences) {
		super(sentences);
	}

	/**
	 * Results for each state are found by finding the minimum distance to a revision state and summing that value
	 * with the the ranking value for the state.
	 * 
	 * Eg. 000: Rank 1
	 * 	   Distance values 000->001 = 2
	 *                     000->111 = 4
	 *                     
	 *                     Result 000: Rank(1) + Min Distance(2) = Result = 3
	 */
	@Override
	public void setResults() throws Exception {
		double min;
		
		for (StateScore ss : this.scores)
		{
			if (ss.getDistances().size() < 1)
				throw new Exception("StateScoreResultsGeneral: no sentences to revise by");
			
			
			min = ss.getDistances().get(0);
			
			for (int i = 1; i < ss.getDistances().size(); i++)
				if (ss.getDistances().get(i) < min)
					min = ss.getDistances().get(i);

			ss.setResult(min + ss.getRank());
		}
	}

}
