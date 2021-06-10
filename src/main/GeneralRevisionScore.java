/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;

import distance.RankingState;
import language.BeliefState;
import language.State;

/**
 * @author sam_t
 *
 */
public class GeneralRevisionScore {
	
	private final double DEFAULT_SCORE = Double.MAX_VALUE;

	private HashMap<State, Double> scoremap;
	
	public GeneralRevisionScore() {
		scoremap = new HashMap<State, Double>();
	}
	
	public GeneralRevisionScore(BeliefState validstates) {
		scoremap = new HashMap<State, Double>();
		
		for (State s : validstates.getBeliefs())
			scoremap.put(s, DEFAULT_SCORE);
	}
	
	public double getScore(State s) {
		return scoremap.get(s);
	}
	
	public void setScore(State s, double score) {
		scoremap.put(s, score);
	}
	
	
	public HashMap<State, Double> getScoreMap() {
		return scoremap;
	}
	
	
	public RankingState scoreToRank(ArrayList<Character> vocab) {
		return new RankingState(scoreToBeliefState(), vocab);
	}
	
	//return min states
	public BeliefState scoreToBeliefState() {
		BeliefState beliefs = new BeliefState();
		double curr, min = -1;
		
		//find min
		for (double val : scoremap.values())
		{
			curr = val;
			if (min == -1 || curr < min)
				min = curr;
		}
		
		//add min states to belief state
		for (State s: scoremap.keySet())
		{
			if (scoremap.get(s) == min)
				beliefs.addBelief(s);
		}
			
		return beliefs;
	}
	
}
