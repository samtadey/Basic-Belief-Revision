/**
 * 
 */
package revision.ui;

import java.awt.Color;
import java.awt.ScrollPane;

import javax.swing.JTextArea;

/**
 * @author sam_t
 *
 */
public class ErrorPanel extends ScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTextArea error_messages;
	
	public ErrorPanel() {
		
		error_messages = new JTextArea(10,10);
		error_messages.setForeground(Color.RED);
		error_messages.setEditable(false);
		
		this.add(error_messages);
	}
	
}
