/**
 * 
 */
package revision.uitwo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Strings;

/**
 * @author sam_t
 *
 */
public class MainPanel {
	
	static final int TOP_ROW_WEIGHT = 1;
	static final int MID_ROW_WEIGHT = 5;
	static final int BOT_ROW_WEIGHT = 1;
	
	static final int GRID_WEIGHT = 5;
	static final int REPORT_WEIGHT = 1;
	
	static final int LABEL_W_X = 1;
	static final int LABEL_W_Y = 0;
	
	static JFrame f;
	
	static JPanel main_panel, report_panel;
	
    static JLabel trust_lab, report_lab, error_lab, minimax_lab;
    
    static TrustGraphPanel graph;
    static BeliefPanel belief_panel;
    static MiniMaxDistancePanel minimax;
    static ErrorPanel error_panel;
	
	public MainPanel() {
		
		//frame settings
		f = new JFrame(Strings.project_title);
        f.setSize(1250,1000);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null); 
           
        
        //constructors for individual panels
		main_panel = new JPanel();
		graph = new TrustGraphPanel();
        belief_panel = new BeliefPanel(this);
        report_panel = new ReportPanel(graph);
        minimax = new MiniMaxDistancePanel();
		error_panel = new ErrorPanel();   
		main_panel.setLayout(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		

		//
		//BeliefPanel
		//
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
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
		report_lab = new JLabel(Strings.main_reports_title);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = LABEL_W_X;
        gbc.weighty = LABEL_W_Y;
        gbc.insets = new Insets(10, 0, 0, 20);
        main_panel.add(report_lab, gbc);
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 0, 40);
        gbc.weightx = REPORT_WEIGHT;
        gbc.weighty = MID_ROW_WEIGHT;
        gbc.gridwidth = 2;
        main_panel.add(report_panel, gbc);
        
        
        //
        //MiniMaxDistancePanel
        //
		minimax_lab = new JLabel(Strings.mmd_title);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = LABEL_W_X;
        gbc.weighty = LABEL_W_Y;
        gbc.insets = new Insets(0, 0, 0, 20);
        main_panel.add(minimax_lab, gbc);
        

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 40, 40);
        gbc.weightx = REPORT_WEIGHT;
        gbc.weighty = MID_ROW_WEIGHT-1;
        main_panel.add(minimax, gbc);
        
        
        //
        //ErrorPane
		//
        error_lab = new JLabel(Strings.main_errors_title);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = LABEL_W_X;
        gbc.weighty = LABEL_W_Y;
        gbc.insets = new Insets(10, 40, 0, 20);
        main_panel.add(error_lab, gbc);
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
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

}
