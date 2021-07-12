/**
 * 
 */
package ca.bcit.tadey.revision.state;

/**
 * @author sam_t
 *
 * This class is a state representation of the known world. 
 * The class stores a binary string representation of the world state
 *
 */
public class State implements Comparable<State> {
	
	private String state_rep;

	/*
	 * Constructor for a State object
	 * The string parameter is stored as the State
	 */
	public State(String state_rep) {
		this.state_rep = state_rep;
	}
	
	/*
	 * Getter function for the String member state_rep
	 * 
	 * @return
	 * 	The object State as a String
	 */
	public String getState() {
		return this.state_rep;
	}
	
	/*
	 * Hashcode for a State object.
	 * 
	 * @returns
	 * 	Hashcode as an int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state_rep == null) ? 0 : state_rep.hashCode());
		return result;
	}

	/*
	 * equals function for State objects
	 * 
	 * @params
	 * 	Object obj as the object to compare for equality
	 * @returns
	 * 	boolean representing the equality of the two objects
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (state_rep == null) {
			if (other.state_rep != null)
				return false;
		} else if (!state_rep.equals(other.state_rep))
			return false;
		return true;
	}
	
	/*
	 * Comparator function for the State object
	 * 
	 * @params
	 * 	State o as the state to compare to
	 * @returns
	 * 	int value representing the results of the compare
	 * 	-1 indicates 'this' is lesser than the object 'o'
	 * 	0 indicates the objects are equal
	 *  1 indicates 'o' is lesser than the object 'this'
	 */
	@Override
	public int compareTo(State o) {
		//add something for different lengths?
		//if (this.state_rep.length() != o.state_rep.length())
			//throw something
			//throw IllegalArgumentException;
		
		return this.state_rep.compareTo(o.state_rep);
	}
	
	public String toString() {
		return this.getState();
	}

}


