/**
 * 
 */
package distance.revision;

import java.util.ArrayList;
import java.util.List;

import language.State;

/**
 * A StateScore object records all information used to find the result of revision. All information is recorded and then a result is set The result field 
 * is then used to determine which states are selected for revision
 * 
 * @author sam_t
 */
public class StateScore {
	
	/**
	 * Default result value set to max
	 */
	static final double MAX_RESULT = Double.MAX_VALUE;
	
	private State state;
	private int rank;
	private List<Double> distances;
	private double result;
	
	/**
	 * StateScore objects must be instantiated with
	 *  - State object
	 *  - Rank value (int)
	 */
	public StateScore(State s, int r) {
		this.state = s;
		this.rank = r;
		this.distances = new ArrayList<Double>();
		this.result = MAX_RESULT;
	}
	
	/**
	 * Getter for State
	 * 
	 * @return State
	 */
	public State getState() {
		return this.state;
	}
	
	/**
	 * Getter for rank
	 * 
	 * @return rank as an int
	 */
	public int getRank() {
		return this.rank;
	}
	
	/**
	 * Getter for the distances list
	 * 
	 * @return distances as a List
	 */
	public List<Double> getDistances() {
		return distances;
	}
	
	
	/**
	 * Add distance value to the list
	 * 
	 * @param dist as a double
	 */
	public void addDistance(double dist) {
		this.distances.add(dist);
	}

	/**
	 * Getter for the result member variable
	 * 
	 * @return result as a double
	 */
	public double getResult() {
		return result;
	}
	
	/**
	 * Setter for the result member variable
	 * 
	 * @param result
	 */
	public void setResult(double result) {
		this.result = result;
	}

}
