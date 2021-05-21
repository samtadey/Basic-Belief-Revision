/**
 * 
 */
package revision_ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import constants.Strings;
import main.BeliefRevisionUI;

/**
 * @author sam_t
 *
 */
public class VarListPanel extends JPanel implements ItemListener, ActionListener {
	
	private DefaultListModel<Character> list;
	private JList<Character> l;
	private JComboBox<String> chooser;
	JPanel cardholder;
	
	private JLabel label;
	
	private HashMap<String, JPanel> cardlist;
	
	
	public VarListPanel(Set<Character> varset) {
		//super(new GridBagLayout());
		this.list = new DefaultListModel<Character>();
		for (Character t: varset)
			this.list.addElement(t);
		
		//layout
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
    	String[] distance = {Strings.hamming, Strings.w_hamming, Strings.para, Strings.rand};
    	chooser = new JComboBox<String>(distance);
    	cardlist = new HashMap<String, JPanel>();
    	
    	//this.setLayout(new CardLayout());
    	cardholder = new JPanel(new CardLayout());
    	
    	JPanel card1 = new JPanel();
    	card1.setBackground(Color.red);
    	JPanel card2 = new WeightedHammingPanel(varset);
    	//card2.setBackground(Color.blue);
    	JPanel card3 = new ParametrizedDiffPanel(varset);
    	//card3.setBackground(Color.yellow);
    	JPanel card4 = new JPanel();
    	card4.setBackground(Color.green);
    	
    	//add cards to the map
    	//used by the main panel to access the cards
    	cardlist.put("Weighted Hamming", card2);
    	
    	//add to holder
    	cardholder.add(card1, Strings.hamming);
    	cardholder.add(card2, Strings.w_hamming);
    	cardholder.add(card3, Strings.para);
    	cardholder.add(card4, Strings.rand);
    	
    	chooser.addItemListener(this);
    	
    	
    	this.setBackground(Color.gray);
    	this.add(chooser);
    	this.add(cardholder);

    	//this.add(chooser);
	}
	
	
	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout)(this.cardholder.getLayout());
	    cl.show(this.cardholder, (String)evt.getItem());
	}
	
	
	public HashMap<String, JPanel> getCards() {
		return this.cardlist;
	}
	
	public JComboBox<String> getChooser() {
		return this.chooser;
	}
	
	public WeightedHammingPanel getWeighted() {
		return (WeightedHammingPanel) this.cardlist.get("Weighted Hamming");
	}

	public JPanel getCardByString(String cardId) {
		return this.cardlist.get(cardId);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Varlist");
	}
	

}
