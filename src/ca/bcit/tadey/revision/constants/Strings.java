package ca.bcit.tadey.revision.constants;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.trust.constraint.TriangleInequalityOperator;
import ca.bcit.tadey.revision.trust.constraint.TriangleInequalityResponse;

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
	public static final String function_error_state_length(State s) {
		return "Invalid state in file: " + s.getState();
	}
	
	
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
	public static final String action_init_title = "Initial Trust Value";
	
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
	public static final String report_upload_functions = "Upload Functions";
	
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
	public static final String error_init_trust_value = Strings.action_init_title + " must be a double";
	
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
	
	
	
	//tooltip 
	//html tags used for formatting
	
	//action panel
	public static final String tooltip_prop_vocab = "<html>Input the propositional variables to use through all aspects of this tool. <br> Each variable must be "
			+ "separated by a comma <br> eg. x,y,z</html>";
	
	public static final String tooltip_init_trust = "<html>Input a value to set the initially generated trust graph members to.<br>"
			+ "Default: 5.0</html>";
	
	
	public static final String tooltip_threshold = "<html>Set the value for the threshold in Naive revision. <br>"
			+ "Required: Naive Revision<br>"
			+ "Enabled: Naive Revision</html>";
	
	
	public static final String tooltip_rev_type = "Select revision type";
	
	
	public static final String tooltip_gen_trust = "<html>Generate a trust graph for the specified propositional vocabulary<br>"
			+ "Required: " + Strings.action_vocab_title + "<br>"
			+ "Optional:" + Strings.action_init_title
			+ "</html>";
	
	
	public static final String tooltip_revise = "<html>Perform belief revision. <br>"
			+ "Required: " + Strings.action_gen_trust_action + "<br>" + Strings.belief_beliefs_title + "<br>" + Strings.belief_sentence_title
			+ "</html>";
	
	
	//Belief Panel
	public static final String tooltip_ranking_type = "Select ranking type";
	
	public static final String tooltip_beliefs = "Specify initial beliefs";
	public static final String tooltip_upload_beliefs = "<html>Upload belief ranking file <br>"
			+ "Template: ranking.txt</html>";;
	
	public static final String tooltip_sentence = "Specify sentence to revise by";
	
	public static final String tooltip_results = "Results of belief revision. No input";
	
	//Report Panel
	public static final String tooltip_report_formula = "<html>Specify a report to modify the trust graph. <br>"
			+ "Ensure you're using the correct propositional variables. <br>"
			+ "eg: x & y <br>"
			+ "eg. y</html>";
	
	public static final String tooltip_report_result = "<html>Specify a report result. <br>"
			+ "Value must be a 0 or 1.</html>";
	
	public static final String tooltip_add_report = "<html>Add a report to the trust graph. <br>"
			+ "Required: At least one complete formula and result pair. <br>"
			+ "Optional: " + Strings.report_upload_functions
			+ " </html>";
	
	public static final String tooltip_upload_func = "<html>Specify custom function file <br>"
			+ "Template: report_function.txt</html>";
	
	
	//Minimax Distance Panel
	public static final String tooltip_state = "<html>State to check Minimax distance</html>";
	
	public static final String tooltip_state_dist = "<html>Minimax distance value</html>";
	
	public static final String tooltip_find_dist = "<html>Find the minimax distance between two states. <br>"
			+ "Required: " + Strings.mmd_state1 + ", " + Strings.mmd_state2
			+ "</html>";
	
	//error panel
	public static final String tooltip_error_desc = "<html>The Errors pane will display errors and exceptions when they occur. <br>"
			+ "They will provide guidance when the tool is not being used in the correct way.</html>";
	
	
	//trust graph panel
	public static final String tooltip_trust_edge = "<html>Trust value between two valid states. <br>"
			+ "Distance values must be greater than 0.0 <br>"
			+ "Non-greyed out values can be changed manually through keyboard input.</html>";
}
