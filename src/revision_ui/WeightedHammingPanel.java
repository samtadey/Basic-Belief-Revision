/**
 * 
 */
package revision_ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

import data.UiData;

/**
 * @author sam_t
 *
 */
public class WeightedHammingPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<Character> vars;
	ArrayList<Double> weights;
	Set<Character> varset;
	//JPanel main, vars, equals, weights;
	
	
	ArrayList<Character> items;
	DefaultListModel<JTextField> test;

	
	public WeightedHammingPanel(Set<Character> varset) {
		this.varset = varset;
		//this.varlist = new HashMap<Character, Double>();
		items = new ArrayList<Character>();		
		//i think "this" will be main
		//main = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		test = new DefaultListModel<JTextField>();

		
		

		for (Character c: varset)
		{
			JPanel panel = new JPanel();
			JLabel var = new JLabel(Character.toString(c));
		
			JLabel eq = new JLabel("=");
			JTextField tf = new JTextField(10);
			//
			tf.setText("1.0");
			//WeightItemContainer con = new WeightItemContainer(c);
			
			items.add(c);
			test.addElement(tf);
		
			//JButton b = new JButton("Test");
			panel.add(var);
			panel.add(eq);
			panel.add(tf);
			//WeightItemContainer con = new WeightItemContainer(c);
			//add to list for reference
			//items.add(con);
			this.add(panel);
			
		}
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) throws NumberFormatException {
		
		//set the data object 
		for (int i = 0; i < test.getSize() && i < items.size(); i++)
		{
			System.out.println(i);
			System.out.println(items.get(i) + " " + test.get(i).getText());
			
			//handle this with errors
			double w = Double.parseDouble(test.get(i).getText());
			
			UiData.addVarWeights(items.get(i), w);
		}
		
		System.out.println("Weighted handler");
//		for (WeightItemContainer c: items)
//			c.setWeight();
		
	}
	
	
}
