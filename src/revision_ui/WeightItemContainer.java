/**
 * 
 */
package revision_ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author sam_t
 *
 */
public class WeightItemContainer extends JPanel implements ActionListener {
	
	private char varlabel;
	private JTextField tf;
	private String val;
	
	public WeightItemContainer(Character varlabel) {
		this.varlabel = varlabel;
		JLabel var = new JLabel(Character.toString(varlabel));
		
		JLabel eq = new JLabel("=");
		tf = new JTextField(10);
	
		JButton b = new JButton("Test");
		
		tf.addActionListener(this);
		
		this.add(var);
		this.add(eq);
		this.add(tf);
		this.add(b);
	}
	
	public char getLabel() {
		return this.varlabel;
	}
	
	public double getWeight() throws NumberFormatException {
		//String input = tf.getText();
		String input = val;
		System.out.println(input);
		return Double.parseDouble(input);
	}
	
	public void setWeight() {
		this.val = new String(tf.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//?
		System.out.println(tf.getText());
	}
}
