/**
 * 
 */
package main;



import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import distance.Distance;
import distance.HammingDistance;
import language.BeliefState;
import language.State;


/**
 * @author sam_t
 *
 */
public class BeliefRevision {
	
	/*
	 * Default Constructor
	 */
	public BeliefRevision() {}
	  
    /*
     * @params
     * 	String input
     * 
     * Recieves a string representation of a BeliefState and converts it into
     * a BeliefState object.
     */
    public static BeliefState parseInput(String input) {
    	
	   	BeliefState bstate = new BeliefState();     
	    for (String line : input.split("\n")) 
	    {
	    	bstate.addBelief(new State(line));
	    }
	    return bstate;
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
    public static BeliefState reviseStates(BeliefState beliefs, BeliefState sentence, Distance d) {
    	
    	if (beliefs.getBeliefs().size() == 0 || sentence.getBeliefs().size() == 0)
    		return sentence;
    	
    	BeliefState states = new BeliefState();
    	int[] idx = new int[sentence.getBeliefs().size()];
    	int min, curr;
    	
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
    private static int findStateMinDistance(BeliefState beliefs, State sentence_state, Distance d) {
    	int min, curr;
    	
    	if (beliefs.getBeliefs().size() < 1)
    		return -1;
    	
    	//min = findDistance(beliefs.getBeliefs().get(0), sentence_state);
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
     * @params
     * 		State s1
     * 		State s2
     * 
     * @returns
     * 		int distance
     * 
     * This function compares two States and returns the distance between the two.
     * The distance is defined as the Hamming Distance between the two States.
     * 
     */
    private static int findDistance(State s1, State s2) {
    	int dist = 0;
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

}
