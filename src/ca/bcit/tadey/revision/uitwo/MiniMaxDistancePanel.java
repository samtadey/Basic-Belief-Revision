/**
 * 
 */
package ca.bcit.tadey.revision.uitwo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.bcit.tadey.revision.constants.Strings;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.ui.settings.UISettings;
import ca.bcit.tadey.revision.uitwo.handler.ErrorHandler;

/**
 * @author sam_t
 *
 */
public class MiniMaxDistancePanel extends JPanel implements ActionListener {

	JLabel s1_lab, s2_lab, result_lab;
	JTextField s1_box, s2_box, result;
	JButton action;
	
	static DecimalFormat dc = new DecimalFormat(Strings.text_format);
	/**
	 * 
	 */
	public MiniMaxDistancePanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		//this.setBackground(Color.pink);
		this.setBorder(UISettings.panelborder);
		
		Insets leftcol = new Insets(10, 20, 0, 20);
		Insets rightcol = new Insets(10, 0, 0, 20);
		
		
		//labels for each column
		//formula
		//result
		s1_lab = new JLabel(Strings.mmd_state1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        this.add(s1_lab, gbc);
        
		s2_lab = new JLabel(Strings.mmd_state2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        this.add(s2_lab, gbc);
        
		result_lab = new JLabel(Strings.mmd_result);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        this.add(result_lab, gbc);
        
		action = new JButton(Strings.mmd_action);
		action.setToolTipText(Strings.tooltip_find_dist);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        this.add(action, gbc);
        
        //right column
		s1_box = new JTextField(10);
		s1_box.setToolTipText(Strings.tooltip_state);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = rightcol;
        gbc.weightx = 2;
        this.add(s1_box, gbc);
        
		s2_box = new JTextField(10);
		s2_box.setToolTipText(Strings.tooltip_state);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = rightcol;
        gbc.weightx = 2;
        this.add(s2_box, gbc);
        
		result = new JTextField(10);
		result.setToolTipText(Strings.tooltip_state_dist);
		result.setEditable(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = rightcol;
        gbc.weightx = 2;
        this.add(result, gbc);
        
        
        action.addActionListener(this);
        
	}
	
	public static String setFormattedText(double val) {
		return dc.format(val);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		
		if (s.equals(Strings.mmd_action))
		{
			State s1, s2;
			double dist;
			
			//check null
			if (TrustGraphPanel.minimax == null)
			{
				ErrorHandler.addError(Strings.mmd_action, "MiniMax object not set");
				return;
			}
			
			//check valid states to create
			try {
				s1 = new State(s1_box.getText());
				s2 = new State(s2_box.getText());
			} catch (Exception ex) {
				ErrorHandler.addError(Strings.mmd_action, "Bad State Input");
				return;
			}
			
			//check states are valid within the DistanceState object
			try {
				dist = TrustGraphPanel.minimax.getMap().getDistance(s1, s2);
			} catch (Exception ex) {
				ErrorHandler.addError(Strings.mmd_action, "State doesn't exist in MiniMax graph");
				return;
			}
			
			result.setText(setFormattedText(dist)); 
		}
	}

}
