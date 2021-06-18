/**
 * 
 */
package distance.visualize;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import distance.DistanceMap;
import distance.Report;
import language.BeliefState;
import language.State;

/**
 * @author sam_t
 *
 */
public class DistanceUI extends JFrame implements ActionListener, FocusListener {
	
	static JFrame f;
	
	static JPanel main_panel;
	static JPanel top;
	static JPanel grid;
	static JPanel reports;
	static JPanel errors;
	
	static JTextField vocab;
	static JTextField t_formula;
	static JTextField t_result;
	
	static JLabel l_vocab;
	static JLabel formula;
	static JLabel rep_res;
	
	static JButton gen_grid;
	
	static GridLayout visual;
	
	private static DistanceMap distance;
	
	ArrayList<ArrayList<JTextField>> grid_text;
	private static double[][] dist_val;
	
	static String prev_box_val;
	

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
		State s1, s2;
		double dist;
		JTextField tf;
		int grids = allstates.getBeliefs().size();
		
		grid.removeAll();
		
		dist_val = new double[grids][grids];
    	grid_text = new ArrayList<ArrayList<JTextField>>();
    	
    	//init text field arrays
    	for (int i = 0; i < grids; i++)
    		grid_text.add(new ArrayList<JTextField>());
    	
    	
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
    				dist = distance.getDistance(s1, s2);
    				tf = new JTextField(Double.toString(dist));
    				if (i == j || j > i)
    					tf.setEditable(false);
    				
    				//add listener
    				tf.addFocusListener(this);
    				//add distance to data structure
    				dist_val[i][j] = dist;
    				//add to jtext array // this box maps to the distance array
    				grid_text.get(i).add(tf);
    				//add to ui
    				grid.add(tf);
    			}
    		}
    	}
	}
	
	/*
	 * Adds the error message to the errors pane and resets the frame UI
	 * 
	 * @params
	 * 	String message - Error message to display on the error pane
	 */
	private void addError(String message) {
		JLabel err = new JLabel("Error: " + message);
		err.setForeground(Color.RED);
		errors.add(err);
		f.validate();
	}
	
	/*
	 * Clears the error pane of all messages
	 */
	private void clearErrors() {
		errors.removeAll();
		f.validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        Set<Character> vars;
        BeliefState allstates;
        System.out.println("Action");
             
		clearErrors();
         
    	if (s.equals("Generate Default Grid"))
    	{	
    		
    		//sets up a default grid for the UI
    		//resets all structures
    		try {
            	vars = getVocab(vocab.getText());
            	distance = new DistanceMap(vars);
            	int grids = distance.getPossibleStates().getBeliefs().size();
            	
            	visual.setColumns(grids+1);
            	visual.setRows(grids+1);

            	allstates = distance.getPossibleStates();

            	rebuildGrid(allstates);
            	        
            	//set up reports area
            	reports.removeAll();

                reports.add(new JLabel("Formula:"));
                t_formula = new JTextField(10);
                reports.add(t_formula);

                reports.add(new JLabel("Result:"));
                
                t_result = new JTextField(2);
                reports.add(t_result);
                JButton addrep = new JButton("Add Report");
                reports.add(addrep);
                
                addrep.addActionListener(this);
                //print.addActionListener(this);

            	//done setting up grid
            	//update?
                f.validate();

            	
            } catch (Exception ex) {
            	System.out.println(ex);
            	addError(ex.getMessage());
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
    			addError("Invalid Input: Result - Allowable input, 0 or 1");
    			error = true;
    		}

    		//validate prop formula parsing
    		form = t_formula.getText();

    		try {
    			res = Integer.parseInt(t_result.getText().trim());
    			r = new Report(form, res);
    			errormsg = distance.addReport(r);
    		} catch (Exception ex) {
    			addError(ex.getMessage());
    			error = true;
    		}
    		
    		if (error)
    			return;
    		
    		//build error message pane
    		for (int i = 0; i < errormsg.size(); i++)
    			addError(errormsg.get(i));
    		
    		//rebuild matrix
    		rebuildGrid(distance.getPossibleStates());
    		f.validate();
    	}
		
	}
	

	/**
	 * Sets up default JFrame and JPanel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        f = new JFrame("Distance Visualizer");
        
        DistanceUI di = new DistanceUI();
        
        main_panel = new JPanel();
        top = new JPanel();
        grid = new JPanel();
        reports = new JPanel();
        errors = new JPanel();
        
        errors.setLayout(new BoxLayout(errors, BoxLayout.Y_AXIS));
        //top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        
        l_vocab = new JLabel("Propositional Variables:");
        vocab = new JTextField(10);
        gen_grid = new JButton("Generate Default Grid");
        
        
        top.add(l_vocab);
        top.add(vocab);
        top.add(gen_grid);
        
        gen_grid.addActionListener(di);
        //defau;t
        visual = new GridLayout(3, 3);
        grid.setLayout(visual);
        
        main_panel.add(top);
        main_panel.add(grid);
        main_panel.add(reports);
        main_panel.add(errors);
        
        f.add(main_panel);

        // set the size of frame
        f.setSize(1000, 1000);
        f.show();

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
		
		clearErrors();
		
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
				addError("Attempting to set distance below 0");
				grid_text.get(indx).get(indy).setText(prev_box_val);
				f.validate();
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
			addError("Invalid Grid Input");
			grid_text.get(indx).get(indy).setText(prev_box_val);
			f.validate();
		}
	}




}
