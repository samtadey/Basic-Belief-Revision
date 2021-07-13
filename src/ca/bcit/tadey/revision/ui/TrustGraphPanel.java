/**
 * 
 */
package ca.bcit.tadey.revision.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.bcit.tadey.revision.constants.Strings;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.translation.InputTranslation;
import ca.bcit.tadey.revision.trust.DistanceState;
import ca.bcit.tadey.revision.trust.constraint.TriangleInequalityResponse;
import ca.bcit.tadey.revision.ui.handler.ErrorHandler;
import ca.bcit.tadey.revision.ui.handler.TrustGraphHandler;
import ca.bcit.tadey.revision.ui.settings.UISettings;

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
	static DistanceState distance;
	
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
             
         
		if (s.equals(Strings.action_gen_trust_action))
    	{	       
			Set<Character> vars;
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
    		TriangleInequalityResponse tri_res = ConstraintPanel.tri_res;
    		
    		if (!validMembers(distance, tri_res))
    			return;

    		//Define error collection
    		ArrayList<String> errormsg = new ArrayList<String>();
    		HashMap<Character, Double> var_weights;
    		DistanceState upd;
    		try {
	    		//
	    		//Parse weights
	    		//
	    		var_weights = TrustGraphHandler.setVarWeights(VarWeightsPanel.var_lab, VarWeightsPanel.var_weight);
	    		//
	    		//Add Reports to Trust Graph. Collect any logic errors
	    		//
	    		upd = TrustGraphHandler.addReportAll(ReportPanel.formulae, ReportPanel.results, ReportPanel.weights, distance, tri_res, var_weights, errormsg);
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
	    		//Refresh Frame
	    		//
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
				tri_res = ConstraintPanel.tri_res;
				
				//checks important member objects are not null
				//sets error messages if null
	    		if (!validMembers(distance, tri_res))
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
	private boolean validMembers(DistanceState distance, TriangleInequalityResponse tri_res) {
		boolean isvalid = true;
		
		if (distance == null) 
		{
			//set error
			ErrorHandler.addError(Strings.report_add_report_action, Strings.report_add_report_action, Strings.error_gen_trust_prereq);
			isvalid = false;
		}
		
		if (tri_res == null)
		{
			ErrorHandler.addError(Strings.report_add_report_action, Strings.report_add_report_action, Strings.error_constraint_tri_eq);
			isvalid = false;
		}
		
		return isvalid;
	}


}
