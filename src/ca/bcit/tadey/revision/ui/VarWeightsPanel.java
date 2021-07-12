package ca.bcit.tadey.revision.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ca.bcit.tadey.revision.constants.Strings;
import ca.bcit.tadey.revision.translation.InputTranslation;
import ca.bcit.tadey.revision.trust.DistanceState;
import ca.bcit.tadey.revision.trust.VariableWeights;
import ca.bcit.tadey.revision.ui.handler.ErrorHandler;
import ca.bcit.tadey.revision.ui.settings.UISettings;

public class VarWeightsPanel extends JPanel implements ActionListener {

	public static JScrollPane scroll;
	
	public static ArrayList<JLabel> var_lab;
	public static ArrayList<JTextField> var_weight;
	
	public static JButton gen_weights;
	
	static Insets leftcol = new Insets(5, 5, 0, 5);
	static Insets rightcol = new Insets(5, 0, 0, 5);
	
	static JPanel gridbag;
	
	private static final String DEFAULT_TEXT_VAL = "1.00";
	
	//same as in TrustGraphHandle // could move to generate helper file
	static DecimalFormat dc = new DecimalFormat(Strings.text_format);
	
	public static String setFormattedText(double val) {
		return dc.format(val);
	}
	
	public VarWeightsPanel() {
		//allows component to fill to outer layout size
		this.setLayout(new BorderLayout());
		//set main panel within scrollpanel
		gridbag = new JPanel(new GridBagLayout());
		//set scrollpane
		scroll = new JScrollPane(gridbag);
		//add scrollpane to VarWeightsPanel object
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
			
			gen_weights = new JButton(Strings.action_gen_weights);
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.gridx = 0;
	        gbc.gridy = row;
	        gbc.insets = leftcol;
	        gbc.weightx = 1;
	        gbc.gridwidth = 2;
	        gridbag.add(gen_weights, gbc);
	        
	        gen_weights.addActionListener(this);
    		//MainPanel.f.validate();
		}
		else if (s.equals(Strings.action_gen_weights))
		{
			//generate weights from grid
			//get grid
			DistanceState distance = TrustGraphPanel.distance;
			VariableWeights vw = new VariableWeights(distance.getMap());
			HashMap<Character,Double> weights = vw.findVariableWeights();
			String lab;
			
			//check grid set
			if (distance == null || distance.getMap() == null)
			{
				ErrorHandler.addError(Strings.action_gen_weights, Strings.gen_weights_grid_null);
				return;
			}
			
			//find weights
			char c;
			//set the textfields with corresponding weight value
			for (int i = 0; i < var_lab.size(); i++)
			{
				//if something goes wrong with label set error and return
				lab = var_lab.get(i).getText();
				if (lab.length() != 1)
				{
					ErrorHandler.addError(Strings.action_gen_weights, "Something went wrong with labels");
					return;
				}
				//get label
				c = var_lab.get(i).getText().charAt(0);
				//find weight and set to textfield
				var_weight.get(i).setText(setFormattedText(weights.get(c)));
			}
		}
	}
}
