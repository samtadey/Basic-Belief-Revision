/**
 * 
 */
package distance.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import constants.Strings;
import language.State;

/**
 * @author sam_t
 *
 */
public class ReportFunctionReader {
	
	Scanner file_reader;
	
	
	public ReportFunctionReader(File file) throws FileNotFoundException {
		file_reader = new Scanner(file);
	}
	
	public void close() {
		this.file_reader.close();
	}
	
	public boolean isEmpty(String line) {
		return line.length() == 0;
	}
	
	public boolean isComment(String line) {
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
	
	//
	//Do we add a vocabulary to the constructor?
	//compare with number of vocab members?
	//
	public boolean isValidState(String state) {
		return false;
	}
	
	
//	public ReportFunction parseFile() throws Exception {
//		
//		//default
//		ReportFunction report = new ReportFunction("1", "1");
//		String line;
//		String prev_line = "";
//		
//		//parse default section
//		while (file_reader.hasNextLine())
//		{
//			line = file_reader.nextLine();
//			if (isEmpty(line) || isComment(line))
//				continue;
//			
//			if (line.equals(Strings.report_file_default_header))
//			{
//				prev_line = parseDefaults(report);
//				System.out.println(prev_line);
//				break;
//			}
//		}
//		
//		
//		if (prev_line.equals(Strings.report_file_allcombo_header))
//			prev_line = parseAllCombo(report);
//		
//		if (prev_line.equals(Strings.report_file_combo_header))
//			prev_line = parseCombo(report);
//		
//
//		return report;
//	}
	
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
	
	public String parseDefaults(ReportFunction report) throws Exception {
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
	
	//format 
	// State1, State2, Positive Val, Negative Val
	public String parseCombo(ReportFunction report) throws Exception {
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
			
			report.addToComboPos(s1, s2, items[2].trim());
			report.addToComboNeg(s1, s2, items[3].trim());
		}
		
		return "";
	}
	
	public String parseAllCombo(ReportFunction report) throws Exception {
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
			
			report.addToAllComboPos(s, items[1].trim());
			report.addToAllComboNeg(s, items[2].trim());
		}
		
		return "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("D:\\Documents\\CST_BTech\\Summer Research\\report_function.txt");
		ReportFunction rep;
		
		try {
			ReportFunctionReader read = new ReportFunctionReader(file);
			rep = read.parseFile();
			System.out.println("Pos: " + rep.getDefaultPos() + " Neg: " + rep.getDefaultNeg());

			System.out.println(rep.findPosFormula(new State("00"), new State("01")));
			System.out.println(rep.findPosFormula(new State("00"), new State("11")));
			
			System.out.println(rep.findNegFormula(new State("00"), new State("01")));
			System.out.println(rep.findNegFormula(new State("00"), new State("11")));
			System.out.println("Close: ");
			read.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}


	}

}
