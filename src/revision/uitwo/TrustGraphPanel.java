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
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.Strings;
import distance.DistanceState;
import distance.file.ReportFunction;
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
        Set<Character> vars;   
         
        //genereate default grid action
		if (s.equals(Strings.action_gen_trust_action))
    	{	       
    		try {
    			//propositional variables
    			vars = InputTranslation.getVocab(ActionPanel.vocab.getText());
    			
    			//create default trust graph
            	distance = new DistanceState(vars);
            	
            	//width and length of grid
            	grids = distance.getMap().getPossibleStates().getBeliefs().size();
            	
            	//reset grid UI items
            	grid_text = TrustGraphHandler.resetGridItems(grids);
            	
            	//set the grid's width and height
            	visual.setColumns(grids+1);
            	visual.setRows(grids+1);
            	
        		//Build trust graph with DistanceState object
            	TrustGraphHandler.rebuildGrid(this, grid_text, distance);

            	//Create the minimax DistanceState object
            	minimax = new DistanceState(vars);

        		//Refresh Frame
                MainPanel.f.validate();
            } catch (Exception ex) {
            	ErrorHandler.addError(Strings.action_gen_trust_action, ex.toString());
            }
    	}
		//add report action
    	else if (s.equals(Strings.report_add_report_action))
    	{	
    		if (!validMembers(distance))
    			return;

    		ArrayList<String> errormsg = new ArrayList<String>();
    		DistanceState upd;
    		ReportFunction mod;
    		
    		try {
    			//get propositional variables
    			vars = InputTranslation.getVocab(ActionPanel.vocab.getText());

    			//get ReportFunction object
    			if (ReportPanel.report_func != null)
    				mod = ReportPanel.report_func;
    			else
    				mod = new ReportFunction(); //default
    			
	    		//Add Reports to Trust Graph. Collect any logic errors in errormsg
	    		upd = TrustGraphHandler.addReportAll(ReportPanel.formulae, ReportPanel.results, mod, distance, errormsg);

	    		//set errors to errorpane
	    		ErrorHandler.addErrorGroup(Strings.report_add_report_action, errormsg);

	    		//set new DistanceState
	    		distance.setMap(upd.getMap());
	    		
	    		//Rebuild trust graph
	    		grids = distance.getMap().getPossibleStates().getBeliefs().size();
	    		grid_text = TrustGraphHandler.resetGridItems(grids);
	    		TrustGraphHandler.rebuildGrid(this, grid_text, distance);

	    		//set new minimax trust graph
	    		minimax.setMap(distance.miniMaxDistance());
	    		
	    		//refresh frame
	    		MainPanel.f.validate();
    		} catch (Exception ex) {
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
		double new_val;
		State s1, s2;
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
				//trust graph not null
	    		if (!validMembers(distance))
	    			return;
				
	    		//set value if value is valid
				distance.setMapMember(s1, s2, new_val);
				
				//set new value to UI
				grid_text.get(indx).get(indy).setText(TrustGraphHandler.setFormattedText(new_val));
				
				//set errors to pane
				ErrorHandler.addErrorGroup(Strings.action_trust_graph_manual, errors);
				
				//recalculate minimax
				minimax.setMap(distance.miniMaxDistance());
				
			}
			else
			{
				//reset to previous value
				resetManualInputTarget(indx,indy,Strings.action_trust_graph_manual, "Value " + new_val + " invalid");
			}
			
		//reset to previous value
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
