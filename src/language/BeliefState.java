/**
 * 
 */
package language;

import java.util.ArrayList;

/**
 * @author sam_t
 *
 */
public class BeliefState {

	private ArrayList<State> beliefs; 
	//private int num_vars;
	
	public BeliefState() {
		this.beliefs = new ArrayList<State>();
	}
	
	public BeliefState(ArrayList<State> beliefs) {
		this.beliefs = beliefs;
	}

	public ArrayList<State> getBeliefs() {
		return beliefs;
	}

	public void setBeliefs(ArrayList<State> beliefs) {
		this.beliefs = beliefs;
	}
	
	public void addBelief(State belief) {
		this.beliefs.add(belief);
	}
	
	public void removeBelief(int idx) throws IndexOutOfBoundsException {
		this.beliefs.remove(idx);
	}
	
	public void toConsole() {
		
		if (this.beliefs.size() < 1) {
			System.out.println("No Beliefs");
			return;
		}
		
		for (int i = 0; i < this.beliefs.size(); i++)
			System.out.println("[ " + this.beliefs.get(i).getState() + " ]");
		
	}

//	public int getNumVars() {
//		return num_vars;
//	}
//
//	public void setNumVars(int num_vars) {
//		this.num_vars = num_vars;
//	}

}
