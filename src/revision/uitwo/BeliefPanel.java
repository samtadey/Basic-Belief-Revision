/**
 * 
 */
package revision.uitwo;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import constants.Strings;
import constants.UIToOperatorPairs;
import distance.DistanceMap;
import distance.DistanceState;
import distance.RankingState;
import distance.revision.RevisionOperator;
import distance.revision.StateScoreResults;
import language.BeliefState;
import main.BeliefRevision;
import propositional_translation.InputTranslation;
import revision.uitwo.handler.ErrorHandler;
import revision.uitwo.handler.FileHandler;
import revision.ui.settings.UISettings;

/**
 * @author sam_t
 *
 * The BeliefPanel class contains information pertaining to belief in the Belief Revision process. It contains initial beliefs, a sentence to revise by, and
 * the results of revision. 
 */
public class BeliefPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String[] bel_input_choice = Strings.belief_combo;
	
	static JTextArea bel, sent, res;
	static JLabel bel_lab, sent_lab, res_lab, vocab_lab;
	static JComboBox<String> belief_input;
	static JButton file_upload;
	
	static ActionPanel act;
	static RankingPanel rank;
	
	private Insets allfieldsbutright;
	
	/*
	 * BeliefPanel Constructor
	 * Sets up the Panel UI
	 */
	public BeliefPanel(MainPanel main) {
		
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        Insets labels = new Insets(10, 40, 0, 0);
        allfieldsbutright = new Insets(5, 40, 20, 0);
        	
		//
		//Belief Column
		//
		bel_lab = new JLabel(Strings.belief_beliefs_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = labels;
        gbc.weightx = 1;
        this.add(bel_lab, gbc);
		
        
        rank = new RankingPanel();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = allfieldsbutright;
        gbc.weightx = 1;
        //rank.setBorder(UISettings.componentborder);
        this.add(rank, gbc);
        
        //Belief Type Combo Box
        belief_input = new JComboBox<String>(Strings.belief_combo);
        //belief_input.addItem(rank.getCardByString());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = allfieldsbutright;
        gbc.weightx = 1;
        //belief_input.setBorder(UISettings.componentborder);
        this.add(belief_input, gbc);
        //listener for combobox change

        belief_input.addActionListener(this);
        
        //
        //Sentences Column
        //
		sent_lab = new JLabel(Strings.belief_sentence_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = labels;
        gbc.weightx = 1;
        this.add(sent_lab, gbc);

        sent = new JTextArea(10,10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = allfieldsbutright;
        gbc.weightx = 1;
        sent.setBorder(UISettings.componentborder);
        this.add(sent, gbc);
        
        //
        //Results Column
        //
        res_lab = new JLabel(Strings.belief_result_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = labels;
        gbc.weightx = 1;
        this.add(res_lab, gbc);
        
        res = new JTextArea(10,10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = allfieldsbutright;
        gbc.weightx = 1;
        res.setBorder(UISettings.componentborder);
        this.add(res, gbc);

        //
        //Propositional Vocab and Action Column
        //
        vocab_lab = new JLabel(Strings.belief_action_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = labels;
        gbc.weightx = 1;
        this.add(vocab_lab, gbc);
        
        act = new ActionPanel(main, this);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 40, 20, 40); //need to add spacing for the right side for this component
        gbc.weightx = 1;
        this.add(act, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		String bel_string, sent_string, combo_item;
		RankingState bel_rank = null, updated_rank;
		BeliefState sent_state, bel_state;
		DistanceMap dist, minimax;
		Set<Character> vocab;
		BeliefRevision revise;
		StateScoreResults scores;
		RevisionOperator rev_op;
		double threshold = 0;
        //determines which Panel to display in the Ranking Panel
		//Based on the combobox selection
        CardLayout cl = (CardLayout)(RankingPanel.cardholder.getLayout());
	    cl.show(RankingPanel.cardholder, (String)belief_input.getSelectedItem());
	    

		if (action.equals(Strings.action_revise_action))
		{
			//validate input parameters
			//beliefs exist
			//sentence exists
			//distance object is not null
			if (TrustGraphPanel.distance == null || TrustGraphPanel.minimax == null)
			{
				//set error
				ErrorHandler.addError(Strings.action_revise_action, Strings.action_gen_trust_action, Strings.error_gen_trust_prereq);
				return;
			}
			
			try {
				dist = TrustGraphPanel.distance.getMap();
				minimax = TrustGraphPanel.minimax.getMap();
				vocab = dist.getVocab();
				
				//check revision choice
				if (ActionPanel.revision_type.getSelectedItem().equals(Strings.revision_general))
					rev_op = UIToOperatorPairs.revision.get(Strings.revision_general);
				else if (ActionPanel.revision_type.getSelectedItem().equals(Strings.revision_naive))
				{
					rev_op = UIToOperatorPairs.revision.get(Strings.revision_naive);
					//check threshold input
					//will throw error if not valid
					threshold = validThresh(ActionPanel.threshold.getText());
				}
				else
					throw new Exception("Revision Choice not set");
				
				
				
				combo_item = (String) belief_input.getSelectedItem();
				//check ranking combobox for type of input
				if (combo_item.equals(Strings.belief_combo_hamming))
				{
					bel_string = RankingPanel.bel.getText();
					bel_state = InputTranslation.convertPropInput(bel_string, vocab);
					bel_rank = new RankingState(bel_state, InputTranslation.setToArr(vocab));
				}
				else if (combo_item.equals(Strings.belief_combo_file))
				{
					bel_rank = RankingPanel.rankings_from_file;
				}
				
				//check that ranking function is not null
				if (bel_rank == null)
				{
					ErrorHandler.addError(Strings.action_revise_action, Strings.error_revise_no_ranking);
					return;
				}
				
				System.out.println("Threshold: " + threshold);
				
				//parse the sentence
				sent_string = sent.getText();
				//convert the sentence into a beliefstate
				sent_state = InputTranslation.convertPropInput(sent_string, vocab);
				
				//create belief revision object
				revise = new BeliefRevision(bel_rank, sent_state, dist, rev_op, threshold);
				//set score object 
				scores = revise.produceStateScoreResults(minimax);
				//produce updated ranking function based on scores
				updated_rank = revise.reviseStates(scores);
				
				//take updated ranking function
				//add it to the results textarea
				StringBuilder output = new StringBuilder();
				for (String s : updated_rank.getFormula())
					output.append(s + "\n");
				
				res.setText(output.toString());
				
			} catch (Exception ex) {
				System.out.println(ex.toString());
				ErrorHandler.addError(Strings.action_revise_action, ex.toString());
				//set error
			}


		}
	
	}
	
	/**
	 * 
	 * 
	 * @param thresh
	 * @return
	 * @throws Exception
	 */
	private double validThresh(String thresh) throws Exception {
		double val;
		
		try {
			val = Double.parseDouble(thresh);
		} catch (Exception ex) {
			throw new Exception("Threshold value must be a number");
		}
		
		if (val <= 0.0)
			throw new Exception("Threshold value must be greater than 0");
		
		return val;
	}
}
