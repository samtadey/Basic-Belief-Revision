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
	
	public static final String DEFAULTS = "1";
	
	private String default_pos;
	private String default_neg;
	
	private HashMap<State, String> all_combo_pos;
	private HashMap<State, String> all_combo_neg;
	
	private HashMap<State, HashMap<State, String>> combo_pos;
	private HashMap<State, HashMap<State, String>> combo_neg;
	
	/**
	 * 
	 */
	public ReportFunction() {
		this.default_pos = DEFAULTS;
		this.default_neg = DEFAULTS;
		
		this.all_combo_pos = new HashMap<State,String>();
		this.all_combo_neg = new HashMap<State,String>();
		
		this.combo_pos = new HashMap<State, HashMap<State, String>>();
		this.combo_neg = new HashMap<State, HashMap<State, String>>();
	}

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

	//setters
	public void addToAllComboPos(State st, String val) {
		this.all_combo_pos.put(st, val);
	}
	
	public void addToAllComboNeg(State st, String val) {
		this.all_combo_neg.put(st, val);
	}
	

	/**
	 * Wrapper function that chooses the Positive option for the addToCombo method
	 * 
	 * @param s
	 * @param t
	 * @param val
	 */
	public void addToComboPos(State s, State t, String val) {
		addToCombo(this.combo_pos, s, t, val);
	}
	
	/**
	 * Wrapper function that chooses the Negative option for the addToCombo method
	 * 
	 * @param s
	 * @param t
	 * @param val
	 */
	public void addToComboNeg(State s, State t, String val) {
		addToCombo(this.combo_neg, s, t, val);
	}
	
	/**
	 * Driver function behind addToComboPos and addToComboNeg.
	 * This function will add state combinations to the corresponding HashMap data structure
	 * 
	 * @param pos_or_neg either this.combo_pos or this.combo_neg
	 * @param s state 1 to add
	 * @param t state 2 to add
	 * @param val value being mapped to state 1 and 2
	 */
	private void addToCombo(HashMap<State, HashMap<State, String>> pos_or_neg, State s, State t, String val) {
		HashMap<State, String> inner;
		
		int result = s.compareTo(t);

		//find inner hashmap
		if (result < 0)
			if (pos_or_neg.get(s) == null)
				inner = new HashMap<State, String>();
			else 
				inner = pos_or_neg.get(s);
		else
			if (pos_or_neg.get(t) == null)
				inner = new HashMap<State, String>();
			else 
				inner = pos_or_neg.get(t);
		
		//place new item in inner hashmap
		if (result < 0)
		{			
			inner.put(t, val);
			//need this line if inner was null before
			pos_or_neg.put(s, inner);
		}
		else 
		{
			inner.put(s, val);
			pos_or_neg.put(t, inner);
		}
	}
	
	
	public String getComboPos(State s, State t) {
		return getCombo(this.combo_pos, s, t);
	}


	public String getComboNeg(State s, State t) {
		return getCombo(this.combo_neg, s, t);
	}
	
	private String getCombo(HashMap<State, HashMap<State, String>> pos_or_neg, State s, State t) {
		int result = s.compareTo(t);
		
		if (result < 0)
		{
			if (pos_or_neg.get(s) != null)
				return pos_or_neg.get(s).get(t);
		}
		else
		{
			if (pos_or_neg.get(t) != null)
				return pos_or_neg.get(t).get(s);
		}
		
		return null;
	}
	
	
	//starts s
	//not checking the all combinations structures until problem solved
	public String findPosFormula(State s1, State s2) {
		String val;
		
		val = getCombo(this.combo_neg, s1, s2);
		
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
		
		val = getCombo(this.combo_pos, s1, s2);
		
		//if no specific val
		//use default
		if (val == null)
			val = getDefaultNeg();
			
		return val;
		
	}
	
	

	//should check states against vocab before additions
	
	
}
