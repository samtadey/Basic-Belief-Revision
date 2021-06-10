/**
 * 
 */
package revision.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import constants.Strings;
import distance.DistanceState;
import distance.Report;
import language.BeliefState;
import language.State;
import revision.ui.handler.TrustGraphHandler;

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

        this.setBackground(Color.CYAN);

	}

	private Set<Character> getVocab(String input) throws Exception {
		Set<Character> vocab = new LinkedHashSet<Character>();
		
		for (String var : input.split(",")) 
	    {
	    	if (var.length() == 1)
	    		vocab.add(var.charAt(0));
	    	else
	    		throw new Exception("Bad Vocab Input");
	    }
		return vocab;
	}
	
	
	/*
	 * Adds the error message to the errors pane and resets the frame UI
	 * 
	 * @params
	 * 	String message - Error message to display on the error pane
	 */
//	private void addError(String message) {
//		JLabel err = new JLabel("Error: " + message);
//		err.setForeground(Color.RED);
//		errors.add(err);
//		//f.validate();
//	}
//	
//	/*
//	 * Clears the error pane of all messages
//	 */
//	private void clearErrors() {
//		errors.removeAll();
//		//f.validate();
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int grids;
        String s = e.getActionCommand();
        System.out.println("Action Trust Panel");
        System.out.println(s);
             
         
		if (s.equals(Strings.action_gen_trust_action))
    	{	       
			Set<Character> vars;
    		try {
    			//
    			//Create new distance object with user specified propositional variables
    			//
    			vars = getVocab(BeliefPanel.act.vocab.getText());
    			//validate vocab?
    			
            	distance = new DistanceState(vars);
            	grids = distance.getPossibleStates().getBeliefs().size();
            	
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
            }
    	}
    	else if (s.equals(Strings.report_add_report_action) && distance != null)
    	{

    		//Define error collection
    		ArrayList<String> errormsg;
    		//
    		//Add Reports to Trust Graph. Collect any logic errors
    		//
    		errormsg = TrustGraphHandler.addReportAll(ReportPanel.formulae, ReportPanel.results, distance);
    		
    		grids = distance.getPossibleStates().getBeliefs().size();
        	//
    		//Reset JTextAreas in grid before rebuild
    		//
    		grid_text = TrustGraphHandler.resetGridItems(grids);
    		//
    		//Rebuild Matrix with updated trust values
    		//
    		TrustGraphHandler.rebuildGrid(this, grid_text, distance);
    		//
    		//Refresh Frame
    		//
    		MainPanel.f.validate();
    		
    		System.out.println(errormsg);
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
		State s1, s2;
		Component tbox = e.getComponent();
		indy = (tbox.getX() / tbox.getWidth()) - 1;
		indx = (tbox.getY() / tbox.getHeight()) - 1;		
		//gets the index of for the text box where the event occurrred
		//seems in reverse, but produces the correct index
		System.out.print("Index: " + indx + " " + indy + " ");		
		
		//check whether input could be a double
		double w;
		try {
			w = Double.parseDouble(grid_text.get(indx).get(indy).getText());
			
			System.out.println("Value: " + w);
			//this works because its all in order

			s1 = distance.getPossibleStates().getBeliefs().get(indx);
			s2 = distance.getPossibleStates().getBeliefs().get(indy);
			
			//if invalid input, reset to previous value
			if (w < 0)
			{
				grid_text.get(indx).get(indy).setText(prev_box_val);
				MainPanel.f.validate();
			}
			else //set input value to distance object
			{
				distance.setDistance(s1, s2, w);
			}
		//if invalid input, reset to previous value
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			//addError("Invalid Grid Input");
			grid_text.get(indx).get(indy).setText(prev_box_val);
			MainPanel.f.validate();
		}
	}




}
