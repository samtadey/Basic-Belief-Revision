/**
 * 
 */
package ca.bcit.tadey.revision.trust.file;

import java.util.HashMap;

import ca.bcit.tadey.revision.state.State;

/**
 * A ReportFunction object stores String formula's that are applied to state combinations during the Add Report process on the Trust Graph.
 * There are multiple fields of customization that can be used to modify trust graph scores. 
 * 
 * Default values - Most General
 * All Combo values 
 * Combo values - Most specific
 * 
 * Should take the forms
 * 	- "1"
 *  - "f(v) = 1 + v"

 * @author sam_t
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
	 * Default Report Function Constructor
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
	 * Report Function Constructor, set the default positive and negative formulas
	 */
	public ReportFunction(String pos, String neg) {
		this.default_pos = pos;
		this.default_neg = neg;
		
		this.all_combo_pos = new HashMap<State,String>();
		this.all_combo_neg = new HashMap<State,String>();
		
		this.combo_pos = new HashMap<State, HashMap<State, String>>();
		this.combo_neg = new HashMap<State, HashMap<State, String>>();
	}
	
	/**
	 * Get default positive formula
	 * @return default positive formula
	 */
	private String getDefaultPos() {
		return default_pos;
	}

	/**
	 * Get default negative formula
	 * @return default negative formula
	 */
	private String getDefaultNeg() {
		return default_neg;
	}
	
	/**
	 * Set default positive formula
	 * @param pos String
	 */
	protected void setDefaultPos(String pos) {
		this.default_pos = pos;
	}
	
	/**
	 * Set default negative formula
	 * @param neg String
	 */
	protected void setDefaultNeg(String neg) {
		this.default_neg = neg;
	}

	/**
	 * Get the positive formula for all combinations of the state parameter
	 * If there exists a entry for State : 00
	 * 	- This would be applied to these state combinations: 00/01, 00/10, 00/11
	 * 
	 * @param s State
	 * @return String or null
	 */
	private String getAllComboPos(State s) {
		return all_combo_pos.get(s);
	}

	/**
	 * Get the negative formula for all combinations of the state parameter
	 * If there exists a entry for State : 00
	 * 	- This would be applied to these state combinations: 00/01, 00/10, 00/11
	 * 
	 * @param s State
	 * @return String or null
	 */
	private String getAllComboNeg(State s) {
		return all_combo_neg.get(s);
	}

	/**
	 * Add entry to the All Combo Positive structure
	 * 
	 * @param st State
	 * @param val String formula
	 */
	protected void addToAllComboPos(State st, String val) {
		this.all_combo_pos.put(st, val);
	}
	
	/**
	 * Add entry to the All Combo Negative structure
	 * 
	 * @param st State
	 * @param val String formula
	 */
	protected void addToAllComboNeg(State st, String val) {
		this.all_combo_neg.put(st, val);
	}
	

	/**
	 * Wrapper function that chooses the Positive option for the addToCombo method
	 * 
	 * @param s
	 * @param t
	 * @param val
	 */
	protected void addToComboPos(State s, State t, String val) {
		addToCombo(this.combo_pos, s, t, val);
	}
	
	/**
	 * Wrapper function that chooses the Negative option for the addToCombo method
	 * 
	 * @param s
	 * @param t
	 * @param val
	 */
	protected void addToComboNeg(State s, State t, String val) {
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
	
	/**
	 * Get Positive State combination formula
	 * 
	 * @param s State
	 * @param t State
	 * @return String formula or null
	 */
	private String getComboPos(State s, State t) {
		return getCombo(this.combo_pos, s, t);
	}


	/**
	 * Get Negative State combination formula
	 * 
	 * @param s State
	 * @param t State
	 * @return String formula or null
	 */
	private String getComboNeg(State s, State t) {
		return getCombo(this.combo_neg, s, t);
	}
	
	/**
	 * Gets the formula value for the specified state combination parameter
	 * 
	 * @param pos_or_neg HashMap<State, HashMap<State, String>> instance variable
	 * @param s State
	 * @param t State
	 * @return String formula or null
	 */
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
	
	
	/**
	 * Find the Positive Formula to use for a given State combination
	 * Checks each positive data structure from most specific to most general
	 * Combo -> AllCombo -> Default
	 * When an entry is found in any of those structures, that formula is returned
	 * as the formula for the state combination (s1,s2) to use
	 * 
	 * @param s1 State
	 * @param s2 State
	 * @return String formula
	 */
	public String findPosFormula(State s1, State s2) {
		String val;
		
		val = getComboPos(s1, s2);
		
		//if no specific val
		//use default
		if (val == null)
			val = getDefaultPos();
			
		return val;
		
	}
	
	/**
	 * Find the Negative Formula to use for a given State combination
	 * Checks each positive data structure from most specific to most general
	 * Combo -> AllCombo -> Default
	 * When an entry is found in any of those structures, that formula is returned
	 * as the formula for the state combination (s1,s2) to use
	 * 
	 * @param s1 State
	 * @param s2 State
	 * @return String formula
	 */
	public String findNegFormula(State s1, State s2) {
		String val;
		
		val = getComboNeg(s1, s2);
		
		//if no specific val
		//use default
		if (val == null)
			val = getDefaultNeg();
			
		return val;
		
	}
	
}
