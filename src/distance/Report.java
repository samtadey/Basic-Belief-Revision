/**
 * 
 */
package distance;

import java.util.Set;

import language.BeliefState;
import main.BeliefRevision;

/**
 * @author sam_t
 *
 */
public class Report {

	String formula;
	int reported_value;
	
	public Report(String formula, int reported_value) {
		this.formula = formula;
		this.reported_value = reported_value;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public int getReportedVal() {
		return reported_value;
	}

	public void setReportedVal(int reported_value) {
		this.reported_value = reported_value;
	}
	
	
	public BeliefState convertFormToStates(Set<Character> vocab) {
		BeliefState states;
		states = BeliefRevision.convertPropInput(this.formula, vocab);
		return states;
	}

}
