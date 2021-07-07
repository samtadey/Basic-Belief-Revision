/**
 * 
 */
package distance.file;

import java.util.HashMap;

import language.State;

/**
 * @author sam_t
 *
 */
public class ReportFunction {
	
	String default_pos;
	String default_neg;
	
	HashMap<State, String> all_combo_pos;
	HashMap<State, String> all_combo_neg;
	
	HashMap<State, HashMap<State, String>> combo_pos;
	HashMap<State, HashMap<State, String>> combo_neg;

	/**
	 * 
	 */
	public ReportFunction(String pos, String neg) {
		this.default_pos = pos;
		this.default_neg = neg;
		
		this.all_combo_pos = new HashMap<State,String>();
		this.all_combo_neg = new HashMap<State,String>();
		
		this.combo_pos = new HashMap<State, HashMap<State, String>>();
		this.combo_neg = new HashMap<State, HashMap<State, String>>();
	}
	
	//temp
//	public HashMap<State, HashMap<State, String>> getComboNeg() {
//		return this.combo_neg;
//	}
//	
//	public HashMap<State, HashMap<State, String>> getComboPos() {
//		return this.combo_pos;
//	}
//	
//	public HashMap<State,String> getAllComboPos() {
//		return this.all_combo_pos;
//	}
//	
//	public HashMap<State,String> getAllComboNeg() {
//		return this.all_combo_neg;
//	}
	
	//getters
	public String getDefaultPos() {
		return default_pos;
	}

	public String getDefaultNeg() {
		return default_neg;
	}
	
	public void setDefaultPos(String pos) {
		this.default_pos = pos;
	}
	
	public void setDefaultNeg(String neg) {
		this.default_neg = neg;
	}

	public String getAllComboPos(State s) {
		return all_combo_pos.get(s);
	}


	public String getAllComboNeg(State s) {
		return all_combo_neg.get(s);
	}


	public String getComboPos(State s, State t) {
		int result = s.compareTo(t);
		
		if (result < 0)
			return this.combo_pos.get(s).get(t);
		else
			return this.combo_pos.get(t).get(s);
	}


	public String getComboNeg(State s, State t) {
		int result = s.compareTo(t);
		
		if (result < 0)
			return this.combo_neg.get(s).get(t);
		else
			return this.combo_neg.get(t).get(s);
	}

	//setters
	public void addToAllComboPos(State st, String val) {
		this.all_combo_pos.put(st, val);
	}
	
	public void addToAllComboNeg(State st, String val) {
		this.all_combo_neg.put(st, val);
	}
	
	//need ordering
	public void addToComboPos(State s, State t, String val) {
		HashMap<State, String> inner = new HashMap<State, String>();
		int result = s.compareTo(t);
	
		if (result < 0)
		{
			inner.put(t, val);
			this.combo_pos.put(s, inner);
		}
		else 
		{
			inner.put(s, val);
			this.combo_pos.put(t, inner);
		}
	}
	
	public void addToComboNeg(State s, State t, String val) {
		HashMap<State, String> inner = new HashMap<State, String>();
		int result = s.compareTo(t);
	
		if (result < 0)
		{
			inner.put(t, val);
			this.combo_neg.put(s, inner);
		}
		else 
		{
			inner.put(s, val);
			this.combo_neg.put(t, inner);
		}
	}
	
	
	//starts s
	//not checking the all combinations structures until problem solved
	public String findPosFormula(State s1, State s2) {
		String val;
		
		val = getComboPos(s1,s2);
		
		//if no specific val
		//use default
		if (val == null)
			val = getDefaultPos();
			
		return val;
		
	}
	
	//starts s
	//not checking the all combinations structures until problem solved
	public String findNegFormula(State s1, State s2) {
		String val;
		
		val = getComboNeg(s1,s2);
		
		//if no specific val
		//use default
		if (val == null)
			val = getDefaultNeg();
			
		return val;
		
	}
	
	
	//find applied value function?
	//looks through all structures from specific to general
	//and returns the new value 
	
//    public static void main(String[] args) {
//    	String pos = "1";
//    	String neg = "0.5";
//    	
//    	ReportFunction input = new ReportFunction(pos, neg);
//    	
//    	System.out.println(input.getDefaultPos() + " " + input.getDefaultNeg());
//    	
//    	State s1 = new State("00");
//    	State s2 = new State("11");
//    	String val = new String("2");
//    	String val2 = new String("3");
//    	String val3 = new String("formula here");
//    	
//    	input.addToComboPos(s1, s2, val);
//    	input.addToComboNeg(s1, s2, val2);
//    	System.out.println(input.getComboPos(s2, s1));
//    	System.out.println(input.getComboNeg(s1, s2));
//    	
//    	input.addToAllComboPos(s2, val3);
//    	System.out.println(input.getAllComboPos(s2));
//    	
//    }
}
