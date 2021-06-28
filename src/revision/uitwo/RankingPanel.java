/**
 * 
 */
package revision.uitwo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
import javax.swing.JTextArea;

import constants.Strings;
import distance.RankingState;
import propositional_translation.InputTranslation;
import revision.ui.handler.ErrorHandler;
import revision.ui.handler.FileHandler;
import revision.ui.settings.UISettings;

/**
 * @author sam_t
 *
 */
public class RankingPanel extends JPanel implements ActionListener {
	
	static JTextArea bel;
	static JButton file_upload, file_generate;
	static JFileChooser file_up;
	
	static JPanel panel1, panel2, cardholder;
	static JLabel file_selected;
	
	static RankingState rankings_from_file;
	
	Insets leftcol = new Insets(5, 0, 0, 0);
	
	public RankingPanel() {
		
		GridBagConstraints gbc;
		
		//border layout fills to the outer layout size
		panel1 = new JPanel(new BorderLayout());
		panel2 = new JPanel(new GridBagLayout());
		
		cardholder = new JPanel(new CardLayout());
		
		this.setLayout(new BorderLayout());
		
		
		//first panel
		//manual belief input
		bel = new JTextArea(10,10);
		panel1.add(bel);
		panel1.setBorder(UISettings.panelborder);
		
		
		//second panel
		//file upload/generation actions
		file_upload = new JButton(Strings.ranking_file_choose);
        file_up = new JFileChooser();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        gbc.weighty = 1;
		panel2.add(file_upload, gbc);
		
		//setting up filechooser
        file_up = new JFileChooser();
        file_up.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        file_upload.addActionListener(this);
		
		//put name of file selected here
        
        file_selected = new JLabel();
        gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
		panel2.add(file_selected, gbc);
		
		file_generate = new JButton(Strings.ranking_file_generate);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = leftcol;
        gbc.weightx = 1;
        gbc.weighty = 4; //make the bottom button take up more space. Just visual
		panel2.add(file_generate, gbc);
		//panel2.setBackground(Color.red);
		
		
		cardholder.add(panel1, Strings.belief_combo_hamming);
		cardholder.add(panel2, Strings.belief_combo_file);
		
		this.add(cardholder);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
	    //file upload/generate actions
	    if (action.equals(Strings.ranking_file_choose))
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
                FileHandler read;
                try {
                    read = new FileHandler(file);
                    rankings_from_file = read.readFileToRanking(vars);
                    rankings_from_file.toConsoleGroupings();
                    file_selected.setText(file.getName());
                    read.closeFile();
                } catch (Exception ex) {
        			ErrorHandler.addError(Strings.file_open_file, ex.getMessage());
        			return;
                }
            } else {
            	System.out.println("Open command cancelled by user.");
            }
	    }
	    
	    //generate action
	    if (action.equals(Strings.ranking_file_generate))
	    {
	    	//generate example file
	    }
		
	}
	
	
	
}
