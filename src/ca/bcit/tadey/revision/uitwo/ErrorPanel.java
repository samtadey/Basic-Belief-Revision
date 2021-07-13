/**
 * 
 */
package ca.bcit.tadey.revision.uitwo;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.bcit.tadey.revision.constants.Strings;


/**
 * @author sam_t
 *
 */
public class ErrorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static JScrollPane scroll;
	
	public static JTextArea error_messages;
	
	///public static JButton clear;
	
	public ErrorPanel() {
		
		//allows component to fill to outer layout size
		this.setLayout(new BorderLayout());
		
		
		//this.setBorder(UISettings.panelborder);
		//this.setBackground(Color.RED);
		
		error_messages = new JTextArea(5,5);
		error_messages.setToolTipText(Strings.tooltip_error_desc);
		error_messages.setForeground(Color.RED);
		error_messages.setEditable(false);
		
		//clear = new JButton("Clear Errors");
		
		scroll = new JScrollPane(error_messages);
		//scroll.setBackground(Color.pink);
		
		this.add(scroll);
		//this.add(clear);
	}
	
}
