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
	
	
	
	static JTextField vocab;
	static JTextField t_formula;
	static JTextField t_result;
	
	static JLabel l_vocab;
	static JLabel formula;
	static JLabel rep_res;
	
	static JButton gen_grid;
	
	static GridLayout visual;
	
	private static DistanceState distance;
	private static TrustGraphHandler trusthandle;
	
	ArrayList<ArrayList<JTextField>> grid_text;
	private static double[][] dist_val;
	
	static String prev_box_val;
	
	
	public TrustGraphPanel(MainPanel main) {
		 
        trusthandle = new TrustGraphHandler();
        
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
	
	private void rebuildGrid(BeliefState allstates) {
		
		trusthandle.rebuildGrid(this, this, allstates, dist_val, grid_text, distance);
		
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
        String s = e.getActionCommand();
        Set<Character> vars;
        BeliefState allstates;
        System.out.println("Action Trust Panel");
        System.out.println(s);
             
		//clearErrors();
         
    	//if (s.equals("Generate Default Grid"))
		if (s.equals("Generate Trust Graph"))
    	{	
    		
    		//sets up a default grid for the UI
    		//resets all structures
    		
    		//check if action comes from the gen trust graph button
    		//access MainPanel -> BeliefPanel -> ActionPanel -> prop_vocab textfield
    		
    		
    		try {
    			
    			//must change at some point
    			vars = getVocab(MainPanel.belief_panel.act.vocab.getText());
    			
            	//vars = getVocab(vocab.getText());
            	distance = new DistanceState(vars);
            	int grids = distance.getPossibleStates().getBeliefs().size();
            	
            	visual.setColumns(grids+1);
            	visual.setRows(grids+1);

            	allstates = distance.getPossibleStates();

            	rebuildGrid(allstates);

                MainPanel.f.validate();

            	
            } catch (Exception ex) {
            	System.out.println(ex);
            	//addError(ex.getMessage());
            }
    		//this.distance = new DistanceState()
    	}
    	//Adds a report to the current grid
    	//updates the grid with newly calculated values
    	//displays error conditions to the screen
    	else if (s.equals("Add Report"))
    	{
    		boolean error = false;
    		String form, res_char;
    		int res = -1;
    		Report r;
    		ArrayList<String> errormsg = new ArrayList<String>();
    		
    		res_char = t_result.getText().trim();
    		System.out.println(res_char);
    		//validate number value input
    		if (res_char.length() != 1 || (res_char.charAt(0) != '0' && res_char.charAt(0) != '1'))
    		{
    			//addError("Invalid Input: Result - Allowable input, 0 or 1");
    			error = true;
    		}

    		//validate prop formula parsing
    		form = t_formula.getText();

    		try {
    			res = Integer.parseInt(t_result.getText().trim());
    			r = new Report(form, res);
    			errormsg = distance.addReport(r);
    		} catch (Exception ex) {
    			//addError(ex.getMessage());
    			error = true;
    		}
    		
    		if (error)
    			return;
    		
    		//build error message pane
    		for (int i = 0; i < errormsg.size(); i++)
    			//addError(errormsg.get(i));
    		
    		//rebuild matrix
    		rebuildGrid(distance.getPossibleStates());
    		//f.validate();
    		MainPanel.f.validate();
    	}
		
	}
	

	/*
	 * 
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
	 * 
	 */
	@Override
	public void focusLost(FocusEvent e) {
		
		//clearErrors();
		
		int indx, indy;
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
			State s1, s2;
			s1 = distance.getPossibleStates().getBeliefs().get(indx);
			s2 = distance.getPossibleStates().getBeliefs().get(indy);
			
			//if invalid input, reset to previous value
			if (w < 0)
			{
				//addError("Attempting to set distance below 0");
				grid_text.get(indx).get(indy).setText(prev_box_val);
				//f.validate();
			}
			else //set input value to distance object
			{
				//arr used for printing and viewing
				dist_val[indx][indy] = w;
				distance.setDistance(s1, s2, w);
			}
		//if invalid input, reset to previous value
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			//addError("Invalid Grid Input");
			grid_text.get(indx).get(indy).setText(prev_box_val);
			//f.validate();
		}
	}




}
