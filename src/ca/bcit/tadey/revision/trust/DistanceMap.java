package ca.bcit.tadey.revision.trust;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.state.StateHelper;

/**
 * @author sam_t
 *
 */
public class DistanceMap {
	
	public static final double DEFAULT_VAL = 5.0;
	
	private Set<Character> vocab;
	private BeliefState possible_states;
	private HashMap<State, HashMap<State, Double>> distances;
	private double init_val;
	
	/*
	 * Constructor for the DistanceState object
	 * Initiates all State combinations in the distances member, and sets all the distances to 0.0
	 * 
	 * @params
	 * 	Set<Character> representing the propositional variables
	 */
	public DistanceMap(Set<Character> vocab) {
		this.vocab = vocab;
		//must initialize all possible states with a distance of 0
		//not easy to generate states as strings, thats why they are 
		this.possible_states = new BeliefState(StateHelper.generateStates(vocab.size()));
		HashMap<State, Double> inner;
		//state combinations and set distances
		this.init_val = DEFAULT_VAL;
		
		this.distances = new HashMap<State, HashMap<State, Double>>();
		for (int i = 0; i < possible_states.getBeliefs().size() - 1; i++)
		{
			inner = new HashMap<State, Double>();
			for (int j = i+1; j < possible_states.getBeliefs().size(); j++)	
				inner.put(possible_states.getBeliefs().get(j), this.init_val);
			distances.put(possible_states.getBeliefs().get(i), inner);
		}
	}
	
	/*
	 * Constructor for the DistanceState object
	 * Initiates all State combinations in the distances member, and sets all the distances to 0.0
	 * 
	 * @params
	 * 	Set<Character> representing the propositional variables
	 *  double init_val representing the starting value to set trust graph cells to
	 */
	public DistanceMap(Set<Character> vocab, double init_val) {
		this.vocab = vocab;
		//must initialize all possible states with a distance of 0
		//not easy to generate states as strings, thats why they are 
		this.possible_states = new BeliefState(StateHelper.generateStates(vocab.size()));
		HashMap<State, Double> inner;
		//state combinations and set distances
		this.init_val = init_val;
		
		this.distances = new HashMap<State, HashMap<State, Double>>();
		for (int i = 0; i < possible_states.getBeliefs().size() - 1; i++)
		{
			inner = new HashMap<State, Double>();
			for (int j = i+1; j < possible_states.getBeliefs().size(); j++)	
				inner.put(possible_states.getBeliefs().get(j), this.init_val);
			distances.put(possible_states.getBeliefs().get(i), inner);
		}
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param map as the DistanceMap to copy
	 */
	public DistanceMap(DistanceMap map) {
		this.vocab =  new LinkedHashSet<Character>(map.vocab);
		this.possible_states = new BeliefState(map.possible_states);
		this.init_val = map.init_val;
		
		//set map
		this.distances = new HashMap<State, HashMap<State, Double>>();
		
		HashMap<State, Double> inner;
		
		for (State s1: map.distances.keySet())
		{
			inner = new HashMap<State,Double>();
			for (State s2 : map.distances.get(s1).keySet())
			{
				inner.put(s2, map.getDistance(s1, s2));
			}
			this.distances.put(s1, inner);
		}
	}
	
	/*
	 * Getter function for the vocab member variable
	 * 
	 * @returns 
	 * 	Set<Character> as the propositional characters
	 */
	public Set<Character> getVocab() {
		return this.vocab;
	}
	
	/*
	 * Getter function for the distances member variable
	 * 
	 * @returns
	 * 	distances as a HashMap<State, HashMap<State, Double>>
	 */
	public HashMap<State, HashMap<State, Double>> getDistances() {
		return this.distances;
	}
	
	
	public BeliefState getPossibleStates() {
		return this.possible_states;
	}
	/*
	 * Getter function for the distance between two state objects
	 * 
	 * @params
	 * 	State s1
	 * 	State s2
	 * @returns
	 * 	The distance between the two parameter states as a double.
	 * 	If the states are equal, the distance returned is 0.
	 */
	public double getDistance(State s1, State s2) throws NullPointerException {
		
		int result = s1.compareTo(s2);
		//do a string compare
		//smaller states will always be the first argument
		if (result < 0)
			return this.distances.get(s1).get(s2);
		//first state is larger, therefore we must switch order
		else if (result > 0)
			return this.distances.get(s2).get(s1);
		
		//states are equal
		return 0.0;
	}
	
	/*
	 * Setter function for the distance between two state objects
	 * 
	 * @params
	 * 	State s1
	 * 	State s2
	 *  double dist as the distance between s1 and s2
	 * 
	 * @throws
	 * 
	 */
	public void setDistance(State s1, State s2, double dist) throws NullPointerException {
		
		if (dist < 0)
		{
			//should I set the distance TO zero in this case?
			//throw new DistanceStateException("Attempting to set " + s1.getState() + "/" + s2.getState() + " below zero");
			System.out.println("Attempting to set " + s1.getState() + "/" + s2.getState() + " below zero: No Change");
			return;
		}
		
		int result = s1.compareTo(s2);
		
		if (result < 0)
			this.distances.get(s1).replace(s2, dist);
		//first state is larger, therefore we must switch order
		else if (result > 0)
			this.distances.get(s2).replace(s1, dist);
		
		//if states are equal to nothign
	}

	/*
	 * Prints state combinations and their distances to the console
	 */
	public void stateToConsole() {
		for (Map.Entry<State, HashMap<State, Double>> entry: distances.entrySet())
		{
			State key = entry.getKey();
			for (Map.Entry<State, Double> inner: entry.getValue().entrySet())
			{
				System.out.println(key.getState() + " " + inner.getKey().getState() + " =  " + inner.getValue());
			}
		}
	}
	
	
//	public static void main(String[] args) {
////		Set<Character> vocab = new LinkedHashSet<Character>();
////		vocab.add('A');
////		vocab.add('B');
////		vocab.add('C');
////		
//////		
//////		State s1 = new State("000");
//////		State s2 = new State("001");
//////		State s3 = new State("111");
//////		State s4 = new State("101");
//////		State s5 = new State("010");
//////		
////		DistanceState dist = new DistanceState(vocab);	
////		Report r = new Report("A & B", 0);
////		Report r2 = new Report("A & B", 1);
////		dist.stateToConsole();
////		dist.addReport(r);
////		System.out.println("After Adding Report");
////		dist.stateToConsole();
////		dist.addReport(r2);
////		System.out.println("After Adding Report 2");
////		dist.stateToConsole();
////		
////		dist.stateToConsole();
//	}
//
	
	
}



