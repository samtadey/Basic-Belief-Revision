/**
 * 
 */
package main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import distance.Distance;
import distance.HammingDistance;
import distance.WeightHammingDistance;
import language.BeliefState;
import language.State;


/**
 * @author sam_t
 *
 */
public class BeliefRevisionUI extends JFrame implements ActionListener {
	  
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
    
    static JComboBox t;
    
    /*
     * Take action when the button is pressed
     */
    public void actionPerformed(ActionEvent e)
    { 	
    	//BeliefRevison revise = new Be
    	BeliefState b, c, d;
        String s = e.getActionCommand();
        String combo;
        Distance distance;
        
        //retains order
    	Set<Character> chars = new LinkedHashSet<Character>();
    	chars.add('A');
    	chars.add('B');
    	chars.add('C');

    	HashMap<Character, Double> weights = new HashMap<Character, Double>();
    	weights.put('A', 1.0);
    	weights.put('B', 0.8);
    	weights.put('C', 0.5);
        
        
        if (s.equals("submit")) 
        {              
            b = BeliefRevision.parseInput(bel.getText());
            System.out.println("Current Beliefs");
            b.toConsole();
            
            c = BeliefRevision.parseInput(sent.getText());
            System.out.println("New Sentence");
            c.toConsole();
            
            combo = (String) t.getSelectedItem();
            switch(combo) {
            	case "Hamming":
            		distance = new HammingDistance(chars);
            		break;
            	case "Weighted Hamming":
            		distance = new WeightHammingDistance(chars, weights);
            		break;
            	default:
            		distance = new HammingDistance(chars);
            		
            }
            
            d = BeliefRevision.reviseStates(b,c, distance);
            System.out.println("States with Min Distance");
            d.toConsole();
        }
    }
    

    /*
     * UI Setup
     */
    public static void main(String[] args)
    {
    	String[] distance = {"Hamming", "Weighted Hamming"};
    	t = new JComboBox(distance);
        // create a new frame to store text field and button
        f = new JFrame("textfield");
  
        // create a label to display text
        l = new JLabel("");
        lbel = new JLabel("Beliefs");
        lsent = new JLabel("Sentence");
        // create a new button
        b = new JButton("submit");
  
        // create a object of the text class
        BeliefRevisionUI te = new BeliefRevisionUI();
  
        // addActionListener to button
        b.addActionListener(te);
  
        // create a text area, specifying the rows and columns
        bel = new JTextArea(10, 10);
        sent = new JTextArea(10,10);
        
        JPanel p = new JPanel();
  
        // add the text area and button to panel
        p.add(t);
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
