/**
 * 
 */
package revision.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import constants.Strings;
import distance.DistanceState;
import distance.RankingState;
import language.BeliefState;
import main.BeliefRevision;
import propositional_translation.InputTranslation;
import revision.ui.settings.UISettings;

/**
 * @author sam_t
 *
 * The BeliefPanel class contains information pertaining to belief in the Belief Revision process. It contains initial beliefs, a sentence to revise by, and
 * the results of revision. 
 */
public class BeliefPanel extends JPanel implements ActionListener {

	static JTextArea bel, sent, res;
	static JLabel bel_lab, sent_lab, res_lab, vocab_lab;
	
	static ActionPanel act;
	
	/*
	 * BeliefPanel Constructor
	 * Sets up the Panel UI
	 */
	public BeliefPanel(MainPanel main) {
		
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        Insets labels = new Insets(10, 40, 0, 0);
        Insets allfieldsbutright = new Insets(5, 40, 20, 0);
        	
		//this.setBackground(Color.GREEN);
		
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
		
        bel = new JTextArea(10,10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = allfieldsbutright;
        gbc.weightx = 1;
        bel.setBorder(UISettings.componentborder);
        this.add(bel, gbc);
        
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
		System.out.println("Belief Panel");
		String bel_string, sent_string;
		RankingState bel_rank, updated_rank;
		BeliefState sent_state, bel_state;
		DistanceState dist;
		
		//get input
		bel_string = bel.getText();
		sent_string = sent.getText();
		
		//convert beliefs to RankingState
		bel_state = InputTranslation.convertPropInput(bel_string, TrustGraphPanel.distance.getVocab());
		bel_rank = new RankingState(bel_state, InputTranslation.setToArr(TrustGraphPanel.distance.getVocab()));
		
		sent_state = InputTranslation.convertPropInput(sent_string, TrustGraphPanel.distance.getVocab());
		
		dist = TrustGraphPanel.distance;
		
		updated_rank = BeliefRevision.reviseStates(bel_rank, sent_state, dist);
		//rustGraphPanel.distance
		StringBuilder output = new StringBuilder();
		//res.setText(updated_rank.getFormula().get(0));
		
		for (String s : updated_rank.getFormula())
		{
			output.append(s + "\n");
		}
		
		res.setText(output.toString());
	}
}
