/**
 * 
 */
package revision.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.Strings;

/**
 * @author sam_t
 * The Report Panel is an iterface that allows users to input reporting formula's and results. When the add reports action is taken, these reports will
 * be used to modify the trust values in the TrustGraphPanel. 
 *
 */
public class ReportPanel extends JPanel {
	
	static final int NUM_FIELDS = 5;
	static final int FORM_COL = 0;
	static final int RES_COL = 1;
	
	JLabel formula, result;
	
	ArrayList<JTextField> formulae;
	ArrayList<JTextField> results;

	JButton add_report_action;
	
	/*
	 * Report Panel Constructor
	 * Sets up the GUI 
	 */
	public ReportPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		this.setBackground(Color.pink);
		
		formulae = new ArrayList<JTextField>();
		results = new ArrayList<JTextField>();
		
		Insets leftcol = new Insets(10, 20, 0, 20);
		Insets rightcol = new Insets(10, 0, 0, 20);
		
		//labels for each column
		//formula
		//result
		formula = new JLabel(Strings.report_formula_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        this.add(formula, gbc);
        
		result = new JLabel(Strings.report_result_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = rightcol;
        gbc.weightx = 1;
        this.add(result, gbc);
        
        
        //formulae column
        JTextField form;
        for (int i = 1; i < NUM_FIELDS+1; i++)
        {
        	form = new JTextField();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = FORM_COL;
            gbc.gridy = i;
            gbc.insets = leftcol;
            gbc.weightx = 5;
        	
            formulae.add(form);
            this.add(form, gbc);
        }
        
        //result column
        JTextField res;
        for (int i = 1; i < NUM_FIELDS+1; i++)
        {
        	res = new JTextField();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = RES_COL;
            gbc.gridy = i;
            gbc.insets = rightcol;
            gbc.weightx = 1;
        	
            results.add(res);
            this.add(res, gbc);
        }

        
        //action button
        add_report_action = new JButton(Strings.report_add_report_action);
        gbc.gridx = FORM_COL;
        gbc.gridy = RES_COL + NUM_FIELDS;
        gbc.weightx = 0;
        gbc.insets = new Insets(50, 20, 10, 20);

        this.add(add_report_action, gbc);
        
	}
	
}
