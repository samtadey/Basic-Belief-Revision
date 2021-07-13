/**
 * 
 */
package ca.bcit.tadey.revision;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;

/**
 * Sets score objects as the result of Naive Belief Revision
 * 
 * @author sam_t
 */
public class StateScoreResultsNaive extends StateScoreResults {

	private BeliefState beliefs;
	private double threshold;
	
	/**
	 * 
	 * @param sentences
	 * @param minrank
	 * @param threshold
	 */
	public StateScoreResultsNaive(BeliefState sentences, BeliefState minrank, double threshold) {
		super(sentences);
		this.beliefs = minrank;
		this.threshold = threshold;
	}

	
	/**
	 * Does Naive based revision on a belief set
	 */
	@Override
	public void setResults() throws Exception {
		double min;
		BeliefState partition = new BeliefState(), result;
		
		//iterate through all states
		for (StateScore ss : this.scores)
		{
			if (ss.getDistances().size() < 1)
				throw new Exception("StateScoreResultsGeneral: no sentences to revise by");
			
			min = ss.getDistances().get(0);
			
			//find min distance for each state
			for (int i = 1; i < ss.getDistances().size(); i++)
				if (ss.getDistances().get(i) < min)
					min = ss.getDistances().get(i);

			//if the min distance value is <= the threshold parameter
			//add to the partition set
			if (min <= this.threshold)
				partition.addBelief(ss.getState());
		}
		
		//I think there will always be one state at least
		//where ss and a sentence are the same state
		if (partition.getBeliefs().size() < 1)
			throw new Exception("No states are accepted by partition threshold value");
		
		//do hamming belief revision on initial beliefs and the partition set
		result = hammRevise(this.beliefs, partition);
		
		//resulting states are selected for revision
		//revised states are given the best possible score in the 
		//StateScore object: 0
		//these states will be selected by the revise method since they have the min score
		for (State s: result.getBeliefs())
		{
			for (StateScore ss : this.scores)
				if (s.equals(ss.getState()))
				{
					ss.setResult(0);
					break;
				}
		}
	}
	
	/**
	 * Hamming Revision between two BeliefStates
	 * 
	 * @param beliefs
	 * @param partition
	 * @return
	 * @throws Exception
	 */
	private BeliefState hammRevise(BeliefState beliefs, BeliefState partition) throws Exception {
		
		BeliefState result = new BeliefState();
		double min = Double.MAX_VALUE, val;
		double[] minvals = new double[partition.getBeliefs().size()];
		int i;
		
		//find min val
		for (i = 0; i < partition.getBeliefs().size(); i++)
		{
			val = minDistance(beliefs, partition.getBeliefs().get(i));
			if (val < min)
				min = val;
    		minvals[i] = val;
		}
		
		//go through 
		for (i = 0; i < partition.getBeliefs().size(); i++)
			//add belief to result
			if (minvals[i] == min)
				result.addBelief(partition.getBeliefs().get(i));

		
		return result;
	}
	
	/**
	 * Finds the min distance from a partition state to all beliefs
	 * 
	 * @param beliefs
	 * @param partition
	 * @return
	 * @throws Exception
	 */
	private double minDistance(BeliefState beliefs, State partition) throws Exception {
		double min = Double.MAX_VALUE, diff;
		
		for (State s : beliefs.getBeliefs())
		{
			diff = stateDiff(s, partition);
			if (diff < min)
				min = diff;
		}
		
		return min;
	}
	
	
	/**
	 * Finds the Hamming Distance between two State objects
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 * @throws Exception
	 */
	private double stateDiff(State s1, State s2) throws Exception {
		if (s1.getState().length() != s1.getState().length())
			throw new Exception("States not comparable");
			
		double diff = 0;
		
		for (int i = 0; i < s1.getState().length(); i++)
			if (s1.getState().charAt(i) != s2.getState().charAt(i))
				diff++;
		
		return diff;
	}

}
