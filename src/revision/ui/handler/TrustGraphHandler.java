/**
 * 
 */
package revision.ui.handler;

import java.awt.GridBagConstraints;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import constants.PropositionalSymbols;
import constants.Strings;
import distance.DistanceMap;
import distance.DistanceState;
import distance.Report;
import distance.revision.TriangleInequalityResponse;
import language.BeliefState;
import language.State;
import revision.ui.TrustGraphPanel;

/**
 * @author sam_t
 *
 * The TrustGraphHandler is responsible for handling back end logic changes, and then translating these changes to the GUI Panels. 
 * Functions:
 * 		rebuildGrid
 * 		addAllReport
 */
public class TrustGraphHandler {

	static DecimalFormat dc = new DecimalFormat(Strings.text_format);
	
	public static ArrayList<ArrayList<JTextField>> resetGridItems(int grids) {
		ArrayList<ArrayList<JTextField>> grid_text = new ArrayList<ArrayList<JTextField>>();
    	for (int i = 0; i < grids; i++)
    		grid_text.add(new ArrayList<JTextField>());
    	
    	return grid_text;
	}
	
	public static String setFormattedText(double val) {
		return dc.format(val);
	}
	
	/*
	 * rebuildGrid builds the TrustGraphPanel from the DistanceState object.
	 * 
	 * @params
	 * 		TrustGraphPanel grid: The panel to rebuild according to DistanceState values
	 * 		BeliefState allstates: All possible states given the propositional variables
	 * 		//distval?
	 * 		ArrayList<ArrayList<JTextField>> grid_text: UI grid items, each grid piece is a textfield
	 * 		DistanceState distance: The object being translated to the TrustGraphPanel object
	 * 
	 */
	public static void rebuildGrid(TrustGraphPanel grid, ArrayList<ArrayList<JTextField>> grid_text, DistanceState distance) {
		State s1, s2;
		double dist;
		JTextField tf;
		BeliefState allstates = distance.getMap().getPossibleStates();
		//DecimalFormat dc = new DecimalFormat(Strings.text_format);
		//int grids = allstates.getBeliefs().size();
		
		//GridBagConstraints gbc = new GridBagConstraints();
		
		grid.removeAll();
	
		//iterate through all textfields and set to distancestate
		grid.add(new JLabel("State/State"));
    	for (int i = 0; i < allstates.getBeliefs().size(); i++)
    		grid.add(new JLabel("" + allstates.getBeliefs().get(i).getState()));
    	
    	for (int i = 0; i < allstates.getBeliefs().size(); i++)
    	{
    		//danger
    		for (int j = -1; j < allstates.getBeliefs().size(); j++)
    		{
    			if (j < 0)
    				grid.add(new JLabel("" + allstates.getBeliefs().get(i).getState()));
    			else 
    			{
    				s1 = allstates.getBeliefs().get(i);
    				s2 = allstates.getBeliefs().get(j);
    				dist = distance.getMap().getDistance(s1, s2);
    				//decimal formatting
    				tf = new JTextField(setFormattedText(dist));
    				//tf = new JTextField(Double.toString(dist));
    				if (i == j || j > i)
    					tf.setEditable(false);
    				
    				
    				//add listener
    				tf.addFocusListener(grid);
    				grid_text.get(i).add(tf);
    				grid.add(tf);
    			}
    		}
    	}
	}
	
	
	/*
	 * addReportAll is the driver function behind the Add Reports action in the TrustGraphPanel. 
	 * 
	 * @params
	 * 		ArrayList<JTextField> t_formula: Array of all formula textfields.
	 * 		ArrayList<JTextField> t_result: Array of all result textfields
	 * 		DistanceState distance: DistanceState object represented by the TrustGraphPanel
	 * 
	 * @returns
	 * 		ArrayList<String> errormsg: All internal constraint error messages generated through the addReport process
	 */
	public static ArrayList<String> addReportAll(ArrayList<JTextField> t_formula, ArrayList<JTextField> t_result, ArrayList<JComboBox<String>> t_op, 
			ArrayList<JTextField> t_weights, DistanceState distance, TriangleInequalityResponse tri_res) {
		ArrayList<String> errormsg, newmsg;
		Report r;
		errormsg = new ArrayList<String>();
		double weight;
		String op;
		

		if (t_formula.size() != t_result.size() || t_formula.size() != t_result.size() || t_formula.size() != t_op.size())
		{
			System.out.println("addReport Bad");
			return errormsg;
		}
		
		//for every field available 
		//1. validate input
		//2. add report to distance object
		//3. record error messages
		for (int i = 0; i < t_formula.size(); i++)
		{
			if (validateReportInput(t_formula.get(i), t_result.get(i), t_weights.get(i), distance.getMap().getVocab()))
			{
				try {
					r = new Report(t_formula.get(i).getText(), Integer.parseInt(t_result.get(i).getText()));
					op = (String) t_op.get(i).getSelectedItem();
					weight = Double.parseDouble(t_weights.get(i).getText());
					
					newmsg = distance.addReport(r, op, weight, tri_res);
					errormsg.addAll(newmsg);
				} catch (Exception e) {
					errormsg.add(e.toString());
				}
			}
			else 
			{
				//if formula is empty, do not consider this report an 'error'.
				//this is just a report not to be considered
				///if ( (t_formula.get(i) == null && t_result.get(i) != null) || (t_formula.get(i) != null && t_result.get(i) == null) )
				if (!t_formula.get(i).getText().isEmpty())
					errormsg.add(Strings.errorReportInputInvalid(i+1));
			}
		}
		
		return errormsg;
	}
	
	/*
	 * addReport takes one of the user inputed reports, converts it into a Report object, and adds that report to the DistanceState object.
	 * This function should be run after input has been validated
	 * 
	 * @params
	 * 		String form: The formula parameter for a report object
	 * 		int res: The integer result of a report object
	 * 		DistanceState distance: The distance object the report will be added to
	 * 
	 * @returns
	 * 		ArrayList<String> errormsg: An array of strings containing any error messages that could have been generated by DistanceState constraints.
	 * 
	 */
//	private static ArrayList<String> addReport(Report r, DistanceState distance, TriangleInequalityResponse tri_res) throws ParserException {
//		ArrayList<String> errormsg = new ArrayList<String>();
//		
//		//add report to the distance object
//		//if input values are invalid, will throw an exception
//		errormsg = distance.addReport(r, tri_res);
//		
//		return errormsg;
//	}
	
	/*
	 * validateReportInput checks user input for a given reporting field. This function ensures that the correct type
	 * of input has been used by the user. This function is the first step in the Add Report Action.
	 * 
	 * @params
	 * 		JTextField t_formula: formula field for a report input
	 * 		JTextField t_result: result field for a report input		
	 * 
	 * @returns
	 * 		boolean: result of validation
	 */
	private static boolean validateReportInput(JTextField t_formula, JTextField t_result, JTextField t_weight, Set<Character> vocab) {
		String res_char, form_string;
		char symbol;
		
		//
		//if formula empty, not valid report
		//
		if (t_formula.getText().isEmpty())
			return false;
		
		
		//
		//Validate that result is one character and either 0 or 1
		//
		res_char = t_result.getText().trim();
		System.out.println(res_char);
		
		if (res_char.length() != 1 || (res_char.charAt(0) != '0' && res_char.charAt(0) != '1'))
			return false;
		
		//
		//Validate weight is a double
		//
		try {
			Double.parseDouble(t_weight.getText());
		} catch (NumberFormatException ex) {
			return false;
		}
		
		//
		//Validate that formula 
		//
		form_string = t_formula.getText().trim();
		System.out.println(form_string);
		
		for (int i = 0; i < form_string.length(); i++)
		{
			symbol = form_string.charAt(i);
			//if symbol alphabetic, but is not contained in the vocab, is not valid 
			if (Character.isAlphabetic(symbol) && !vocab.contains(symbol))
				return false;
			
			//if not a prop variable and not a defined prop symbol, not valid
			if (!Character.isAlphabetic(symbol) && !PropositionalSymbols.symbols.contains(Character.toString(symbol)))
				return false;
		}
		
		return true;
	}
	
	
	
	
}
