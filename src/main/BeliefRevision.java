/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import distance.DistanceMap;
import distance.DistanceState;
import distance.RankingState;
import distance.revision.RevisionOperator;
import distance.revision.RevisionStateScore;
import distance.revision.StateScore;
import distance.revision.StateScoreResults;
import distance.revision.StateScoreResultsGeneral;
import distance.revision.StateScoreResultsNaive;
import language.BeliefState;
import language.State;
import propositional_translation.InputTranslation;



/**
 * @author sam_t
 *
 */
public class BeliefRevision {
	
	RevisionOperator rev;
	DistanceMap distance;
	RankingState beliefs;
	BeliefState sentence;
	double threshold;
	
	public BeliefRevision(RankingState beliefs, BeliefState sentence, DistanceMap distance, RevisionOperator rev_type) {
		this.beliefs = beliefs;
		this.sentence = sentence;
		this.distance = distance;
		this.rev = rev_type;
		this.threshold = 0;
	}
	
	public BeliefRevision(RankingState beliefs, BeliefState sentence, DistanceMap distance, RevisionOperator rev_type, double threshold) {
		this.beliefs = beliefs;
		this.sentence = sentence;
		this.distance = distance;
		this.rev = rev_type;
		this.threshold = threshold;
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
    public BeliefState reviseStates(BeliefState beliefs, BeliefState sentence, DistanceMap d) {
    	
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
    private double findStateMinDistance(BeliefState beliefs, State sentence_state, DistanceMap d) {
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
    
    //
    //old stuff
    //
    
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
    public RankingState reviseStates() {
    	
    	//return a reanking state?
    	RevisionStateScore score, temp;
    	score = new RevisionStateScore(beliefs.getValidStates());
    	
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
    private static RevisionStateScore scoreStates(RankingState beliefs, State sentence, DistanceMap distance) {
    	double score, dist;
    	int rank;
    	RevisionStateScore scoreset = new RevisionStateScore(beliefs.getValidStates());
    	
    	for (State s : beliefs.getValidStates().getBeliefs())
    	{
    		//get belief rank
    		rank = beliefs.getRank(s);
    		
    		//get distance between belief state and sentence state
    		dist = distance.getDistance(s, sentence);
    		
    		//score the state with its rank and distance to sentence state
    		score = rank + dist;
    		
    		scoreset.setScore(s, score);
    	}
    	
    	return scoreset;
    }
    
    
    
	public static RevisionStateScore mergeScoresMin(RevisionStateScore score1, RevisionStateScore score2) {
		RevisionStateScore combined = new RevisionStateScore();
		double val1,val2;
		
		for (State s : score1.getScoreKeys())
		{
			val1 = score1.getScore(s);
			val2 = score2.getScore(s);
			
			if (val1 < val2)
				combined.setScore(s, val1);
			else
				combined.setScore(s, val2);
		}
		
		return combined;
	}
	
	//
	//
	//new stuff
    //
	//
	
	
	/**
	 * Creates a StateScoreResults object based on the RevisionOperator of this object
	 * 
	 * @param sentence
	 * @param beliefs
	 * @param threshold
	 * @return
	 */
	private StateScoreResults genResultsType(BeliefState sentence, BeliefState beliefs, double threshold) {
    	if (this.rev == RevisionOperator.GENERAL)
    		return new StateScoreResultsGeneral(sentence);
    	else if (this.rev == RevisionOperator.NAIVE)
    		return new StateScoreResultsNaive(sentence, beliefs, threshold);
    	return null;
    	
    }
    
	/**
	 * Create a StateScoreResults object containing the states and other data produced through revision process. 
	 * 
	 * @return
	 * @throws Exception
	 */
    public StateScoreResults produceStateScoreResults() throws Exception {
    	
    	StateScoreResults results = genResultsType(sentence, beliefs.getMinStates(), this.threshold);
    	StateScore score;
    	
    	//create a statescore for each belief
    	for (State b : beliefs.getValidStates().getBeliefs())
    	{
    		score = new StateScore(b, beliefs.getRank(b));
    		//add each sentence distance value to each statescore
    		for (State sent : results.getSentences().getBeliefs())
    			score.addDistance(distance.getDistance(b, sent));
    		//add complete statescore to the results object
    		results.addStateScore(score);
    	}
    	
    	//calculate results for the object
    	results.setResults();
 
    	return results;
    }
    
    /**
     * Create a StateScoreResults object containing the states and other data produced through revision process.
     * Uses minimax distance values for distance measurements
     * 
     * @param minimax
     * @return
     * @throws Exception
     */
    public StateScoreResults produceStateScoreResults(DistanceMap minimax) throws Exception {
    	
    	StateScoreResults results = genResultsType(sentence, beliefs.getMinStates(), this.threshold);
    	StateScore score;
    	
    	//create a statescore for each belief
    	for (State b : beliefs.getValidStates().getBeliefs())
    	{
    		score = new StateScore(b, beliefs.getRank(b));
    		//add each sentence distance value to each statescore
    		for (State sent : results.getSentences().getBeliefs())
    			score.addDistance(minimax.getDistance(b, sent));
    		//add complete statescore to the results object
    		results.addStateScore(score);
    	}
    	
    	//calculate results for the object
    	results.setResults();
 
    	return results;
    }
    
    /**
     * Uses the StateScoreResults object to produce a RankingState as the product of belief revision
     * 
     * @param results
     * @return
     */
    public RankingState reviseStates(StateScoreResults results) {
    	return results.scoreToRank(InputTranslation.setToArr(distance.getVocab()));
    }
    
}
