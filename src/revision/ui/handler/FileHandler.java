/**
 * 
 */
package revision.ui.handler;

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
	
	
	/**
	 * Reads the file used to instantiate the object and converts the file contents into a RankingState object.
	 * 
	 * @param vocab propositional vocabulary
	 * @return RankingState
	 * @throws Exception
	 */
	public RankingState readFileToRanking(ArrayList<Character> vocab) throws Exception {
		String line;
		String[] token;
		int rank, vocab_size = vocab.size();
		State state;
		RankingState rs = new RankingState(vocab);
		
		while (file_reader.hasNextLine())
		{
			line = file_reader.nextLine();
			
			token = line.split(",");
			if (token.length != 2)
				throw new Exception(Strings.exception_missing_file_param);
			
			if (token[0].length() != vocab_size)
				throw new Exception(Strings.exception_bad_state_length);
				
			state = new State(token[0]);
			rank = Integer.parseInt(token[1]);

			rs.setRank(state, rank); // throws?
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
