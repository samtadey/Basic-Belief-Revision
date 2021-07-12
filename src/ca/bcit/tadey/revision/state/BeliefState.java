/**
 * 
 */
package ca.bcit.tadey.revision.state;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author sam_t
 * 
 * The BeliefState class represents a set of states. These states should be in the form of a binary string
 *
 * Variables
 *   beliefs - an ArrayList of States
 *
 */
public class BeliefState {

	private ArrayList<State> beliefs; 
	
	/*
	 * Default constructor
	 * Instantiates the object
	 */
	public BeliefState() {
		this.beliefs = new ArrayList<State>();
	}
	
	
	/*
	 * @params
	 * 	 ArrayList<State>
	 * 
	 * Instantiates the object
	 */
	public BeliefState(ArrayList<State> beliefs) {
		this.beliefs = new ArrayList<State>(beliefs);
	}
	
	public BeliefState(BeliefState beliefs) {
		this.beliefs = new ArrayList<State>(beliefs.getBeliefs());
	}
	
	
	public BeliefState(Set<State> beliefs) {
		this.beliefs = new ArrayList<State>();
		for (State s : beliefs)
			this.beliefs.add(s);
	}

	/*
	 * @returns beliefs as an ArrayList<State>
	 */
	public ArrayList<State> getBeliefs() {
		return beliefs;
	}

	/*
	 * @params
	 *   ArrayList<State>
	 * 
	 * Sets the list of beliefs to the method argument
	 */
	public void setBeliefs(ArrayList<State> beliefs) {
		this.beliefs = beliefs;
	}
	
	/*
	 * @params
	 *   State
	 * 
	 * Adds the State method argument to the ArrayList of States
	 */
	public void addBelief(State belief) {
		this.beliefs.add(belief);
	}
	
	/*
	 * @params
	 * 	 int idx
	 * 
	 * Removes the State located at the index of the ArrayList<State>
	 * 
	 * @throws
	 *   IndexOutOfBoundsException
	 */
	public void removeBelief(int idx) throws IndexOutOfBoundsException {
		this.beliefs.remove(idx);
	}
	
	/*
	 * Want this to resemble a set
	 */
	public boolean contains(State s) {
		for (State os : this.beliefs)
			if (os.equals(s))
				return true;
		return false;
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < this.beliefs.size(); i++)
			build.append("[ " + this.beliefs.get(i).getState() + " ],");
		return build.toString();
	}
	
	/*
	 * Prints the contents of the BeliefState to the console.
	 * Sample Output:
	 * [ 1001 ]
	 * [ 1000 ]
	 * [ 0000 ]
	 */
	public void toConsole() {
		
		if (this.beliefs.size() < 1) {
			System.out.println("No Beliefs");
			return;
		}
		
		for (int i = 0; i < this.beliefs.size(); i++)
			System.out.println("[ " + this.beliefs.get(i).getState() + " ]");
		
	}

}
