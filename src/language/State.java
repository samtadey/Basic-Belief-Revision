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
public class State {
	
	private String state_rep;
	
	public State(String state_rep) {
		this.state_rep = state_rep;
	}
	
	public String getState() {
		return this.state_rep;
	}
}
