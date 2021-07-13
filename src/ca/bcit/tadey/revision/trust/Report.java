/**
 * 
 */
package ca.bcit.tadey.revision.trust;

import java.util.Set;

import aima.core.logic.common.ParserException;
import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.translation.InputTranslation;

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
	
	
	public BeliefState convertFormToStates(Set<Character> vocab) throws ParserException {
		BeliefState states;
		states = InputTranslation.convertPropInput(this.formula, vocab);
		return states;
	}

}
