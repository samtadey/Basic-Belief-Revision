/**
 * 
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import language.BeliefState;
import language.State;


/**
 * @author sam_t
 *
 */
public class BeliefRevision extends JFrame implements ActionListener {
	  
    // JFrame
    static JFrame f;
  
    // JButton
    static JButton b;
  
    // label to display text
    static JLabel l;
    static JLabel lbel;
    static JLabel lsent;
  
    // text area
    static JTextArea bel;
    static JTextArea sent;
    
    
    public static BeliefState parseInput(String input) {
    	
	   	BeliefState bstate = new BeliefState();     
	    for (String line : input.split("\n")) 
	    {
	    	bstate.addBelief(new State(line));;
	    }
	    return bstate;
    }
    
    public BeliefState revisionStates(BeliefState beliefs, BeliefState sentence) {
    	//returns a Belief state containing the states in the sentence with the minimum distance to any belief state
    	// [ 111 100 101] - belief state
        // [ 000 011 010 ] - sentence state - things we now believe to be true
    	// return states in the sentence with the min value to belief states
    	// [ 000 011 ] - both states have a min distance of 1 to at least one of the current belief states
    	
    	//ALLSAT should be done at this point
    	
    	BeliefState states = new BeliefState();
    	//store distance values for each sentense
    	int[] idx = new int[sentence.getBeliefs().size()];
    	int min, curr;
    	
    	min = findStateMinDistance(beliefs, sentence.getBeliefs().get(0));
    	idx[0] = min;
    	//
    	for (int i = 1; i < sentence.getBeliefs().size(); i++)
    	{
    		curr = findStateMinDistance(beliefs, sentence.getBeliefs().get(i));
    		if (curr < min)
    			min = curr;
    		idx[i] = curr;
    	}
    	
    	//add states with the min value to the new set
    	//how can I do this better
    	for (int i = 0; i < idx.length; i++)
    		if (idx[i] == min)
    			states.addBelief(sentence.getBeliefs().get(i));
    	
    	return states;
    	
    }
    
    public int findStateMinDistance(BeliefState beliefs, State sentense_state) {
    	int min, curr;
    	
    	if (beliefs.getBeliefs().size() < 1)
    		return -1;
    	
    	min = findDistance(beliefs.getBeliefs().get(0), sentense_state);
    	
    	for (int i = 1; i < beliefs.getBeliefs().size(); i++)
    	{
    		curr = findDistance(beliefs.getBeliefs().get(i), sentense_state);
    		if (curr < min)
    			min = curr;
    	}
    	
    	return min;
    }
    
    public int findDistance(State s1, State s2) {
    	int dist = 0;
    	if (s1.getState().length() != s2.getState().length())
    	{
    		System.out.println("States being compared aren't the same length");
    		return -1;
    	}
    	
    	for (int i = 0; i < s1.getState().length(); i++)
    		if (s1.getState().charAt(i) != s2.getState().charAt(i))
    			dist++;
    	
    	return dist;
    }
  
    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    { 	
    	BeliefState b, c, d;
        String s = e.getActionCommand();
        
        if (s.equals("submit")) 
        {              
            b = parseInput(bel.getText());
            System.out.println("Current Beliefs");
            b.toConsole();
            
            c = parseInput(sent.getText());
            System.out.println("New Sentence");
            c.toConsole();
            
            d = revisionStates(b,c);
            System.out.println("States with Min Distance");
            d.toConsole();
        }
    }
    

    // main class
    public static void main(String[] args)
    {
        // create a new frame to store text field and button
        f = new JFrame("textfield");
  
        // create a label to display text
        l = new JLabel("nothing entered");
        lbel = new JLabel("Beliefs");
        lsent = new JLabel("Sentence");
        // create a new button
        b = new JButton("submit");
  
        // create a object of the text class
        BeliefRevision te = new BeliefRevision();
  
        // addActionListener to button
        b.addActionListener(te);
  
        // create a text area, specifying the rows and columns
        bel = new JTextArea(10, 10);
        
        sent = new JTextArea(10,10);
        
  
        JPanel p = new JPanel();
  
        // add the text area and button to panel
        p.add(lbel);
        p.add(bel);
        p.add(lsent);
        p.add(sent);
        p.add(b);
        p.add(l);
  
        f.add(p);
        // set the size of frame
        f.setSize(500, 500);
  
        f.show();
    }
  


}
