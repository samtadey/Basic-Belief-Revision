/**
 * 
 */
package revision.ui.handler;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextArea;

import revision.ui.ErrorPanel;

/**
 * @author sam_t
 *
 */
public class ErrorHandler {
	
	static Calendar calendar = Calendar.getInstance();
	
	static JTextArea errors = ErrorPanel.error_messages;

	
	//error msg for actions requiring prerequisite actions
	public static void addError(String action, String prereq, String message) {
		errors.append(timeOfDay() + ": Action: " + action + " Requires: " + prereq + " Message: " + message + "\n");
	}
	
	//basic error msg
	public static void addError(String action, String message) {
		errors.append(timeOfDay() + ": Action: " + action + " Message: " + message + "\n");
	}
	
	//multiple error msg to add
	public static void addErrorGroup(String action, ArrayList<String> messages) {
		for (String msg : messages)
			addError(action, msg);
	}
	
	//clear error action
	public void clearErrors() {
		errors.removeAll();
	}
	
	//helpers
	//get time of day
	private static String timeOfDay() {
		return new String(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
	}
}
