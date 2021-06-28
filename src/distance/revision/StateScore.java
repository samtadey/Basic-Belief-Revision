/**
 * 
 */
package distance.revision;

import java.util.ArrayList;
import java.util.List;

import language.State;

/**
 * @author sam_t
 *
 */
public class StateScore {
	
	static final double MAX_RESULT = Double.MAX_VALUE;
	
	private State state;
	private int rank;
	private List<Double> distances;
	private double result;
	
	/**
	 * 
	 */
	public StateScore(State s, int r) {
		this.state = s;
		this.rank = r;
		this.distances = new ArrayList<Double>();
		this.result = MAX_RESULT;
	}
	
	public State getState() {
		return this.state;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public List<Double> getDistances() {
		return distances;
	}
	
	
	//add
	public void addDistance(double dist) {
		this.distances.add(dist);
	}


	public double getResult() {
		return result;
	}
	
	public void setResult(double result) {
		this.result = result;
	}

}
