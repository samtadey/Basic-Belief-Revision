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

import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.ConjunctionOfClauses;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToCNF;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import constants.Strings;
import data.UiData;
import distance.DistanceState;
import language.BeliefState;
import language.State;
import propositional_translation.InputTranslation;
import revision_ui.ParametrizedDiffPanel;
import revision_ui.TestComp;
import revision_ui.VarListPanel;
import revision_ui.WeightedHammingPanel;
import solver.DPLL;
import solver.Formula;
import solver.FormulaSet;

/**
 * @author sam_t
 *
 */
public class BeliefRevisionUI extends JFrame implements ActionListener {
	  
    // JFrame
    static JFrame f;
  
    // JButton
    static JButton b, testsub;
  
    // label to display text
    static JLabel l;
    static JLabel lbel;
    static JLabel lsent;
    static JLabel output;
    static JLabel dist;
  
    // text area
    static JTextArea bel;
    static JTextArea sent;
    
    static JComboBox t;
    static JComboBox inputtype;
    
    static JList list;
    
    static VarListPanel varlistpanel;
    
    static TestComp test;
    
    static UiData data;
    
    static JButton cbutton;
    static JTextArea convert;
    
    
    private void doHamming(BeliefState beliefs, BeliefState sentence, DistanceState d) {
        State bel, sent;
        double dist;
        for (int i = 0; i < beliefs.getBeliefs().size(); i++)
        {
    		bel = beliefs.getBeliefs().get(i);
        	for (int j = 0; j < sentence.getBeliefs().size(); j++)
        	{
        		sent = sentence.getBeliefs().get(j);
        		//find distance
        		dist = BeliefRevision.getDistanceHamming(bel, sent);
        		//set distance to distance obj
        		d.setDistance(bel, sent, dist);
        	}
        }
    }
    
    
    private void doWeightHamming(BeliefState beliefs, BeliefState sentence, HashMap<Character, Double> weights, DistanceState d) {
        State bel, sent;
        double dist;
        for (int i = 0; i < beliefs.getBeliefs().size(); i++)
        {
    		bel = beliefs.getBeliefs().get(i);
        	for (int j = 0; j < sentence.getBeliefs().size(); j++)
        	{
        		sent = sentence.getBeliefs().get(j);
        		//find distance
        		dist = BeliefRevision.getDistanceWeightHamming(bel, sent, weights, d.getVocab());
        		//set distance to distance obj
        		d.setDistance(bel, sent, dist);
        	}
        }
    }
    
    private void doRandom(BeliefState beliefs, BeliefState sentence, DistanceState d) {
        State bel, sent;
        double dist;
        for (int i = 0; i < beliefs.getBeliefs().size(); i++)
        {
    		bel = beliefs.getBeliefs().get(i);
        	for (int j = 0; j < sentence.getBeliefs().size(); j++)
        	{
        		sent = sentence.getBeliefs().get(j);
        		//find distance
        		dist = BeliefRevision.getDistanceRandom(bel, sent);
        		//set distance to distance obj
        		d.setDistance(bel, sent, dist);
        	}
        }
    }
    

    
    /*
     * Take action when the button is pressed
     */
    public void actionPerformed(ActionEvent e)
    { 	
    	//BeliefRevison revise = new Be
    	BeliefState b, c, d;
        String s = e.getActionCommand();
        String combo, intype;
        DistanceState distance;
        
        //retains order
        //still not sure where these are coming from
    	Set<Character> chars = new LinkedHashSet<Character>();
    	chars.add('A');
    	chars.add('B');
    	chars.add('C');
    	HashMap<Character, Double> para_weights;
    	HashMap<Character, Double> ham_weights;
    	Set<Character> para_order;
    	
    	//
    	//how do we feed our set of variables
    	//

    	if (s.equals("Conversion"))
    	{
//    		FormulaSet formset;
    		BeliefState soln;
//    		
//    		//probs some checking here
//    		formset = InputTranslation.propToCNFForm(convert.getText(), chars);
//    		
//    		//for multiple we could just combine formsets for every line of input
//    		formset.toConsole();
//    		DPLL dpll = new DPLL();
//    		
//    		soln = dpll.allSatDpllBlock(formset);
//    		
//    		System.out.println("Solutions");
//    		soln.toConsole();		
    		soln = BeliefRevision.convertPropInput(convert.getText(), chars);
    		
    		output.setText(soln.toString());
    	}
    	
    	
        if (s.equals("submit")) 
        {       
        	System.out.println("Running Test");
        	
        	//handle type of input
        	intype = (String) inputtype.getSelectedItem();
        	System.out.println(intype);
        	
        	//handle incorrect input
        	//throw message if incorrect in else and return
        	
        	if (intype.equals(Strings.prop_input))
        	{
        		b = BeliefRevision.convertPropInput(bel.getText(), chars);
                System.out.println("Current Beliefs");
                b.toConsole();
                
                c = BeliefRevision.convertPropInput(sent.getText(), chars);
                System.out.println("New Sentence");
                c.toConsole();
        	}
        	else if (intype.equals(Strings.state_input))
        	{
        		b = BeliefRevision.parseInput(bel.getText());
                System.out.println("Current Beliefs");
                b.toConsole();
                
                c = BeliefRevision.parseInput(sent.getText());
                System.out.println("New Sentence");
                c.toConsole();
        	}
        	else
        	{
        		return;
        	}
        		//figure how to handle incorrect input
        	

            combo = (String) varlistpanel.getChooser().getSelectedItem();
            System.out.println(combo);
            
            distance = new DistanceState(chars);
            
            //error checking?
            
            switch(combo) {
            	case Strings.hamming:
            		doHamming(b,c,distance);
            		break;
            	case Strings.w_hamming:
            		//handle issue of no data
            		ham_weights = UiData.getVarWeights();
            		doWeightHamming(b,c,ham_weights,distance);
            		break;
            	case Strings.para:
            		para_order = UiData.getVarOrder();
            		//could add this function to the distance class
            		para_weights = new HashMap<Character, Double>();
            		double weight = 1.0;
                	for (Character ch: para_order)
                	{
                		System.out.println(ch);
                		para_weights.put(ch, weight++);
                	}
                	doWeightHamming(b,c,para_weights,distance);
                	//could just do the para calculation on construction
            		//distance = new ParametrizedDifference(chars, para_weights);
            		break;
            	case Strings.rand:
            		doRandom(b,c,distance);
            		break;
            	default:
            		doHamming(b,c,distance);    		
            }
     
            //set up a hamming function iterating through and setting all states
            //iterate through state combininations and set distances
      
            d = BeliefRevision.reviseStates(b,c, distance);
            System.out.println("States with Min Distance");
            d.toConsole();
            //dist.setText(d.);
            output.setText(d.toString());
        }
    }
    
    
    

    /*
     * UI Setup
     */
    public static void main(String[] args)
    {
        // create a new frame to store text field and button
        f = new JFrame("Belief Revision");
       
        // create a label to display text
        l = new JLabel("");
        lbel = new JLabel("Beliefs");
        lsent = new JLabel("Sentence");
        output = new JLabel("Output");
        dist = new JLabel("Revised States: ");
        // create a new button
        b = new JButton("submit");
        
    	//String[] input = {"Propositional Formula", "Belief States"};
    	inputtype = new JComboBox<String>(Strings.input_types);
        // create a object of the text class
        BeliefRevisionUI te = new BeliefRevisionUI();
        
        //don't know where this data is coming from
        Set<Character> ch = new LinkedHashSet<Character>();
        ch.add('A');
        ch.add('B');
        ch.add('C');
        //ch.add('D');
        //ch.add('E');
        
        
        UiData.setVarOrder(ch);
        varlistpanel = new VarListPanel(ch);

        //all listener functions get called in reverse order
        //this creates a problem where all of the panel functions are called, even if they are not
        //in use.
        b.addActionListener(te);
        b.addActionListener((WeightedHammingPanel) varlistpanel.getCardByString(Strings.w_hamming));
        b.addActionListener((ParametrizedDiffPanel) varlistpanel.getCardByString(Strings.para));
  
        // create a text area, specifying the rows and columns
        bel = new JTextArea(10, 10);
        sent = new JTextArea(10, 10);
        
        JPanel main_panel = new JPanel();
        JPanel set_info = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        
        
        // add the text area and button to panel
        set_info.add(inputtype);
        set_info.add(lbel);
        set_info.add(bel);
        set_info.add(lsent);
        set_info.add(sent);
        set_info.add(b);
        set_info.add(l);
        
        JPanel demo = new JPanel();
        demo.add(dist);
        demo.add(output);
        
        JPanel conversion = new JPanel();
        convert = new JTextArea(10, 10);
        cbutton = new JButton("Conversion");
        conversion.add(convert);
        conversion.add(cbutton);
        cbutton.addActionListener(te);
        
        //main panel adding sub panels
        main_panel.add(set_info);
        main_panel.add(varlistpanel);
        main_panel.add(demo);
        main_panel.add(conversion);
        

        f.add(main_panel);

        // set the size of frame
        f.setSize(700, 700);
  
        f.show();
    }
  


}
