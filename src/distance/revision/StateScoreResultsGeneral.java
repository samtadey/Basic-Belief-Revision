/**
 * 
 */
package distance.revision;

import language.BeliefState;

/**
 * @author sam_t
 *
 */
public class StateScoreResultsGeneral extends StateScoreResults {

	/**
	 * 
	 */
	public StateScoreResultsGeneral(BeliefState sentences) {
		super(sentences);
	}

	@Override
	public void setResults() throws Exception {
		double min;
		
		for (StateScore ss : this.scores)
		{
			if (ss.getDistances().size() < 1)
				throw new Exception("StateScoreResultsGeneral: no sentences to revise by");
			
			
			min = ss.getDistances().get(0);
			
			for (int i = 0; i < ss.getDistances().size(); i++)
				if (ss.getDistances().get(i) < min)
					min = ss.getDistances().get(i);

			ss.setResult(min + ss.getRank());
		}
		

	}

}
