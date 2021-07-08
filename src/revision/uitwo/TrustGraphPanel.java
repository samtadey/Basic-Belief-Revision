/**
 * 
 */
package revision.uitwo;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.Strings;
import constants.UIToOperatorPairs;
import distance.DistanceMap;
import distance.DistanceState;
import distance.VariableWeights;
import distance.constraint.TriangleInequalityOperator;
import distance.constraint.TriangleInequalityResponse;
import distance.constraint.TriangleInequalityResponseIgnore;
import distance.constraint.TriangleInequalityResponseNextValid;
import distance.constraint.TriangleInequalityResponseNoChange;
import distance.file.ReportFunction;
import distance.file.ReportFunctionReader;
import language.BeliefState;
import language.State;
import propositional_translation.InputTranslation;
import revision.uitwo.handler.ErrorHandler;
import revision.uitwo.handler.TrustGraphHandler;
import revision.ui.settings.UISettings;

/**
 * @author sam_t
 *
 */
public class TrustGraphPanel extends JPanel implements ActionListener, FocusListener {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5771430681979792609L;
	
	static JTextField vocab, t_formula, t_result;
	static JLabel l_vocab, formula, rep_res;
	static JButton gen_grid;
	
	static GridLayout visual;
	static DistanceState distance, minimax;
	
	static ArrayList<ArrayList<JTextField>> grid_text;
	
	static String prev_box_val;
	
	
	public TrustGraphPanel(MainPanel main) {
        
        //default
        visual = new GridLayout(3, 3);
        this.setLayout(visual);

        this.setBackground(Color.WHITE);
        this.setBorder(UISettings.panelborder);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		int grids;
        String s = e.getActionCommand();
        //System.out.println("Action Trust Panel");
        //System.out.println(s);
        Set<Character> vars;   
         
		if (s.equals(Strings.action_gen_trust_action))
    	{	       
			
    		try {
    			//
    			//Create new distance object with user specified propositional variables
    			//
    			vars = InputTranslation.getVocab(ActionPanel.vocab.getText());
    			//validate vocab?
    			
            	distance = new DistanceState(vars);
            	grids = distance.getMap().getPossibleStates().getBeliefs().size();
            	
            	//
            	//Init/Reset Grid JTextArea's
            	//
            	grid_text = TrustGraphHandler.resetGridItems(grids);
            	//
            	//Set GridLayout Row and Column values with possible states
            	//
            	visual.setColumns(grids+1);
            	visual.setRows(grids+1);
            	//
        		//Rebuild Matrix with updated trust values
        		//
            	TrustGraphHandler.rebuildGrid(this, grid_text, distance);
            	//
            	//Create MiniMax object
            	//
            	minimax = new DistanceState(vars);

        		//
        		//Refresh Frame
        		//
                MainPanel.f.validate();

            } catch (Exception ex) {
            	System.out.println(ex);
            	ErrorHandler.addError(Strings.action_gen_trust_action, ex.toString());
            }
    	}
    	else if (s.equals(Strings.report_add_report_action))
    	{
    		//String triangle_ineq_action;
    		//TriangleInequalityOperator op;
    		//TriangleInequalityResponse tri_res = new TriangleInequalityResponseIgnore(TriangleInequalityOperator.IGNORE);
    		
    		if (!validMembers(distance))
    			return;

    		//Define error collection
    		ArrayList<String> errormsg = new ArrayList<String>();
    		DistanceState upd;
    		ReportFunction mod;
    		
    		try {
    			vars = InputTranslation.getVocab(ActionPanel.vocab.getText());

    			//
    			//File will be uploaded before this point
    			//
    			if (ReportPanel.report_func != null)
    				mod = ReportPanel.report_func;
    			else
    				mod = new ReportFunction(); //default
    			
	    		//
	    		//Add Reports to Trust Graph. Collect any logic errors
	    		//
	    		upd = TrustGraphHandler.addReportAll(ReportPanel.formulae, ReportPanel.results, mod, distance, errormsg);
	    		System.out.println(errormsg);
	    		//
	    		//set errors to errorpane
	    		//
	    		ErrorHandler.addErrorGroup(Strings.report_add_report_action, errormsg);
	    		//System.out.println(errormsg);
	    		//set new DistanceState
	    		distance.setMap(upd.getMap());
	    		//
	    		//Rebuild Matrix with updated trust values
	    		//
	    		grids = distance.getMap().getPossibleStates().getBeliefs().size();
	    		grid_text = TrustGraphHandler.resetGridItems(grids);
	    		TrustGraphHandler.rebuildGrid(this, grid_text, distance);
	    		//
	    		//New minimax graph
	    		//
	    		minimax.setMap(distance.miniMaxDistance());
	    		
	    		MainPanel.f.validate();
    		} catch (Exception ex) {
    			System.out.println("Panel Handler");
            	ErrorHandler.addError(Strings.action_gen_trust_action, ex.toString());
    		}
    	}
		
	}
	
	
	/*
	 * Focus Actions
	 * 
	 * Focus Gained: Activates when the user clicks on a piece of the grid
	 * Focus Lost: Activates when the user clicks off a piece of the grid
	 */

	/*
	 * Stores the value of of the JTextArea that the user has interacted with
	 */
	@Override
	public void focusGained(FocusEvent e) {
		//store original box value
		int indy, indx;
		Component tbox = e.getComponent();
		indy = (tbox.getX() / tbox.getWidth()) - 1;
		indx = (tbox.getY() / tbox.getHeight()) - 1;
		prev_box_val = grid_text.get(indx).get(indy).getText();
	}

	/*
	 * Verifies the validity of user input.
	 * If input is valid, store the input in the distance object
	 * If input is not valid, use the stored value retrieved in focusGained to reset to the original value
	 */
	@Override
	public void focusLost(FocusEvent e) {
		int indx, indy;
		double upd_dist,current_dist, new_val;
		State s1, s2;
		//BeliefState invalid;
		TriangleInequalityResponse tri_res;
		ArrayList<String> errors = new ArrayList<String>();
		
		Component tbox = e.getComponent();
		indy = (tbox.getX() / tbox.getWidth()) - 1;
		indx = (tbox.getY() / tbox.getHeight()) - 1;		
		//gets the index of for the text box where the event occurrred
		//seems in reverse, but produces the correct index
		System.out.print("Index: " + indx + " " + indy + " ");		
		
		//check whether input could be a double
		try {
			new_val = Double.parseDouble(grid_text.get(indx).get(indy).getText());
			System.out.println("Value: " + new_val);
			//this works because its all in order
			s1 = distance.getMap().getPossibleStates().getBeliefs().get(indx);
			s2 = distance.getMap().getPossibleStates().getBeliefs().get(indy);
			
			//if invalid input, reset to previous value
			if (new_val > 0)
			{
				tri_res = new TriangleInequalityResponseIgnore(TriangleInequalityOperator.IGNORE);
				
				//checks important member objects are not null
				//sets error messages if null
	    		if (!validMembers(distance))
	    			return;
				
	    		current_dist = distance.getMap().getDistance(s1, s2);
				distance.setMapMember(s1, s2, current_dist, new_val, tri_res, errors);
				//newly calculated distance
				upd_dist = distance.getMap().getDistance(s1, s2);
				
				//set new value
				//grid_text.get(indx).get(indy).setText(Double.toString(upd_dist));
				grid_text.get(indx).get(indy).setText(TrustGraphHandler.setFormattedText(upd_dist));
				//set errors to pane
				ErrorHandler.addErrorGroup(Strings.action_trust_graph_manual, errors);
				
				//
				//recalculate minimax
				minimax.setMap(distance.miniMaxDistance());
				
//	    		System.out.println("Graph");
//	    		distance.getMap().stateToConsole();
//	    		System.out.println("MiniMax");
//	    		minimax.getMap().stateToConsole();
			}
			else
			{
				resetManualInputTarget(indx,indy,Strings.action_trust_graph_manual, "Value " + new_val + " invalid");
			}
			
		//if invalid input, reset to previous value
		} catch (Exception ex) {
			resetManualInputTarget(indx,indy,Strings.action_trust_graph_manual,ex.getMessage());
		}
	}


	/**
	 * The resetManualInputTarget resets a trust graph panel that has had invalid input entered into it.
	 * 
	 * @param indx x-index of the panel 
	 * @param indy y-index of the panel
	 * @param action action taken to display in the error pane
	 * @param error_message error message to display in the error pane
	 */
	private void resetManualInputTarget(int indx, int indy, String action, String error_message) {
		//adds message to error pane
		ErrorHandler.addError(action, error_message);
		//resets the text to the previous value
		grid_text.get(indx).get(indy).setText(prev_box_val);
	}
	
	/**
	 * Validates the DistanceState and TriangleInequalityResponse objects
	 * If either object is null, returns false and sets the ErrorPane
	 * 
	 * @param distance DistanceState object
	 * @param tri_res TriangleInequalityResponse object
	 * @return boolean indicating whether any of the object parameters are null
	 */
	private boolean validMembers(DistanceState distance) {
		boolean isvalid = true;
		
		if (distance == null) 
		{
			//set error
			ErrorHandler.addError(Strings.report_add_report_action, Strings.report_add_report_action, Strings.error_gen_trust_prereq);
			isvalid = false;
		}
		
		
		return isvalid;
	}


}
