/**
 * 
 */
package ca.bcit.tadey.revision.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


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
		error_messages.setForeground(Color.RED);
		error_messages.setEditable(false);
		
		//clear = new JButton("Clear Errors");
		
		scroll = new JScrollPane(error_messages);
		//scroll.setBackground(Color.pink);
		
		this.add(scroll);
		//this.add(clear);
	}
	
}
