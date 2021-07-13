/**
 * 
 */
package ca.bcit.tadey.revision.uitwo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.bcit.tadey.revision.constants.Strings;
import ca.bcit.tadey.revision.translation.InputTranslation;
import ca.bcit.tadey.revision.trust.file.ReportFunction;
import ca.bcit.tadey.revision.trust.file.ReportFunctionReader;
import ca.bcit.tadey.revision.ui.settings.UISettings;
import ca.bcit.tadey.revision.uitwo.handler.ErrorHandler;

/**
 * @author sam_t
 * The Report Panel is an iterface that allows users to input reporting formula's and results. When the add reports action is taken, these reports will
 * be used to modify the trust values in the TrustGraphPanel. 
 *
 */
public class ReportPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int NUM_FIELDS = 5;
	static final int FORM_COL = 0;
	static final int RES_COL = 2;
	
	static final String DEFAULT_WEIGHT_VALUE = "1";
	
	JLabel formula, result, operation, weight, file_selected;
	
	static ArrayList<JTextField> formulae;
	static ArrayList<JTextField> results;

	static JButton add_report_action, upload_function_file;
	static JFileChooser file_up;
	
	static ReportFunction report_func;
	
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
		
		Insets leftcol = new Insets(10, 20, 0, 20);
		Insets rightcol = new Insets(10, 0, 0, 20);
		
		//labels for each column
		//formula
		//result
		formula = new JLabel(Strings.report_formula_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = FORM_COL;
        gbc.gridy = 0;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        this.add(formula, gbc);
        
		result = new JLabel(Strings.report_result_title);
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = RES_COL;
        gbc.gridy = 0;
        gbc.insets = rightcol;
        gbc.weightx = 1;
        this.add(result, gbc);
        
        
        //formulae column
        JTextField form;
        for (int i = 1; i < NUM_FIELDS+1; i++)
        {
        	form = new JTextField();
        	form.setToolTipText(Strings.tooltip_report_formula);
        	gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = FORM_COL;
            gbc.gridy = i;
            gbc.gridwidth = 2;
            gbc.insets = leftcol;
            //gbc.weightx = 5;
        	
            formulae.add(form);
            this.add(form, gbc);
        }
        
        
        //result column
        JTextField res;
        for (int i = 1; i < NUM_FIELDS+1; i++)
        {
        	res = new JTextField();
        	res.setToolTipText(Strings.tooltip_report_result);
        	gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = RES_COL;
            gbc.gridy = i;
            gbc.insets = rightcol;
            results.add(res);
            this.add(res, gbc);
        }
        
        
        //action button
        add_report_action = new JButton(Strings.report_add_report_action);
        add_report_action.setToolTipText(Strings.tooltip_add_report);
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = RES_COL + NUM_FIELDS;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(50, 20, 10, 20);
        this.add(add_report_action, gbc);
        
        //upload button
        upload_function_file = new JButton(Strings.report_upload_functions);
        upload_function_file.setToolTipText(Strings.tooltip_upload_func);
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = RES_COL + NUM_FIELDS;
        gbc.weightx = 0;
        gbc.insets = new Insets(50, 0, 10, 0);
        this.add(upload_function_file, gbc);
        
        add_report_action.addActionListener(graph);
        
        upload_function_file.addActionListener(this);
        file_up = new JFileChooser();
        file_up.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        //selected file label
        file_selected = new JLabel();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = RES_COL + NUM_FIELDS + 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
		this.add(file_selected, gbc);
        
	}
	
	

	/**
	 * Changes a results textfield to correspond to the value of the combobox
	 * Addition, multiplication return 1
	 * Subtraction, division return 0
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
	    //file upload/generate actions
	    if (action.equals(Strings.report_upload_functions))
	    {
			ArrayList<Character> vars;
	    	//RankingState rank;
			int returnVal;
    		//vocabulary must be set
    		//check
    		try {
    			vars = InputTranslation.getVocabAsList(ActionPanel.vocab.getText());
    		} catch (Exception ex) {
    			ErrorHandler.addError(Strings.file_open_file, Strings.error_bad_vocab_input);
    			return;
    		}
            
    		returnVal = RankingPanel.file_up.showOpenDialog(this);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = RankingPanel.file_up.getSelectedFile();
                //This is where a real application would open the file.
                ReportFunctionReader read;
                try {
                    read = new ReportFunctionReader(file, vars);
                    report_func = read.parseFile();
                    //report_func.toConsoleGroupings();
                    file_selected.setText(file.getName());
                    read.close();
                } catch (Exception ex) {
        			ErrorHandler.addError(Strings.file_open_file, ex.getMessage());
        			return;
                }
            } else {
            	System.out.println("Open command cancelled by user.");
            }
	    }
		
		
	}
	
}
