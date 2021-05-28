/**
 * 
 */
package main;



import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import aima.core.logic.common.ParserException;
import distance.DistanceState;
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
	
	
	
	
    public static BeliefState convertPropInput(String text, Set<Character> chars) throws ParserException {
    	FormulaSet formset;
		BeliefState soln;
		//probs some checking here
		formset = InputTranslation.propToCNFForm(text, chars);

		DPLL dpll = new DPLL();
		soln = dpll.allSatDpllBlock(formset);
		
		return soln;
    }
	  
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
	

//	public static double getParametrizedDistance(State s1, State s2, HashMap<Character, Double> weights, Set<Character> vars) {  
//				
//		//levels are as such
//		//B = 1
//		//C = 2
//		//D = 3
//		//A = 4
//		//weights are assigned to the hashmap in preprossessing
//		
//		int i = 0;
//		double dist = 0;
//		for (Character c: vars)
//		{
//			if (s1.getState().charAt(i) != s2.getState().charAt(i))
//				dist += weights.get(c);
//			i++;
//		}
//		
//		
//		return dist;
//	}
    
    
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
//    private static int findDistance(State s1, State s2) {
//    	int dist = 0;
//    	if (s1.getState().length() != s2.getState().length())
//    	{
//    		System.out.println("Error: States are not equal length");
//    		return -1;
//    	}
//    	
//    	for (int i = 0; i < s1.getState().length(); i++)
//    		if (s1.getState().charAt(i) != s2.getState().charAt(i))
//    			dist++;
//    	
//    	return dist;
//    }

}
