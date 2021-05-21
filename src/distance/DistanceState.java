package distance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import language.State;

/**
 * @author sam_t
 *
 */
public class DistanceState {
	
	private Set<Character> vocab;
	
	private HashMap<String, HashMap<String, Double>> distances;
	
	public DistanceState(Set<Character> vocab) {
		this.vocab = vocab;
		//must initialize all possible states with a distance of 0
		ArrayList<State> states = generateStates(vocab.size());
		//state combinations and set distances
		
		distances = new HashMap<String, HashMap<String, Double>>();
		for (int i = 0; i < states.size() - 1; i++)
		{
			HashMap<String, Double> inner = new HashMap<String, Double>();
			for (int j = i+1; j < states.size(); j++)
			{
				
				inner.put(states.get(j).getState(), 0.0);
				
			}
			distances.put(states.get(i).getState(), inner);
		}

	}
	
	private ArrayList<State> generateStates(int vocab_size) {
		
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
	
	private int changeBool(int val) {
		if (val == 1)
			return 0;
		return 1;
	}
	
	public HashMap<String, HashMap<String, Double>> getDistances() {
		return this.distances;
	}
	
	//not sure about this
	public double getDistance(State s1, State s2) {
		//do a string compare
		//smaller states will always be the first argument
		if (s1.getState().compareTo(s2.getState()) < 0)
			return this.distances.get(s1.getState()).get(s2.getState());
		//first state is larger, therefore we must switch order
		else if (s1.getState().compareTo(s2.getState()) > 0)
			return this.distances.get(s2.getState()).get(s1.getState());
		
		//states are equal
		return 0.0;
	}
	
	
	public void setDistance(State s1, State s2, double dist) {
		
		if (s1.getState().compareTo(s2.getState()) < 0)
			this.distances.get(s1.getState()).replace(s2.getState(), dist);
		//first state is larger, therefore we must switch order
		else if (s1.getState().compareTo(s2.getState()) > 0)
			this.distances.get(s2.getState()).replace(s1.getState(), dist);
		
		//if states are equal to nothign
	}
	
	public void stateToConsole() {
		for (Map.Entry<String, HashMap<String, Double>> entry: distances.entrySet())
		{
			String key = entry.getKey();
			for (Map.Entry<String, Double> inner: entry.getValue().entrySet())
			{
				System.out.println(key + " " + inner.getKey() + " =  " + inner.getValue());
			}
		}
	}
	
	
	public static void main(String[] args) {
		Set<Character> vocab = new LinkedHashSet<Character>();
		vocab.add('A');
		vocab.add('B');
		vocab.add('C');
		
		State s1 = new State("000");
		State s2 = new State("001");
		State s3 = new State("111");
		State s4 = new State("101");
		State s5 = new State("010");
		
		String o = new String("000");
		String i = new String("001");
		String s = new String("100");
		
		System.out.println(s.compareTo(i));
		
		//System.out.println();
		
		DistanceState dist = new DistanceState(vocab);
		
		///dist.stateToConsole();
		
		dist.setDistance(s2, s1, 2.0);
		System.out.println(dist.getDistance(s1, s2));
		
		dist.setDistance(s1, s3, 20.0);
		dist.setDistance(s4, s3, 12.5);
		dist.setDistance(s5, s2, 0.1);
		dist.setDistance(s5, s3, 111.11);
		
		System.out.println(dist.getDistance(s3, s1));
		
		dist.stateToConsole();
//		for (State key: dist.getDistances().keySet())
//		{
//			for ()
//		}
//		ArrayList<State> st = dist.generateStates(vocab.size());
//		
//		
//		for (int i = 0; i < st.size(); i++)
//			System.out.println(st.get(i).getState());
//		
//		
		
	}

}



