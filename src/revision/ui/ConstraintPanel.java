/**
 * 
 */
package revision.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import constants.Strings;
import distance.revision.TriangleInequalityOperator;
import distance.revision.TriangleInequalityResponse;
import distance.revision.TriangleInequalityResponseMiniMaxDist;
import distance.revision.TriangleInequalityResponseNextValid;
import distance.revision.TriangleInequalityResponseNoChange;
import revision.ui.settings.UISettings;

/**
 * @author sam_t
 *
 */
public class ConstraintPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static String button_name;
	
	static JRadioButton val_un, next_avail, minimax;
	//JTextField next_avail_text;
	ButtonGroup group;
	
	static TriangleInequalityResponse tri_res;

	public ConstraintPanel() {
		
        this.setLayout(new GridBagLayout());
		this.setBorder(UISettings.panelborder);
        GridBagConstraints gbc = new GridBagConstraints();
        
		//Insets leftcol = new Insets(10, 20, 0, 20);
        
        val_un = new JRadioButton(Strings.constr_value_unchange);
        //default selected
        val_un.setSelected(true);
        tri_res = new TriangleInequalityResponseNoChange(TriangleInequalityOperator.NO_CHANGE);
        
        button_name = val_un.getActionCommand();
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 20);
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(val_un, gbc);
        
        next_avail = new JRadioButton(Strings.constr_next_avail);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 20);
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(next_avail, gbc);
        
        minimax = new JRadioButton(Strings.constr_minimax_dist);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 20);
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(minimax, gbc);
        
//        next_avail_text = new JTextField(5);
//        next_avail_text.setEditable(false);
//        gbc.fill = GridBagConstraints.NONE;
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.insets = new Insets(0, 20, 0, 20);
//        gbc.weightx = 1;
//        //gbc.weighty = 1;
//        this.add(next_avail_text, gbc);
     
        
        group = new ButtonGroup();
        group.add(val_un);
        group.add(next_avail);
        group.add(minimax);
        
        val_un.addActionListener(this);
        next_avail.addActionListener(this);
        minimax.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(group.getSelection().toString());
		
		
		String action = e.getActionCommand();
		
		//sec active radio button choice
		//used during the Add Report Action
		button_name = action;
		
//		//allow textfield editable
		if (action.equals(Strings.constr_next_avail))
		{
	        tri_res = new TriangleInequalityResponseNextValid(TriangleInequalityOperator.NEXT_VALID);
		}
		else if (action.equals(Strings.constr_value_unchange))
		{
	        tri_res = new TriangleInequalityResponseNoChange(TriangleInequalityOperator.NO_CHANGE);
		}
		else if (action.equals(Strings.constr_minimax_dist))
		{
			tri_res = new TriangleInequalityResponseMiniMaxDist(TriangleInequalityOperator.MINI_MAX_DIST);
		}
	}
	
	
	
}
