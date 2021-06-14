package constants;

/*
 * The Strings class defines static String values used throughout the project in areas such as UI displays and action control parameters
 */
public final class Strings {

	//
	//ParametrizedDiffPanel 
	//
	public static final String var_up = "Move Up";
	public static final String var_down = "Move Down";
	
	//
	//Distance Choices
	//
	public static final String hamming = "Hamming";
	public static final String w_hamming = "Weighted Hamming";
	public static final String para = "Parametrized Difference";
	public static final String rand = "Random Distance";
	
	//
	//Input Type Choices
	//
	
	public static final String prop_input = "Propositional Formula";
	public static final String state_input = "State Representation";
	
	
	//
	//Combo Box selectors
	//
	public static final String[] distance_types = {hamming, w_hamming, para, rand};
	public static final String[] input_types = {prop_input, state_input};
	
	
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
	
	//
	//Report Panel 
	//
	public static final String report_formula_title = "Formula";
	public static final String report_result_title = "Result";
	public static final String report_add_report_action = "Add Reports";
	
	//file
	public static final String file_open_file = "Open File";
	
	
	//errors
	public static final String error_bad_vocab = "Bad Vocab Input";
	public static final String error_gen_trust_prereq = "Trust Graph not set";
	public static final String error_bad_vocab_input = "Incorrect Propositional Vocabulary Input Format";
	public static final String error_revise_no_ranking = "Belief Rankings not set";
	
	//exceptions
	public static final String exception_missing_file_param =  "At least one State/Rank pair missing";
	public static final String exception_bad_state_length = "State length in file does not match vocabulary size";
	
}
