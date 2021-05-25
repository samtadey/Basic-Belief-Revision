/**
 * 
 */
package language;

/**
 * @author sam_t
 *
 * This class is a state representation of the known world. 
 * The class stores a binary string representation of the world state
 *
 */
public class State implements Comparable<State> {
	
	private String state_rep;
	

	public State(String state_rep) {
		this.state_rep = state_rep;
	}
	
	public String getState() {
		return this.state_rep;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state_rep == null) ? 0 : state_rep.hashCode());
		return result;
	}

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
	
	@Override
	public int compareTo(State o) {
		//add something for different lengths?
		//if (this.state_rep.length() != o.state_rep.length())
			//throw something
			//throw IllegalArgumentException;
		
		return this.state_rep.compareTo(o.state_rep);
	}

}


