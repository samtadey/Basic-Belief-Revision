/**
 * 
 */
package revision_ui;

import java.awt.Component;
import java.awt.TextArea;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author sam_t
 *
 */
public class WeightedHammingPanel<T> extends JPanel {

	HashMap<T, Double> varlist;
	Set<T> varset;
	JPanel main, vars, equals, weights;
	DefaultListModel<T> vlist;
	DefaultListModel<JLabel> labels;
	DefaultListModel<JTextField> weighttextlist;
	JList<T> list;
	JList<JLabel> eq;
	JList<JTextField> w;
	
	public WeightedHammingPanel(Set<T> varset) {
		this.varset = varset;
		this.varlist = new HashMap<T, Double>();
		
		//i think "this" will be main
		//main = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		vars = new JPanel();
		equals = new JPanel();
		equals.setLayout(new BoxLayout(equals, BoxLayout.Y_AXIS));
		weights = new JPanel();
		weights.setLayout(new BoxLayout(weights, BoxLayout.Y_AXIS));
		
		//setup variable list
		vlist = new DefaultListModel<T>();
		weighttextlist = new DefaultListModel<JTextField>();
		labels = new DefaultListModel<JLabel>();

		
		//for some reason can't use list for other components
		for (T c: varset)
		{
			vlist.addElement(c);
			equals.add(new JLabel(" = "));
			weights.add(new JTextField(10));
		}
		list = new JList<T>(vlist);

		
		vars.add(list);

		
		//make equals pane
		
		this.add(vars);
		this.add(equals);
		this.add(weights);
		
	}
	
	
}
