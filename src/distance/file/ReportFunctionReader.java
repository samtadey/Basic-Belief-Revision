/**
 * 
 */
package distance.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import constants.Strings;
import language.State;

/**
 * The ReportFunctionReader parses file input into a ReportFunction object
 * 
 * @author sam_t
 */
public class ReportFunctionReader {
	
	Scanner file_reader;
	ArrayList<Character> vars;
	
	/**
	 * ReportFunctionReader Constructor
	 * 
	 * @param file File to parse
	 * @param vars ArrayList<Character> propositional vocabulary
	 * @throws FileNotFoundException
	 */
	public ReportFunctionReader(File file, ArrayList<Character> vars) throws FileNotFoundException {
		file_reader = new Scanner(file);
		this.vars = vars;
	}
	

	/**
	 * Returns true if the line is blank
	 * 
	 * @param line
	 * @return
	 */
	private boolean isEmpty(String line) {
		return line.length() == 0;
	}
	
	/**
	 * Returns true if the first character in the line is a comment token '#'
	 * 
	 * @param line
	 * @return boolean
	 */
	private boolean isComment(String line) {
		int i;
		//find first non space character
		for (i = 0; i < line.length(); i++)
			if (line.charAt(i) != ' ')
				break;
		
		//if first char is # then this line is a comment
		if (line.charAt(i) == '#')
			return true;
		return false;
		
	}
	
	/**
	 * Returns true if the length of the state is equal to the number of propositional variables in the vocabulary
	 * 
	 * @param state
	 * @return
	 */
	private boolean isValidState(State state) {
		return this.vars.size() == state.getState().length();
	}
	
	/**
	 * Checks State for validity
	 * 
	 * @param s1 State
	 * @throws Exception if invalid
	 */
	private void checkStates(State s1) throws Exception {
		if (!isValidState(s1))
			throw new Exception(Strings.function_error_state_length(s1));
	}
	
	/**
	 * Checks all States for validity
	 * 
	 * @param s1 State
	 * @param s2 State
	 * @throws Exception if any State is invalid
	 */
	private void checkStates(State s1, State s2) throws Exception {
		if (!isValidState(s1))
			throw new Exception(Strings.function_error_state_length(s1));
		if (!isValidState(s2))
			throw new Exception(Strings.function_error_state_length(s2));
	}
	
	/**
	 * Parses the defaults section of the input file type. Will set the positive and negative default formula values
	 * of the ReportFunction object.
	 * 
	 * @param report ReportFunction
	 * @return String line of next section header, or empty string
	 * @throws Exception if file invalid
	 */
	private String parseDefaults(ReportFunction report) throws Exception {
		String line = "";
		String[] items;
		
		while(file_reader.hasNextLine())
		{
			line = file_reader.nextLine();
			//comment
			if (isEmpty(line) || isComment(line))
				continue;
			
			//if we've found next section - Done
			if (line.equals(Strings.report_file_combo_header) || line.equals(Strings.report_file_allcombo_header))
				return line;
			
			items = line.split(",");
			if (items.length != 2)
				throw new Exception(Strings.report_file_error_default);
			
			report.setDefaultPos(items[0].trim());
			report.setDefaultNeg(items[1].trim());
			
		}
		
		return line;
	}
	
	/**
	 * Parses the Combo Section of the input file type. Will add entries to the combo HashMap 
	 * 
	 * @param report ReportFunction 
	 * @return String line of next section header or empty string
	 * @throws Exception
	 */
	private String parseCombo(ReportFunction report) throws Exception {
		String line;
		String[] items;
		State s1,s2;
		
		while (file_reader.hasNextLine())
		{
			line = file_reader.nextLine();
			if (isEmpty(line) || isComment(line))
				continue;
			
			if (line.equals(Strings.report_file_allcombo_header))
				return line;
			
			items = line.split(",");
			if (items.length != 4)
				throw new Exception(Strings.report_file_error_combo);
			
			s1 = new State(items[0].trim());
			s2 = new State(items[1].trim());
			
			//checks validity of states
			checkStates(s1,s2);
			
			report.addToComboPos(s1, s2, items[2].trim());
			report.addToComboNeg(s1, s2, items[3].trim());
		}
		
		return "";
	}
	
	/**
	 * Parses the AllCombo Section of the input file type. Will add entries to the combo HashMap 
	 * 
	 * @param report ReportFunction
	 * @return String header of next section or empty string
	 * @throws Exception
	 */
	private String parseAllCombo(ReportFunction report) throws Exception {
		String line;
		
		String[] items;
		State s;
		
		while (file_reader.hasNextLine())
		{
			line = file_reader.nextLine();
			if (isEmpty(line) || isComment(line))
				continue;
			
			if (line.equals(Strings.report_file_combo_header))
				return line;
			
			items = line.split(",");
			if (items.length != 3)
				throw new Exception(Strings.report_file_error_allcombo);
			
			s = new State(items[0].trim());
			
			checkStates(s);
			
			report.addToAllComboPos(s, items[1].trim());
			report.addToAllComboNeg(s, items[2].trim());
		}
		
		return "";
	}

	/**
	 * Parses the entire input file into a ReportFunction object
	 * 
	 * @return ReportFunction
	 * @throws Exception if File is invalid
	 */
	public ReportFunction parseFile() throws Exception {
		
		//default
		ReportFunction report = new ReportFunction("1", "1");
		String line;
		String prev_line = "";
		
		//parse default section
		while (file_reader.hasNextLine())
		{
			line = file_reader.nextLine();
			if (isEmpty(line) || isComment(line))
				continue;
			
			if (line.equals(Strings.report_file_default_header))
			{
				prev_line = parseDefaults(report);
				break;
			}	
		}
		
		//we have found a header
		while (file_reader.hasNextLine() && !prev_line.equals(""))
		{
			if (prev_line.equals(Strings.report_file_allcombo_header))
				prev_line = parseAllCombo(report);
			
			if (prev_line.equals(Strings.report_file_combo_header))
				prev_line = parseCombo(report);
			
		}
		
		return report;
	}

	/**
	 * Closes the Scanner object
	 */
	public void close() {
		this.file_reader.close();
	}
	
}
