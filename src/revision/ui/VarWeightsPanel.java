package revision.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import constants.Strings;
import propositional_translation.InputTranslation;
import revision.ui.handler.ErrorHandler;
import revision.ui.settings.UISettings;

public class VarWeightsPanel extends JPanel implements ActionListener {

	public static JScrollPane scroll;
	
	public static ArrayList<JLabel> var_lab;
	public static ArrayList<JTextField> var_weight;
	
	static Insets leftcol = new Insets(5, 5, 0, 5);
	static Insets rightcol = new Insets(5, 0, 0, 5);
	
	static JPanel gridbag;
	
	
	
	private static final String DEFAULT_TEXT_VAL = "1";
	
	public VarWeightsPanel() {
		
		//allows component to fill to outer layout size
		this.setLayout(new BorderLayout());

		gridbag = new JPanel(new GridBagLayout());
		
		scroll = new JScrollPane(gridbag);

		this.add(scroll);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		
		//set up panel members
		if (s.equals(Strings.action_gen_trust_action))
		{
			Set<Character> vars;
			
			var_lab = new ArrayList<JLabel>();
			var_weight = new ArrayList<JTextField>();
			
			gridbag.removeAll();
			
			try {
				//get variables
				vars = InputTranslation.getVocab(ActionPanel.vocab.getText());
				
			} catch (Exception ex) {
            	System.out.println(ex);
            	ErrorHandler.addError(Strings.action_gen_trust_action, ex.toString());
            	return;
			}
			
			JLabel lab;
			JTextField text;
			int row = 0;
	        GridBagConstraints gbc = new GridBagConstraints();
			
			for (Character c : vars)
			{
				lab = new JLabel(Character.toString(c));
				var_lab.add(lab);
		        gbc.fill = GridBagConstraints.HORIZONTAL;
		        gbc.gridx = 0;
		        gbc.gridy = row;
		        gbc.insets = leftcol;
		        gbc.weightx = 1;
		        gridbag.add(lab, gbc);
		        
		        text = new JTextField(5);
		        text.setText(DEFAULT_TEXT_VAL);
				var_weight.add(text);
		        gbc.fill = GridBagConstraints.HORIZONTAL;
		        gbc.gridx = 1;
		        gbc.gridy = row;
		        gbc.insets = rightcol;
		        gbc.weightx = 1;
		        gridbag.add(text, gbc);

		        ++row;
			}
			
			

    		//MainPanel.f.validate();
		}
		
	}

}
