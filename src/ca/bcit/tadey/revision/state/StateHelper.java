/**
 * 
 */
package ca.bcit.tadey.revision.state;

import java.util.ArrayList;

/**
 * @author sam_t
 *
 */
public class StateHelper {
	
	/*
	 * Creates all state assignments for a given number of variables.
	 * Eg. all assignments for two variables [[00], [01], [10], [11]]
	 * 
	 * @params
	 * 	int var_num as the number of variables being used to create all combinations of state assignments
	 * @return
	 * 	An ArrayList of ArrayList of Integer representing all possible state assignments given a the parameter
	 */
	public static ArrayList<ArrayList<Integer>> generateAssignments(int var_num) {
		ArrayList<ArrayList<Integer>> assignments = new ArrayList<ArrayList<Integer>>();
		int cur, states = (int) Math.pow(2, var_num), val = 0;
		int sep = states / 2;
		
		for (int i = 0; i < states; i++)
			assignments.add(new ArrayList<Integer>());
		
		//create states in reverse
		for (int i = 0; i < var_num; i++)
		{
	        cur = 0;
	        for (int j = 0; j < states; j++)
	        {
	       
	        	assignments.get(j).add(val);
	            if (++cur == sep)
	            {
	                val = changeBool(val);
	                cur = 0;
	            }
	        }
	        sep /= 2;
		}
			
		return assignments;
	}
	
	/*
	 * Creates all state assignments for a given number of variables.
	 * Eg. all assignments for two variables [[00], [01], [10], [11]]
	 * 
	 * @params
	 * 	int var_num as the number of variables being used to create all combinations of state assignments
	 * @return
	 * 	An ArrayList of State representing all possible state assignments given a the parameter
	 */
	public static ArrayList<State> generateStates(int vocab_size) {
		
		int cur, val = 0, states = (int) Math.pow(2, vocab_size);
		int sep = states / 2;
		ArrayList<State> statelist = new ArrayList<State>(states);
		ArrayList<StringBuilder> statestrings = new ArrayList<StringBuilder>(states);
		
		
		for (int i = 0; i < states; i++)
			statestrings.add(new StringBuilder());
		
		//create states in reverse
		for (int i = 0; i < vocab_size; i++)
		{
	        cur = 0;
	        for (int j = 0; j < states; j++)
	        {
	        	statestrings.get(j).append(val);
	            if (++cur == sep)
	            {
	                val = changeBool(val);
	                cur = 0;
	            }
	        }
	        sep /= 2;
		}
			
		for (StringBuilder s: statestrings)
			statelist.add(new State(s.toString()));
		
		return statelist;
	}
	
	/*
	 * Helper function used in state creation functions
	 * 
	 * @params
	 * 	int val of 0 or 1
	 * @return
	 * 	the opposite of the passed value, changes 1's to 0's, and 0's to 1's.
	 *  Accepts values other than 0 or 1, returns a 1.
	 * 
	 */
	private static int changeBool(int val) {
		if (val == 1)
			return 0;
		return 1;
	}
	

}
