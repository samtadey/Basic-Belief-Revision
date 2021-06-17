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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.ArithmeticOperations;
import constants.Strings;
import revision.ui.handler.ErrorHandler;
import revision.ui.settings.UISettings;

/**
 * @author sam_t
 * The Report Panel is an iterface that allows users to input reporting formula's and results. When the add reports action is taken, these reports will
 * be used to modify the trust values in the TrustGraphPanel. 
 *
 */
public class ReportPanel extends JPanel implements ActionListener {
	
	static final int NUM_FIELDS = 5;
	static final int FORM_COL = 0;
	static final int RES_COL = 1;
	static final int OP_COL = 2;
	static final int WEIGHT_COL = 3;
	
	static final String DEFAULT_WEIGHT_VALUE = "1";
	
	JLabel formula, result, operation, weight;
	
	static ArrayList<JTextField> formulae;
	static ArrayList<JTextField> results;
	static ArrayList<JComboBox<String>> operations;
	static ArrayList<JTextField> weights;

	static JButton add_report_action;
	
	/*
	 * Report Panel Constructor
	 * Sets up the GUI 
	 */
	public ReportPanel(TrustGraphPanel graph) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		//this.setBackground(Color.pink);
		this.setBorder(UISettings.panelborder);
		
		formulae = new ArrayList<JTextField>();
		results = new ArrayList<JTextField>();
		operations = new ArrayList<JComboBox<String>>();
		weights = new ArrayList<JTextField>();
		
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
        
		operation = new JLabel("Operator");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = rightcol;
        gbc.weightx = 1;
        this.add(operation, gbc);
        
		weight = new JLabel("Weight");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = rightcol;
        gbc.weightx = 1;
        this.add(weight, gbc);
        
        
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
        


        //operations column
        JComboBox<String> op;
        for (int i = 1; i < NUM_FIELDS+1; i++)
        {
        	op = new JComboBox<String>(ArithmeticOperations.operators);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = OP_COL;
            gbc.gridy = i;
            gbc.insets = rightcol;
            gbc.weightx = 1;
        	op.addActionListener(this);
            operations.add(op);
            this.add(op, gbc);
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
            
        	res.setEditable(false);
        	try {
        		res.setText(handleResultValue(i-1));
        	} catch (Exception ex) {
        		res.setText("error");
        	}

            results.add(res);
            this.add(res, gbc);
        }
        
        //weights column
        JTextField weight;
        for (int i = 1; i < NUM_FIELDS+1; i++)
        {
        	weight = new JTextField();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = WEIGHT_COL;
            gbc.gridy = i;
            gbc.insets = rightcol;
            gbc.weightx = 1;
            
            weight.setText(DEFAULT_WEIGHT_VALUE);
        	
            weights.add(weight);
            this.add(weight, gbc);
        }
        
        //action button
        add_report_action = new JButton(Strings.report_add_report_action);
        gbc.gridx = FORM_COL;
        gbc.gridy = RES_COL + NUM_FIELDS;
        gbc.weightx = 0;
        gbc.insets = new Insets(50, 20, 10, 20);

        this.add(add_report_action, gbc);
        
        add_report_action.addActionListener(graph);
        
	}
	
	
	/**
	 * Changes the results textfield value to match the corresponding combobox value.
	 * Arithmetic operations that increase values return a "1"
	 * Operations that decrease values return a "0"
	 * 
	 * @param idx
	 * @return String
	 * @throws Exception if the index is out of bounds  
	 */
	private String handleResultValue(int idx) throws Exception {
		if (idx >= operations.size())
			throw new Exception("handleResultValue index out of bounds");
		
		String operator = (String) operations.get(idx).getSelectedItem();
		
		if (operator.equals(ArithmeticOperations.ADDITION)|| operator.equals(ArithmeticOperations.MULTIPLICATION))
			return "1";
		else //if (operator == ArithmeticOperations.SUBTRACTION || operator == ArithmeticOperations.DIVISION)
			return "0";
	}

	/**
	 * Changes a results textfield to correspond to the value of the combobox
	 * Addition, multiplication return 1
	 * Subtraction, division return 0
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		//System.out.println(action);
		int i = 0;
		
		if (action.equals("comboBoxChanged"))
		{
			//iterate through combobox objects until the one that triggered the action
			//is found
			for (Object o : operations)
			{
				if (e.getSource() == o)
					break;
				i++;
			}
			
			//if the combobox is found, update the corresponding result textfield value
			try {
				results.get(i).setText(handleResultValue(i));
			} catch (Exception ex) {
				ErrorHandler.addError(action, ex.getMessage());
			}
		}
		
		
	}
	
}
