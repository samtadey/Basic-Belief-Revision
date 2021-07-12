/**
 * 
 */
package ca.bcit.tadey.revision.uitwo.handler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTextField;

import ca.bcit.tadey.revision.constants.PropositionalSymbols;
import ca.bcit.tadey.revision.constants.Strings;
import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.trust.DistanceState;
import ca.bcit.tadey.revision.trust.Report;
import ca.bcit.tadey.revision.trust.file.ReportFunction;
import ca.bcit.tadey.revision.uitwo.TrustGraphPanel;

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

	/**
	 * 
	 * @param t_formula
	 * @param t_result
	 * @param mod
	 * @param distance
	 * @param errormsg
	 * @return
	 * @throws Exception
	 */
	public static DistanceState addReportAll(ArrayList<JTextField> t_formula, ArrayList<JTextField> t_result, ReportFunction mod,
			DistanceState distance, ArrayList<String> errormsg) throws Exception {
		
		DistanceState update = new DistanceState(distance);
		Report report;

		for (int i = 0; i < t_formula.size(); i++)
		{
			//if formula not empty
			//validate input
			if (!t_formula.get(i).getText().isEmpty())
			{
				try {
					//invalid input will be reported in the error pane
					validateReportInput(t_formula.get(i), t_result.get(i), distance.getMap().getVocab());
					
					//create report object
					report = new Report(t_formula.get(i).getText(), Integer.parseInt(t_result.get(i).getText()));
				
					//run addreport
					update = update.addReport(report, mod, errormsg);
				} catch (Exception ex) {
					//add error to error list
					errormsg.add(Strings.errorReportInputInvalid(i+1, ex.getMessage()));
				}
			}
		}
		
		return update;
	}
	
	
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
	private static boolean validateReportInput(JTextField t_formula, JTextField t_result, Set<Character> vocab) throws Exception {
		String res_char, form_string;
		char symbol;
		
		//
		//Validate that result is one character and either 0 or 1
		//
		res_char = t_result.getText().trim();
		System.out.println(res_char);
		
		if (res_char.length() != 1 || (res_char.charAt(0) != '0' && res_char.charAt(0) != '1'))
			throw new Exception("Result must be a 0 or 1");
		
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
				throw new Exception("Character not in Propositional Vocabulary");
			
			//if not a prop variable and not a defined prop symbol, not valid
			if (!Character.isAlphabetic(symbol) && !PropositionalSymbols.symbols.contains(Character.toString(symbol)))
				throw new Exception("Invalid Symbol in Formula Input");
		}
		
		return true;
	}
	
	
	public static double checkTrustVal(JTextField trust_val) throws Exception {
		
		if (trust_val.getText().isBlank())
			return 0;
		
		try {
			return Double.parseDouble(trust_val.getText());
		} catch (Exception e) {
			throw new Exception(Strings.error_init_trust_value);
		}
		
	}
	
}
