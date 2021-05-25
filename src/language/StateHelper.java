/**
 * 
 */
package language;

import java.util.ArrayList;

/**
 * @author sam_t
 *
 */
public class StateHelper {
	
	/*
	 * @params
	 * @return
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
	        	//statestrings.get(j).append(val);
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
	
	private static int changeBool(int val) {
		if (val == 1)
			return 0;
		return 1;
	}

}
