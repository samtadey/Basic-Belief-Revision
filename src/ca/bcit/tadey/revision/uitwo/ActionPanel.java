/**
 * 
 */
package ca.bcit.tadey.revision.uitwo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.bcit.tadey.revision.constants.Strings;
import ca.bcit.tadey.revision.ui.settings.UISettings;

/**
 * @author sam_t
 * 
 * The ActionPanel is a UI that contains important fields and actions for the Belief Revision tool. The two main actions are the Generate Trust Grid action
 * and the Revise action. 
 * 
 * The Generate Trust Grid action creates the grid that displays distances between possible states. These values represent the trust the belief 
 * agent has in the reporting agent. These values are used in belief revision
 * 
 * The Revise action performs belief revision. Belief revision is accomplished by combining Initial Beliefs (found in the MainPanel) and Trust values. When a new
 * sentence is introduced (MainPanel) belief revision is performed using all the information the revision agent has available. The output of the revision is displayed
 * on the MainPanel in the Results text area
 * 
 */
public class ActionPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JTextField vocab, threshold, init_graph_val;
	
	static JButton gen_trust, revise, open_file;
	static JLabel actions, thresh_lab, init_val_lab;
	static JComboBox<String> revision_type;
	
	JFileChooser test;
	
	//static MainPanel main_panel;
	
	/*
	 * ActionPanel Constructor
	 * Sets up the panel GUI
	 */
	public ActionPanel(MainPanel main, BeliefPanel beliefpan) {

		this.setBorder(UISettings.panelborder);
		//this.setBackground(Color.RED);
		
		Insets leftcol = new Insets(10, 20, 0, 20);
		Insets toright = new Insets(10, 20, 0, 20);
		Insets label = new Insets(0, 20, 0, 20);
		Insets tbox = new Insets(0, 20, 0, 20);
		Insets bottom = new Insets(0,0,10,20);
		
		
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        actions = new JLabel(Strings.action_vocab_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 0, 20);
        //gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(actions, gbc);
		
        
		vocab = new JTextField();
		vocab.setToolTipText(Strings.tooltip_prop_vocab);
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = tbox;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(vocab, gbc);
		
        
        init_val_lab = new JLabel(Strings.action_init_title);
        gbc.fill = GridBagConstraints.BOTH;
//        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = label;
       // gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(init_val_lab, gbc);
        
		init_graph_val = new JTextField(5);
		init_graph_val.setToolTipText(Strings.tooltip_init_trust);
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = tbox;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(init_graph_val, gbc);
        
		gen_trust = new JButton(Strings.action_gen_trust_action);
		gen_trust.setToolTipText(Strings.tooltip_gen_trust);
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(0,0,0,20);
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(gen_trust, gbc);
       
        thresh_lab = new JLabel(Strings.action_thresh_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = label;
       // gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(thresh_lab, gbc);
        
		threshold = new JTextField(5);
		threshold.setEditable(false);
		threshold.setToolTipText(Strings.tooltip_threshold);
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0,20,10,20);
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(threshold, gbc);
        
		revision_type = new JComboBox<String>(Strings.revision_options);
		revision_type.setToolTipText(Strings.tooltip_rev_type);
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = bottom;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(revision_type, gbc);
        
        

		revise = new JButton(Strings.action_revise_action);
		revise.setToolTipText(Strings.tooltip_revise);
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.insets = bottom;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(revise, gbc);
        
        /*
         * Combobox listener
         */
        revision_type.addActionListener(this);
        
		/*
		 * The TrustGraphPanel class listens for this action
		 * When this action is taken a default grid is generated by the TrustGraphPanel, using the propositional vocabulary text field arguemnts.
		 */
		gen_trust.addActionListener(MainPanel.graph);

		
		/*
		 * The MainPanel class listens for this action.
		 * When this action is taken Initial Beliefs and Trust are used to perform belief revision.
		 */
		revise.addActionListener(beliefpan);
		
		
		
		

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		if (revision_type.getSelectedItem().equals(Strings.revision_general))
		{
			threshold.setEditable(false);
		}
		else if (revision_type.getSelectedItem().equals(Strings.revision_naive))
		{
			threshold.setEditable(true);
		}
	}


}
