/**
 * 
 */
package main;



import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import aima.core.logic.common.ParserException;
import distance.DistanceState;
import distance.RankingState;
import language.BeliefState;
import language.State;
import propositional_translation.InputTranslation;
import solver.DPLL;
import solver.FormulaSet;


/**
 * @author sam_t
 *
 */
public class BeliefRevision {
	
	/*
	 * Default Constructor
	 */
	public BeliefRevision() {}

	  

    
    //probss move to input processing class
    public static boolean verifyStateInput(String input) {    
	    for (String line : input.split("\n")) 
	    {
	    	for (int i = 0; i < line.length(); i++)
	    		if (line.charAt(i) != '0' || line.charAt(i) != '1')
	    			return false;
	    }
	    return true;
    }
    
    //does not accept digits
    //accepts all other characters
    public static boolean verifyPropFormulaInput(String input) {
    	//Character.is
    	
    	for (String line: input.split("\n"))
    	{
    		for (int i = 0; i < line.length(); i++)
	    		if (Character.isDigit(line.charAt(i)))
	    			return false;
    	}
    	return true;
    }
    
    
	public static double getDistanceHamming(State s1, State s2) {
		double dist = 0;
		
    	if (s1.getState().length() != s2.getState().length())
    	{
    		System.out.println("Error: States are not equal length");
    		return -1;
    	}
    	
    	for (int i = 0; i < s1.getState().length(); i++)
    		if (s1.getState().charAt(i) != s2.getState().charAt(i))
    			dist++;
    	
    	return dist;
	}
	
	public static double getDistanceRandom(State s1, State s2) {
		Random rand;
		int upper;
		
		//upper bound not inclusive , therefore range is 0 - length + 1
		upper = s1.getState().length() + 1;
		rand = new Random();
		//will cast and thats fine
		return rand.nextInt(upper);
	}
	
	//Set vars must be an ordered set of variables
	public static double getDistanceWeightHamming(State s1, State s2, HashMap<Character, Double> weights, Set<Character> vars) {
		//assuming the set and states are ordered in the same way?
		// [A, B, C](Set) or 011 (State)
		int i = 0;
		double dist = 0;
		for (Character c: vars)
		{
			//
			if (s1.getState().charAt(i) != s2.getState().charAt(i))
				dist += weights.get(c);
			i++;
		}
		
		return dist;
	}
	
    
    
    /*
     * @params
     * 	BeliefState beliefs
     *  BeliefState sentece
     *  
     * @returns
     * 	BeliefState revisions
     * 
     * The method reviseStates identifies States that could be used to revise a BeliefState. Each State in the beliefs and sentence
     * object are compared for the minimum distance. States in the sentence object with the minimum distance to any State in teh beliefs
     * object are stored and returned by the method.
     */
    public static BeliefState reviseStates(BeliefState beliefs, BeliefState sentence, DistanceState d) {
    	
    	if (beliefs.getBeliefs().size() == 0 || sentence.getBeliefs().size() == 0)
    		return sentence;
    	
    	BeliefState states = new BeliefState();
    	double[] idx = new double[sentence.getBeliefs().size()];
    	double min, curr;
    	
    	
    	min = findStateMinDistance(beliefs, sentence.getBeliefs().get(0), d);
    	idx[0] = min;
    	//
    	for (int i = 1; i < sentence.getBeliefs().size(); i++)
    	{
    		curr = findStateMinDistance(beliefs, sentence.getBeliefs().get(i), d);
    		if (curr < min)
    			min = curr;
    		idx[i] = curr;
    	}
    	
    	for (int i = 0; i < idx.length; i++)
    		if (idx[i] == min)
    			states.addBelief(sentence.getBeliefs().get(i));
    	
    	System.out.println("Min Distance: " + min);
    	
    	return states;
    	
    }
    
    /*
     * @params
     * 		BeliefState beliefs
     *      State sentence_state
     *      
     * @return
     * 		int distance
     * 
     * The findStateMinDistance method compares the State sentence_state to each State in the BeliefState belief.
     * The distance between two states is defined by the Hamming Distance between two States
     * When the State sentence_state has been compared to all states in the belief state, the minimum distance is returned.
     */
    private static double findStateMinDistance(BeliefState beliefs, State sentence_state, DistanceState d) {
    	double min, curr;
    	
    	if (beliefs.getBeliefs().size() < 1)
    		return -1;
    	
    	min = d.getDistance(beliefs.getBeliefs().get(0), sentence_state);
    	
    	for (int i = 1; i < beliefs.getBeliefs().size(); i++)
    	{
    		//curr = findDistance(beliefs.getBeliefs().get(i), sentence_state);
    		curr = d.getDistance(beliefs.getBeliefs().get(i), sentence_state);
    		if (curr < min)
    			min = curr;
    	}
    	
    	return min;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
     * 
     * Belief Revision using RankingState and DistanceState
     * 
     */
    
    /*
     * The reviseStates method does belief revision by comparing a RankingState (Initial belief values) and DistanceState (Trust values between The agent and a reporting agent)
     * The result of revision is an updated belief system represented by a RankingState
     * 
     * @params
     * 		RankingState beliefs
     * 		BeliefState sentence
     * 		DistanceState distance
     * 
     * @return
     * 		RankingState as the result of belief revision
     */
    public static RankingState reviseStates(RankingState beliefs, BeliefState sentence, DistanceState distance) {
    	
    	//return a reanking state?
    	GeneralRevisionScore score, temp;
    	score = new GeneralRevisionScore(beliefs.getValidStates());
    	
    	//if no sentence, beliefs do not change
    	if (sentence.getBeliefs().size() == 0)
    		return beliefs;
    	
    	for (State s: sentence.getBeliefs())
    	{
    		temp = scoreStates(beliefs, s, distance);
    		score = mergeScoresMin(score, temp);
    	}
    	
    	return score.scoreToRank(beliefs.getVocab());
    }
    
    /*
     * The scoreStates method scores every state in the RankingState beliefs by each State's ranking value and the distance between every 
     * belief state and the sentence state. The return value is the result of the sum between this rank and the distance function.
     * 
     * @params
     * 		RankingState beliefs
     * 		State sentence
     * 		DistanceState distance
     * 
     * @return
     * 		GeneralRevisionScore as the score given to each state 
     */
    private static GeneralRevisionScore scoreStates(RankingState beliefs, State sentence, DistanceState distance) {
    	double min, score, dist;
    	int rank;
    	GeneralRevisionScore scoreset = new GeneralRevisionScore(beliefs.getValidStates());
    	
    	for (State s : beliefs.getValidStates().getBeliefs())
    	{
    		//get belief rank
    		rank = beliefs.getRank(s);
    		
    		//get distance between belief state and sentense state
    		dist = distance.getDistance(s, sentence);
    		
    		score = rank + dist;
    		
    		scoreset.setScore(s, score);
    	}
    	
    	return scoreset;
    }
    
	public static GeneralRevisionScore mergeScoresMin(GeneralRevisionScore score1, GeneralRevisionScore score2) {
		GeneralRevisionScore combined = new GeneralRevisionScore();
		double val1,val2;
		
		for (State s : score1.getScoreMap().keySet())
		{
			val1 = score1.getScoreMap().get(s);
			val2 = score2.getScoreMap().get(s);
			if (val1 < val2)
				combined.setScore(s, val1);
			else
				combined.setScore(s, val2);
		}
		
		return combined;
	}
    
}
