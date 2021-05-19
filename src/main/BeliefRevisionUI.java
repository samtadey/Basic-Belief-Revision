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

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import distance.Distance;
import distance.HammingDistance;
import distance.ParametrizedDifference;
import distance.RandomDistance;
import distance.WeightHammingDistance;
import language.BeliefState;
import language.State;
import revision_ui.VarListPanel;


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
    
    static JList list;
    //static DefaultListModel<Character> varlist;
    
    static VarListPanel<Character> varlistpanel;
    
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
    	chars.add('D');
    	chars.add('E');

    	HashMap<Character, Double> ham_weights = new HashMap<Character, Double>();
    	ham_weights.put('A', 0.8);
    	ham_weights.put('B', 0.8);
    	ham_weights.put('C', 0.7);
    	ham_weights.put('D', 0.7);
    	ham_weights.put('E', 0.5);
    	
    	Set<Character> para_order = new LinkedHashSet<Character>();
    	para_order.add('B'); // 1
    	para_order.add('E'); // 2
    	para_order.add('C'); // 3
    	para_order.add('A'); // 4
    	para_order.add('D'); // 5
    	HashMap<Character, Double> para_weights = new HashMap<Character, Double>();
    	//assigne para weights with order
    	double weight = 1.0;
    	for (Character ch: para_order)
    		para_weights.put(ch, weight++);
        
        
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
            		distance = new WeightHammingDistance(chars, ham_weights);
            		break;
            	case "Parametrized Difference":
            		distance = new ParametrizedDifference(chars, para_weights);
            		break;
            	case "Random Distance":
            		distance = new RandomDistance(chars);
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
        sent = new JTextArea(10, 10);
        
        JPanel main_panel = new JPanel();
        JPanel set_info = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        
        
        Set<Character> ch = new HashSet<Character>();
        ch.add('A');
        ch.add('B');
        ch.add('C');
        
        varlistpanel = new VarListPanel<Character>(ch);
  
        // add the text area and button to panel
        set_info.add(lbel);
        set_info.add(bel);
        set_info.add(lsent);
        set_info.add(sent);
        set_info.add(b);
        set_info.add(l);
        
        main_panel.add(set_info);
        main_panel.add(varlistpanel);

        f.add(main_panel);

        // set the size of frame
        f.setSize(1500, 500);
  
        f.show();
    }
  


}
