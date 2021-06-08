/**
 * 
 */
package revision.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.Strings;

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

	JTextField vocab;
	
	JButton gen_trust;
	JButton revise;
	
	//static MainPanel main_panel;
	
	/*
	 * ActionPanel Constructor
	 * Sets up the panel GUI
	 */
	public ActionPanel(MainPanel main, BeliefPanel beliefpan) {

		this.setBackground(Color.RED);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		vocab = new JTextField(10);
		gen_trust = new JButton(Strings.action_gen_trust_action);
		revise = new JButton(Strings.action_revise_action);
		

		//order of execution
		//this
		//MainPanel
		
		/*
		 * The TrustGraphPanel class listens for this action
		 * When this action is taken a default grid is generated by the TrustGraphPanel, using the propositional vocabulary text field arguemnts.
		 */
		gen_trust.addActionListener(MainPanel.graph);
		gen_trust.addActionListener(this);
		
		/*
		 * The MainPanel class listens for this action.
		 * When this action is taken Initial Beliefs and Trust are used to perform belief revision.
		 */
		revise.addActionListener(main);
		revise.addActionListener(this);
		
		this.add(vocab);
		this.add(gen_trust);
		this.add(revise);
		
	}

	/*
	 * ActionListener for the ActionPanel. 
	 * Listeners: 
	 * 		MainPanel - setting the result textarea field
	 * 		TrustGraphPanel = se
	 * 				
	 * 
	 * @params
	 * 	ActionEvent e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action Taken ActionPanel");
		BeliefPanel.sent.setText(e.getActionCommand().toString());
		
		String action = e.toString();
		
	}
}
