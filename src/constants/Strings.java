package constants;

import distance.constraint.TriangleInequalityOperator;
import distance.constraint.TriangleInequalityResponse;
import language.BeliefState;
import language.State;

/*
 * The Strings class defines static String values used throughout the project in areas such as UI displays and action control parameters
 */
public final class Strings {

	//
	//Report Value Upload
	//
	public static final String report_file_default_header = "-defaults-";
	public static final String report_file_allcombo_header = "-all combinations-";
	public static final String report_file_combo_header = "-combinations-";
	//parsing errors
	public static final String report_file_error_default = "Default section invalid";
	public static final String report_file_error_allcombo = "All Combinations section invalid";
	public static final String report_file_error_combo = "Combinations section invalid";
	
	//for teh parser
	public static final String function_variable = "v = ";
	public static final String function_title = "f(v)";
	
	
	//
	//Formatting
	//
	public static final String text_format = "0.00";
	
	
	//
	//Title 
	//
	public static final String project_title = "Belief Revision";
	
	//
	//Main Panel
	//
	public static final String main_trust_title = "Trust Graph";
	public static final String main_reports_title = "Reports";
	public static final String main_errors_title = "Errors";

	//
	//Belief Panel
	//
	public static final String belief_beliefs_title = "Beliefs";
	public static final String belief_sentence_title = "Sentences";
	public static final String belief_result_title = "Results";
	public static final String belief_action_title = "Actions";
	
	public static final String belief_combo_hamming = "Hamming Ranking";
	public static final String belief_combo_file = "File Ranking";
	public static final String[] belief_combo = {belief_combo_hamming, belief_combo_file};
	
	public static final String ranking_file_choose = "Choose File";
	public static final String ranking_file_generate = "Generate File";
	
	//
	//Action Panel
	//
	public static final String action_vocab_title = "Propositional Vocabulary";
	public static final String action_revise_action = "Revise";
	public static final String action_gen_trust_action = "Generate Trust Graph";
	public static final String action_thresh_title = "Threshold";
	
	//revision types
	public static final String revision_general = "General";
	public static final String revision_naive = "Naive";
	public static final String[] revision_options = {revision_general, revision_naive};
	
	//gen weights panel
	public static final String action_gen_weights = "Gen. Weights";
	public static final String gen_weights_grid_null = "Trust Grid and/or values not set";

	//
	//Report Panel 
	//
	public static final String report_formula_title = "Formula";
	public static final String report_result_title = "Result";
	public static final String report_add_report_action = "Add Reports";
	
	//
	//MiniMaxDistance Panel
	//
	public static final String mmd_title = "MiniMax Distance Checker";
	public static final String mmd_state1 = "State #1:";
	public static final String mmd_state2 = "State #2:";
	public static final String mmd_result = "Distance:";
	public static final String mmd_action = "Find Distance";
	
	
	//
	//Trust Graph
	//
	public static final String action_trust_graph_manual = "Manual Trust Input";
	
	//
	//Constraints Panel
	//
	public static final String constr_title = "Triangle Inequality Response";
	public static final String constr_value_unchange = "Value Unchanged";
	public static final String constr_next_avail = "Next Available Value";
	public static final String constr_minimax_dist = "MinMax/MaxMin Distance";
	
	
	//file
	public static final String file_open_file = "Open File";
	

	
	
	//errors
	public static final String error_bad_vocab = "Bad Vocab Input";
	public static final String error_gen_trust_prereq = "Trust Graph not set";
	public static final String error_bad_vocab_input = "Incorrect Propositional Vocabulary Input Format";
	public static final String error_revise_no_ranking = "Belief Rankings not set";
	public static final String error_no_default_rank = "Default rank value not set.";
	public static final String error_constraint_tri_eq = "Triangle Inequality Handler Not Set";
	
	//state error
	public static final String errorStateNotPossible(String st) {
		return "State: " + st + " not defined in possible states.";
	}
	
	//report error 
	public static final String errorReportInputInvalid(int report_num, String error_msg) {
		return "Report #" + report_num + " Invalid Input: " + error_msg;
	}
	
	public static final String errorTriHandleType(TriangleInequalityResponse res, State s1, State s2, BeliefState invalid, double old_val, double new_val, double to_set) {
		if (res.getOp() == TriangleInequalityOperator.NO_CHANGE)
			return s1.getState() + "/" + s2.getState() + " Triangle Inequality Violated by " 
				+ invalid.toString() + "value before: " + old_val + " value proposed: " + new_val + " value set: " + old_val;
		if (res.getOp() == TriangleInequalityOperator.NEXT_VALID)
			return s1.getState() + "/" + s2.getState() + " Triangle Inequality Violated by " 
					+ invalid.toString() + "value before: " + old_val + " value proposed: " + new_val + " value set: " + to_set;
		if (res.getOp() == TriangleInequalityOperator.MINI_MAX_DIST)
			return s1.getState() + "/" + s2.getState() + " Triangle Inequality Violated by " 
					+ invalid.toString() + "value before: " + old_val + " value proposed: " + new_val + " value set: " + to_set;
//		if (res.getOp() == TriangleInequalityOperator.IGNORE)
//			return s1.getState() + "/" + s2.getState() + " Can't set distance value to 0";
		
		return "";
	}
	
	//exceptions
	public static final String exception_missing_file_param =  "At least one State/Rank pair missing";
	public static final String exception_bad_state_length = "State length in file does not match vocabulary size";
	
}
