/**
 * 
 */
package ca.bcit.tadey.revision.ui.handler;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextArea;

import ca.bcit.tadey.revision.ui.ErrorPanel;

/**
 * The ErrorHandler manages the ErrorPane. The handler can add messages to the pane, or clear the pane of contents
 * 
 * @author sam_t
 */
public class ErrorHandler {
	
	static Calendar calendar;
	
	static JTextArea errors = ErrorPanel.error_messages;

	
	/**
	 * Adds an error message to the ErrorPane
	 * 
	 * @param action String action being taken when the error occurred
	 * @param prereq String action that must be taken before the action currently being taken
	 * @param message String message to display as the specific error
	 */
	public static void addError(String action, String prereq, String message) {
		errors.append(timeOfDay() + ": Action: " + action + " Requires: " + prereq + " Message: " + message + "\n");
	}
	
	/**
	 * Adds an error message to the ErrorPane
	 * 
	 * @param action String action being taken when the error occurred
	 * @param message String message to display as the specific error
	 */
	public static void addError(String action, String message) {
		errors.append(timeOfDay() + ": Action: " + action + " Message: " + message + "\n");
	}
	
	/**
	 * Adds a group of messages to the ErrorPane
	 * 
	 * @param action String action being taken when the error occurred
	 * @param message String message to display as the specific error
	 */
	public static void addErrorGroup(String action, ArrayList<String> messages) {
		for (String msg : messages)
			addError(action, msg);
	}
	
	/**
	 * Clears the ErrorPane of all displayed errors
	 */
	public void clearErrors() {
		errors.removeAll();
	}
	
	/*
	 * Helpers
	 */
	
	/**
	 * Returns a the time of day as a String
	 * 
	 * @return Time of Day as a String
	 */
	private static String timeOfDay() {
		calendar = Calendar.getInstance();
		
		if (String.valueOf(calendar.get(Calendar.MINUTE)).length() < 2)
			return new String(calendar.get(Calendar.HOUR_OF_DAY) + ":0" + calendar.get(Calendar.MINUTE));
		return new String(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
	}
}
