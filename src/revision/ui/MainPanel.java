/**
 * 
 */
package revision.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author sam_t
 *
 */
public class MainPanel implements ActionListener {
	
	static JFrame f;
	
	static JPanel main_panel, belief_panel, top_panel;
	
    static JLabel lsent;
    static JLabel output;
    static JLabel dist;
  
    // text area
    static JLabel bel_lab, sent_lab, res_lab, vocab_lab;
    static JTextArea test;
    static JTextArea bel, sent, res;
    
    static ActionPanel act;
    static TrustGraphPanel graph;
	
	public MainPanel() {
		f = new JFrame("Belief Revision");
        f.setSize(1000,1000);
		//f.setVisible(true);
		
		main_panel = new JPanel();
		main_panel.setLayout(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		main_panel.setBackground(Color.YELLOW);

		
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.insets = new Insets(5, 40, 0, 40);
        gbc.weightx = 1;
        gbc.weighty = 1;
        main_panel.add(new BeliefPanel(this), gbc);
      
		
        graph = new TrustGraphPanel(this);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        //gbc.insets = new Insets(5, 40, 0, 40);
        gbc.weightx = 1;
        gbc.weighty = 5;
        main_panel.add(graph, gbc);
	
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
