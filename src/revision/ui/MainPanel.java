/**
 * 
 */
package revision.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Strings;

/**
 * @author sam_t
 *
 */
public class MainPanel implements ActionListener {
	
	static final int TOP_ROW_WEIGHT = 1;
	static final int MID_ROW_WEIGHT = 5;
	static final int BOT_ROW_WEIGHT = 1;
	
	static final int GRID_WEIGHT = 4;
	static final int REPORT_WEIGHT = 1;
	
	static final int LABEL_W_X = 1;
	static final int LABEL_W_Y = 0;
	
	static JFrame f;
	
	static JPanel main_panel, report_panel;
	
    static JLabel trust_lab, report_lab, error_lab, constr_lab;
    
    //static ActionPanel act;
    static TrustGraphPanel graph;
    static BeliefPanel belief_panel;
    static ErrorPanel error_panel;
    static ConstraintPanel constraint_panel;
	
	public MainPanel() {
		f = new JFrame(Strings.project_title);
        f.setSize(1000,1000);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null); 
        
		main_panel = new JPanel();
		graph = new TrustGraphPanel(this);
        belief_panel = new BeliefPanel(this);
        report_panel = new ReportPanel(graph);
		error_panel = new ErrorPanel();
		constraint_panel = new ConstraintPanel();
        
		main_panel.setLayout(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		
		//main_panel.setBackground(Color.YELLOW);

		//
		//BeliefPanel
		//
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = TOP_ROW_WEIGHT;
        gbc.weighty = TOP_ROW_WEIGHT;
        main_panel.add(belief_panel, gbc);
      
        //
        //TrustGraphPanel
        //
		trust_lab = new JLabel(Strings.main_trust_title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = LABEL_W_X;
        gbc.weighty = LABEL_W_Y;
        gbc.insets = new Insets(10, 40, 0, 20);
        main_panel.add(trust_lab, gbc);
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 3;
        gbc.insets = new Insets(5, 40, 40, 40);
        gbc.weightx = GRID_WEIGHT;
        gbc.weighty = MID_ROW_WEIGHT;
        main_panel.add(graph, gbc);
        
        //
        //ReportPanel
        //
        //label
		report_lab = new JLabel(Strings.main_reports_title);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = LABEL_W_X;
        gbc.weighty = LABEL_W_Y;
        gbc.insets = new Insets(10, 0, 0, 20);
        main_panel.add(report_lab, gbc);
        
        //Report Panel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 0, 40);
        gbc.weightx = REPORT_WEIGHT;
        gbc.weighty = MID_ROW_WEIGHT;
        main_panel.add(report_panel, gbc);
        
        //Constraint Panel
		constr_lab = new JLabel(Strings.constr_title);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = LABEL_W_X;
        gbc.weighty = LABEL_W_Y;
        gbc.insets = new Insets(0, 0, 0, 20);
        main_panel.add(constr_lab, gbc);
        

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 40, 40);
        gbc.weightx = REPORT_WEIGHT;
        gbc.weighty = MID_ROW_WEIGHT;
        main_panel.add(constraint_panel, gbc);
        
        //
        //ErrorPane
		//
        //label
        error_lab = new JLabel(Strings.main_errors_title);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = LABEL_W_X;
        gbc.weighty = LABEL_W_Y;
        gbc.insets = new Insets(10, 40, 0, 20);
        main_panel.add(error_lab, gbc);
        
        //ErrorPane
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        //gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 40, 40, 40);
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        main_panel.add(error_panel, gbc);
        
	
		f.add(main_panel);

        f.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainPanel();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action Taken MainPanel");
		f.validate();
	}

}
