/**
 * 
 */
package revision.uitwo.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import constants.Strings;
import distance.RankingState;
import language.State;

/**
 * The FileHandler class processes files to be converted into a RankingState object. FileHandler classes require specific input, which are two fields separated by
 * a comma. Field 1 is the state, Field 2 is the rank.
 * 
 * Eg.
 * 000,1
 * 100,2
 * 111,0
 * 
 * @author sam_t
 */
public class FileHandler {

	Scanner file_reader;
	
	
	/**
	 * FileHandler Constructor. 
	 * 
	 * @param file to read
	 * @throws FileNotFoundException
	 */
	public FileHandler(File file) throws FileNotFoundException {
		file_reader = new Scanner(file);
	}
	
	
//	/**
//	 * Reads the file used to instantiate the object and converts the file contents into a RankingState object.
//	 * 
//	 * @param vocab propositional vocabulary
//	 * @return RankingState
//	 * @throws Exception
//	 */
//	public RankingState readFileToRanking(ArrayList<Character> vocab) throws Exception {
//		String line;
//		String[] token;
//		int rank, vocab_size = vocab.size();
//		State state;
//		RankingState rs = new RankingState(vocab);
//		
//		while (file_reader.hasNextLine())
//		{
//			line = file_reader.nextLine();
//			
//			token = line.split(",");
//			if (token.length != 2)
//				throw new Exception(Strings.exception_missing_file_param);
//			
//			if (token[0].length() != vocab_size)
//				throw new Exception(Strings.exception_bad_state_length);
//				
//			state = new State(token[0]);
//			rank = Integer.parseInt(token[1]);
//
//			rs.setRank(state, rank); // throws?
//		}
//
//		return rs;
//	}
	
	/**
	 * Reads the file used to instantiate the object and converts the file contents into a RankingState object.
	 * 
	 * Format:
	 *  3 //-> default rank
	 *  0:000,001 //-> rank 0 states
     *  1:100,101
	 *  2:010
	 * 	
	 *  any number of rank can be used
	 * 
	 * @param vocab propositional vocabulary
	 * @return RankingState
	 * @throws Exception
	 */
	public RankingState readFileToRanking(ArrayList<Character> vocab) throws Exception {
		String line, default_rank;
		String[] token, states;
		int rank;
		RankingState rs;
		
		//read first line
		//default value to set states to
		try
		{
			default_rank = file_reader.nextLine();
			rs = new RankingState(vocab, Integer.parseInt(default_rank));
		} catch (Exception ex) {
			throw new Exception(Strings.error_no_default_rank);
		}
		
		//read ranking values
		while (file_reader.hasNextLine())
		{
			//format rank:state,state
			line = file_reader.nextLine();
			token = line.split(":");
			
			//if not correct input separated by ':'
			if (token.length != 2)
				throw new Exception(Strings.exception_missing_file_param);
			
			//set rank to first value
			rank = Integer.parseInt(token[0]);
			
			//iterate through states
			states = token[1].split(",");
			for (String st: states)
			{			
				//if input state is not possible catch error and throw new error message
				try {
					rs.setRank(new State(st), rank);
				} catch(Exception ex) {
					throw new Exception(Strings.errorStateNotPossible(st));
				}
			}
		}

		return rs;
	}
	
	/**
	 * Closes the scanner object
	 * Should be used after reading the file
	 */
	public void closeFile() {
		file_reader.close();
	}
	
}
