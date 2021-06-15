/**
 * 
 */
package distance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import language.BeliefState;
import language.State;
import language.StateHelper;
import propositional_translation.DistanceHelper;
import propositional_translation.InputTranslation;

/**
 * @author sam_t
 *
 */
public class RankingState {
	
	public static final int DEFAULT_RANK = 1;

	private BeliefState validstates;
	//linked hash set
	private ArrayList<Character> vocab;
	private HashMap<State, Integer> rankings;
	private HashMap<Integer, Set<State>> groupings;
	private int min;
	
	/*
	 * RankingState constructor. Takes a set of characters as an input generates default state rankings for all valid states given 
	 * the vocab parameter.
	 * 
	 * @params
	 * 	Set<Character> state vocabulary
	 */
	public RankingState(ArrayList<Character> vocab) {
		 this.vocab = vocab;
		 rankings = new HashMap<State, Integer>();
		 groupings = new HashMap<Integer, Set<State>>();
		 //generate all states for vocab
		 validstates = new BeliefState(StateHelper.generateStates(vocab.size()));
		 Set<State> a = new HashSet<State>();
		 
		 //assign default rank
		 for (State s: validstates.getBeliefs())
		 {
			 rankings.put(s, DEFAULT_RANK);
			 a.add(s);
		 }
		 min = DEFAULT_RANK;
		 groupings.put(DEFAULT_RANK, a);
	}
	
	public RankingState(ArrayList<Character> vocab, int default_rank) {
		 this.vocab = vocab;
		 rankings = new HashMap<State, Integer>();
		 groupings = new HashMap<Integer, Set<State>>();
		 //generate all states for vocab
		 validstates = new BeliefState(StateHelper.generateStates(vocab.size()));
		 Set<State> a = new HashSet<State>();
		 
		 //assign default rank
		 for (State s: validstates.getBeliefs())
		 {
			 rankings.put(s, default_rank);
			 a.add(s);
		 }
		 min = default_rank;
		 groupings.put(default_rank, a);
	}
	
	/*
	 * RankingState Constructor. Takes a BeliefState and sets those states to the min rank. Sets remaining possible states to a rank based on the 
	 * minimum Hamming distance between any min rank state.
	 * 
	 * @params
	 * 	BeliefState states
	 *  ArrayList<Character> vocab
	 */
	public RankingState(BeliefState states, ArrayList<Character> vocab) {
		int rank;
		Set<State> rank_group;
		
		this.vocab = vocab;
		rankings = new HashMap<State, Integer>();
		groupings = new HashMap<Integer, Set<State>>();
		 
		validstates = new BeliefState(StateHelper.generateStates(vocab.size()));
		Set<State> a = new HashSet<State>();
		 
		//assign rank by Hamming Distance
		for (State s : validstates.getBeliefs())
		{
			rank = DistanceHelper.getMinDistanceHamming(states, s);
			rankings.put(s, rank);
			
			//add to groupings
			if (groupings.get(rank) == null) 
			{
				rank_group = new HashSet<State>();
				rank_group.add(s);
				groupings.put(rank, rank_group);
			}
			else 
			{
				groupings.get(rank).add(s);
			}
		}
		min = 0; //BeliefState states are min rank
		 
	}
	
	public ArrayList<Character> getVocab() {
		return vocab;
	}
	
	public BeliefState getValidStates() {
		return validstates;
	}
	
	/*
	 * Returns the rank of the State object
	 * 
	 * @params
	 * 	State s
	 * @return
	 * 	The rank for the specified state as an integer
	 */
	public int getRank(State s) {
		return rankings.get(s);
	}
	
	/*
	 * Sets a rank for the State object. Replaces the old rank value if the state exists, adds the value if the key does not exist.
	 * 
	 * @params
	 * 	State s: Sets a rank for the state s
	 * 	int rank: the rank to associate with the State
	 */
	public void setRank(State s, int rank) {
		int oldrank = this.rankings.put(s, rank);
		Set<State> set;
		
		this.groupings.get(oldrank).remove(s);
		
		if (rank < min) 
		{
			//set new min and reset the hashset with the new min state
			min = rank;
			set = new HashSet<State>();
			set.add(s);
			this.groupings.put(rank,set);
		}
		else
		{		
			//remove state from old bucket
			
			//if we are removing the last item from the minimum set
			//we must set a new min
			if (oldrank == min && this.groupings.get(oldrank).size() == 0)
			{
				int minloc = oldrank+1;
				//set new min, find new min
				while (this.groupings.get(minloc) != null && this.groupings.get(minloc++).size() != 0);

				this.min = minloc;
			}
				
			//add to new bucket
			set = this.groupings.get(rank);
			
			//if set bucket doesn't exist create it
			if (set == null)
				set = new HashSet<State>();

			//add new item to bucket
			set.add(s);
			//add set to the map of sets
			this.groupings.put(rank, set);
		}
	}
	
	/*
	 * Gets the states with the minimum rank value found in the RankingState object.
	 * 
	 * @return
	 * 	BeliefState states: A collection of states with the minimum rank value within this object.
	 */
//	public BeliefState getMinStates() {
//		BeliefState states = new BeliefState();
//		int min = -1, curr;
//		
//		for (State s : this.rankings.keySet())
//		{
//			curr = this.rankings.get(s);
//			if (min == -1)
//				min = curr;
//			
//			if (curr < min)
//			{
//				min = this.rankings.get(s);
//				states = new BeliefState();
//				states.addBelief(s);
//			}
//			else if (curr == min)
//			{
//				states.addBelief(s);
//			}
//		}
//		
//		return states;
//	}
	
	/*
	 * @returns
	 * 	A BeliefState of the states with the min ranking in the RankingState object
	 */
	public BeliefState getMinStates() {
		return new BeliefState(groupings.get(min));
	}
	
	
	public BeliefState reviseStates(String formula) {
		BeliefState formstates, minstates;
		
		//vocab to set
		Set<Character> toset = new LinkedHashSet<Character>();
		for (int i = 0; i < this.vocab.size(); i++)
			toset.add(this.vocab.get(i));
		//convert formula to beliefstate
		formstates = InputTranslation.convertPropInput(formula, toset);
		//get min states from beliefstates of states
		minstates = getMinStates(formstates);
		
		return minstates;
	}
	
	/*
	 * 
	 */
	public BeliefState getMinStates(BeliefState bstates) {
		BeliefState mstates;
		State state;
		int min, rank;
		if (bstates.getBeliefs().size() <= 1)
			return bstates;
		
		min = this.getRank(bstates.getBeliefs().get(0));
		mstates = new BeliefState();
		
		for (int i = 1; i < bstates.getBeliefs().size(); i++)
		{
			state = bstates.getBeliefs().get(i);
			rank = this.getRank(state);
			
			if (rank < min)
			{
				min = rank;
				//not sure what is more efficient finding min then looping through all states again, or this (looping once but clearing if find new min)
				mstates.getBeliefs().clear();
				//mstates.addBelief(state);
			}
			
			if (rank == min)
				mstates.addBelief(state);
		}
		
		return mstates;
	}
	
	
	/*
	 * Converts all min states into a formula. A formula list is a disjunction of formulae. Each formula is a conjunction of all literals.
	 * 
	 * @returns
	 * 	An ArrayList of String: Each string is a human readable formula corresponding to each state with a min ranking value
	 */
	public ArrayList<String> getFormula() {
		ArrayList<String> formulae = new ArrayList<String>();
		BeliefState b = getMinStates();
		
		for (State s : b.getBeliefs())
			formulae.add(convertStateToFormula(s));
		
		return formulae;
	}
	
	/*
	 * Converts a state into a simple formula as a conjunction of all vocabulary variables.
	 * 
	 * @params
	 * 	State s to convert to a formula
	 * @returns
	 * 	String formula representing the state parameter
	 */
	private String convertStateToFormula(State s) {
		StringBuilder build = new StringBuilder();
		
		for (int i = 0; i < s.getState().length() && i < this.vocab.size(); i++)
		{
			if (i != 0)
				build.append(" & ");
			if (s.getState().charAt(i) == '0')
				build.append('~');
			build.append(this.vocab.get(i));
		}
		
		return build.toString();
	}
	
	/*
	 * Visualizer function for the rankings data member
	 * Prints out all states along with their ranking
	 */
	public void toConsole() {
		for (State s : rankings.keySet())
			System.out.println(s.toString() + ": " + rankings.get(s));
	}
	
	
	/*
	 * Visualizer function for groupings data member
	 * Will print out state groupings. Prints all rankings along with the states in each ranking class
	 */
	public void toConsoleGroupings() {
		for (Integer group: this.groupings.keySet())
		{
			System.out.print(group + ": ");
			for (State s : this.groupings.get(group))
				System.out.print(s.toString() + ", ");
			System.out.println();
		}
				
	}
	
	
	public static void main(String[] args) {
		BeliefState bstate;
		ArrayList<State> statelist;
		ArrayList<String> forms;
		ArrayList<Character> vocab = new ArrayList<Character>();
		vocab.add('a');
		vocab.add('b');
		vocab.add('c');
		
		State s1 = new State("000");
		State s2 = new State("010");
		State s3 = new State("110");
		State s4 = new State("001");
		
		RankingState rs = new RankingState(vocab);
		rs.toConsoleGroupings();
		System.out.println("Min: " + rs.min);
		//basic get and set
		//set to new bucket
		rs.setRank(s2, 0);
		rs.setRank(s3, 3);
		rs.setRank(s1, 2);
		rs.setRank(s4, 4);
		rs.setRank(new State("100"), 2);
		rs.setRank(new State("111"), 3);
		rs.setRank(new State("101"), 5);
		rs.setRank(new State("011"), 5);
		
		System.out.println();
		rs.toConsoleGroupings();
		System.out.println("Min: " + rs.min);
		bstate = rs.getMinStates();
		bstate.toConsole();
		
		System.out.println();
		
		//remove from single bucket and set new min
		rs.setRank(s2, 6);
		rs.toConsoleGroupings();
		System.out.println("Min: " + rs.min);
		bstate = rs.getMinStates();
		bstate.toConsole();
		forms = rs.getFormula();
		System.out.println(forms);
		
		rs.setRank(s4, 1);
		rs.toConsoleGroupings();
		System.out.println("Min: " + rs.min);
		//remove from single bucket, set new min not adjacent to min value. eg min = 0 to min = 2
		
		bstate = rs.getMinStates();
		bstate.toConsole();
		
		forms = rs.getFormula();
		System.out.println(forms);
		
		
		//test beliefstate creation with ranking object
		statelist = new ArrayList<State>();
		statelist.add(s1);
		statelist.add(s2);

		bstate = new BeliefState(statelist);
		
		rs = new RankingState(bstate, vocab);
		rs.toConsole();
		System.out.println();
		rs.toConsoleGroupings();
		
	}
}
