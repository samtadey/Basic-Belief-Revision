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
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;

import main.BeliefRevisionUI;

/**
 * @author sam_t
 *
 */
public class VarListPanel<T> extends JPanel implements ItemListener {
	
	private DefaultListModel<T> list;
	private JList<T> l;
	private JButton add,remove,up,down;
	private JComboBox chooser;
	JPanel cardholder;
	
	public VarListPanel(Set<T> varset) {
		//super(new GridBagLayout());
		this.list = new DefaultListModel<T>();
		for (T t: varset)
			this.list.addElement(t);
		
		//layout
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
    	String[] distance = {"Hamming", "Weighted Hamming", "Parametrized Difference", "Random Distance"};
    	chooser = new JComboBox(distance);
    	
    	//this.setLayout(new CardLayout());
    	cardholder = new JPanel(new CardLayout());
    	
    	JPanel card1 = new JPanel();
    	card1.setBackground(Color.red);
    	JPanel card2 = new WeightedHammingPanel<T>(varset);
    	//card2.setBackground(Color.blue);
    	JPanel card3 = new ParametrizedDiffPanel<T>(varset);
    	//card3.setBackground(Color.yellow);
    	JPanel card4 = new JPanel();
    	card4.setBackground(Color.green);
    	
    	cardholder.add(card1, "Hamming");
    	cardholder.add(card2, "Weighted Hamming");
    	cardholder.add(card3, "Parametrized Difference");
    	cardholder.add(card4, "Random Distance");
    	
    	chooser.addItemListener(this);
    	
    	this.add(chooser);
    	this.add(cardholder);
//		
//		l = new JList<T>(this.list);
//		add = new JButton("Add");
//		remove = new  JButton("Remove");
//		up = new JButton("Move Up");
//		down = new JButton("Move Down");
//		
//  //      BeliefRevisionUI te = new BeliefRevisionUI();
//        
//        // addActionListener to button
//        add.addActionListener(this);
//        up.addActionListener(this);
//        down.addActionListener(this);
//		
//		this.add(l, BorderLayout.PAGE_START);
//		
//		this.add(add, BorderLayout.CENTER);
//		this.add(remove, BorderLayout.CENTER);
//		this.add(up);
//		this.add(down);
    	this.add(chooser);
	}
	
	
	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout)(this.cardholder.getLayout());
	    cl.show(this.cardholder, (String)evt.getItem());
	}
	
	
	
	
	
	

	

}
