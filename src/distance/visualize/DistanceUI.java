/**
 * 
 */
package distance.visualize;

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

import distance.DistanceState;
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
	
	static JTextField vocab;
	
	static JLabel l_vocab;
	static JLabel formula;
	static JLabel rep_res;
	
	static JButton gen_grid;
	
	static GridLayout visual;
	
	private static DistanceState distance;
	
	ArrayList<ArrayList<JTextField>> grid_text;
	private static double[][] dist_val;
	
//	public DistanceUI(Set<Character> vocab) {
//        distance = new DistanceState(vocab);
//	}
	
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
	
	private void updateGridUI() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        Set<Character> vars;
        BeliefState allstates;
        System.out.println("Action");
        
        if (s.equals("Print Distances"))
        {
        	//throws
        	for (int i = 0; i < dist_val.length; i++)
        	{
        		System.out.print("[ ");
        		for (int j = 0; j < dist_val[i].length; j++)
        		{
        			System.out.print(" " + dist_val[i][j] + ",");
        		}
        		System.out.println(" ]");
        	}
        }
        
    	if (s.equals("Generate Default Grid"))
    	{
    		try {
            	vars = getVocab(vocab.getText());
            	distance = new DistanceState(vars);
            	int grids = distance.getPossibleStates().getBeliefs().size();
            	
            	visual.setColumns(grids+1);
            	visual.setRows(grids+1);
            	//visual = new GridLayout(grids, grids);
            	//grid.setLayout(visual);
            	//map states combinations to the grid
            	allstates = distance.getPossibleStates();
            	
            	grid.removeAll();
            	
            	dist_val = new double[grids][grids];
            	grid_text = new ArrayList<ArrayList<JTextField>>();
            	
            	State s1,s2;
            	JTextField tf;
            	double dist;
            	
            	//init text field arrays
            	for (int i = 0; i < grids; i++)
            		grid_text.add(new ArrayList<JTextField>());
            	
            	//
            	//Set up grid
            	//set header bar
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
            	
            	reports.removeAll();
            	
                formula = new JLabel("Formula:");
                reports.add(formula);
                reports.add(new JTextField(10));
                rep_res = new JLabel("Result:");
                reports.add(rep_res);
                reports.add(new JTextField(2));
                JButton addrep = new JButton("Add Report");
                reports.add(addrep);
                JButton print = new JButton("Print Distances");
                reports.add(print);
                
                addrep.addActionListener(this);
                print.addActionListener(this);

            	//done setting up grid
            	//update?
                f.validate();

            	
            } catch (Exception ex) {
            	System.out.println(ex);
            }
    		//this.distance = new DistanceState()
    	}
    	else if (s.equals("Add Report"))
    	{
    		System.out.println("Hey there");
    		// if formula or result not valid input
    		//message to form
    		
    		//else all good
    		
    		String form;
    		int res;
    		
    		form = formula.getText();
    		res = Integer.parseInt(rep_res.getText());
    		Report r = new Report(form, res);
    		
    		distance.addReport(r);
    		
    		//update matrix
    	}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        f = new JFrame("Distance Visualizer");
        
        DistanceUI di = new DistanceUI();
        
        main_panel = new JPanel();
        top = new JPanel();
        grid = new JPanel();
        reports = new JPanel();
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
        
        f.add(main_panel);

        // set the size of frame
        f.setSize(1000, 1000);
        f.show();

	}

	@Override
	public void focusGained(FocusEvent e) {
		//System.out.println(e.getComponent());
		
		//nothing right now
	}

	//want to check the weight after the user is done with it
	//when a user selects a button for an action, this will record the last event on a text box as well
	@Override
	public void focusLost(FocusEvent e) {
		int indx, indy;
		Component tbox = e.getComponent();
		indy = (tbox.getX() / tbox.getWidth()) - 1;
		indx = (tbox.getY() / tbox.getHeight()) - 1;
		
		//gets the index of for the text box where the event occurrred
		//seems in reverse, but produces the correct index
		System.out.println(indx + " " + indy);
		
		String weight = grid_text.get(indx).get(indy).getText();
		double w = Double.parseDouble(weight);
		
		//do checking for value exceptions
		if (w < 0) 
		{
			//reset grid state
			//set error message
			return;
		}
		//check triangle ineq
		
		dist_val[indx][indy] = w;
		
		//this works because its all in order
		State s1, s2;
		s1 = distance.getPossibleStates().getBeliefs().get(indx);
		s2 = distance.getPossibleStates().getBeliefs().get(indy);
		
		//do some checking here for exceptions
		distance.setDistance(s1, s2, w);
		distance.stateToConsole();
		//use index to change state distances
	}




}
