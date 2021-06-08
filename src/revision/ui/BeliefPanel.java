/**
 * 
 */
package revision.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author sam_t
 *
 */
public class BeliefPanel extends JPanel {

	static JTextArea bel, sent, res;
	static JLabel bel_lab, sent_lab, res_lab;
	
	static ActionPanel act;
	
	public BeliefPanel(MainPanel main) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        	
		this.setBackground(Color.GREEN);
		
		bel_lab = new JLabel("Beliefs");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 40, 0, 20);
        gbc.weightx = 1;
        this.add(bel_lab, gbc);
		
        bel = new JTextArea(10,10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 40, 20, 40);
        gbc.weightx = 1;
        this.add(bel, gbc);
        
		sent_lab = new JLabel("Sentences");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 0, 20);
        gbc.weightx = 1;
        this.add(sent_lab, gbc);

        sent = new JTextArea(10,10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 40);
        gbc.weightx = 1;
        this.add(sent, gbc);
        
		bel_lab = new JLabel("Result");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 0, 20);
        gbc.weightx = 1;
        this.add(bel_lab, gbc);
        
        res = new JTextArea(10,10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 40);
        gbc.weightx = 1;
        this.add(res, gbc);

		bel_lab = new JLabel("Propositional Vocabulary");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 0, 20);
        gbc.weightx = 1;
        this.add(bel_lab, gbc);
        
        act = new ActionPanel(main, this);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 40);
        gbc.weightx = 1;
        this.add(act, gbc);
	}
}
