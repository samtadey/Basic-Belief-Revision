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

/**
 * @author sam_t
 *
 */
public class ActionPanel extends JPanel implements ActionListener {

	JTextField vocab;
	
	JButton gen_trust;
	JButton revise;
	
	//static MainPanel main_panel;
	
	public ActionPanel(MainPanel main, BeliefPanel beliefpan) {
		//main_panel = main;
		this.setBackground(Color.RED);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		vocab = new JTextField(10);
		gen_trust = new JButton("Generate Trust Graph");
		revise = new JButton("Revise");
		
		revise.addActionListener(main);
		revise.addActionListener(this);
		
		this.add(vocab);
		this.add(gen_trust);
		this.add(revise);
		
		//set context in internal handler ? in main
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action Taken ActionPanel");
		BeliefPanel.sent.setText(e.getActionCommand().toString());
	}
}
