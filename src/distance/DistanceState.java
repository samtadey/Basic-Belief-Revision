/**
 * 
 */
package distance;

import java.util.ArrayList;
import java.util.Set;

import aima.core.logic.common.ParserException;
import constants.Strings;
import distance.revision.TriangleInequalityResponse;
import distance.revision.TrustRevisionOperation;
import language.BeliefState;
import language.State;

/**
 * @author sam_t
 *
 */
public class DistanceState {

	private DistanceMap map;
	
	public DistanceState(Set<Character> vocab) {
		this.map = new DistanceMap(vocab);
	}

	public DistanceMap getMap() {
		return map;
	}

	public void setMap(DistanceMap map) {
		this.map = map;
	}
	
	public void setMapMember(State s1, State s2, double current_val, double new_val, TriangleInequalityResponse tri_res, ArrayList<String> errors) {
		BeliefState invalid;
		
		if (current_val == new_val)
			return;
		
		//check triangle inequality validity given the naive value generated
		invalid = checkTriangleInequality(s1, s2, new_val);
		
		//if any invalid states have been found
		//the new_val must be changed to satisfy triangle inequality
		if (invalid.getBeliefs().size() > 0)
			new_val = calcDistanceValueTriIneq(s1,s2,invalid,current_val,new_val,tri_res,errors);
		
		//set the map with the new value
		map.setDistance(s1, s2, new_val);
	}
	
	/*
	 * The function iterates through all combinations of the BeliefStates and modifies their distance values by the mod_value parameter
	 * 
	 * @params
	 * 	BeliefState s1, s2
	 * 	double mod_value
	 * 
	 * @return 
	 * 	ArrayList<String> error messages for any value assignments that do not meet Distance/Reporting constraints
	 */
	private ArrayList<String> modByReport(BeliefState b1, BeliefState b2, String trust_op, double mod_value, 
			TriangleInequalityResponse tri_res) throws Exception {
		
		State s1, s2;
		double current_val, new_val;
		ArrayList<String> errors = new ArrayList<String>();
		
		for (int i = 0; i < b1.getBeliefs().size(); i++)
		{
			s1 = b1.getBeliefs().get(i);
			for (int j = 0; j < b2.getBeliefs().size(); j++)
			{
				s2 = b2.getBeliefs().get(j);
				//current value in the grid
				current_val = map.getDistance(s1, s2);
				//naive value assignment
				//assigns the value to the user designated value
				new_val = TrustRevisionOperation.reviseValue(current_val, mod_value, trust_op);
				//constraint controlled setter function for the DistanceState
				//sets the distance value based on current constraints and constraint handlers
				this.setMapMember(s1, s2, current_val, new_val, tri_res, errors);
			}
		}
		//return error messages
		return errors;
	}
	
	/**
	 * 
	 * @param s1
	 * @param s2
	 * @param invalid
	 * @param current_val
	 * @param new_val
	 * @param tri_res
	 * @param errors
	 * @return
	 */
	private double calcDistanceValueTriIneq(State s1, State s2, BeliefState invalid, double current_val, double new_val, 
			TriangleInequalityResponse tri_res, ArrayList<String> errors) {
		
		double handled_val;

		//value will be set based on tri_res object and available values
		handled_val = tri_res.handleTriangleInequality(s1, s2, invalid, this.map, current_val, new_val);
		
		//set error Triangle Inequality errors
		//provide information on how it was handled
		errors.add(Strings.errorTriHandleType(tri_res, s1, s2, invalid, current_val, new_val, handled_val));

		//set new_val to the handled_val
		//new_val = handled_val;
		return handled_val;
	}
	
	
	/*
	 * Checks a proposed distance value for triangle inequality among intermediate states
	 * 
	 * @returns
	 * 	State that violates triangle inequality between the States s and u. 
	 *  Returns null if triangle inequality is not violated
	 */
	private BeliefState checkTriangleInequality(State s, State u, double proposed_val) {
		BeliefState allinvalid = new BeliefState();
		double hypot, edge_val;
		double a,b,c; //actuals
		//State tri_ineq = null;
		
		for (State t : map.getPossibleStates().getBeliefs())
		{
			if (!s.equals(t) && !u.equals(t))
			{
				a = map.getDistance(s, t);
				b = map.getDistance(t, u);
				c = map.getDistance(s, u);

				hypot = a + b;
				
				//we are increasing a value
				if (proposed_val > c)
				{
					if (proposed_val > hypot)
						allinvalid.addBelief(t);
				}
				else //decreasing a value
				{		
					edge_val = proposed_val + a;
					if (edge_val < b)
						allinvalid.addBelief(t);
						//return s;
					edge_val = proposed_val + b;
					if (edge_val < a)
						allinvalid.addBelief(t);
					
				}
			}
		}
		return allinvalid;
	}
	

	/*
	 * Modifies the distance function by the Report.
	 * Splits states in to two categories, satisfied and unsatisfied. Satisfied states are true given the formula in the report object, unsatisfied states are false.
	 * Given the reported result on the true/false nature of the formula, BeliefStates are iterated through and distances are changed to reflect the 
	 * information of the report.
	 * 
	 * @params
	 * 	Report r
	 */
	public ArrayList<String> addReport(Report r, String operation, double modifier, TriangleInequalityResponse tri_res) throws ParserException {
		//convert report to states
		BeliefState sat_report = r.convertFormToStates(map.getVocab());
		BeliefState unsat_report = new BeliefState();
		ArrayList<String> errors = new ArrayList<String>();
		
		//BeliefState unsat_report = //add all but sat_report;
		//if does not contain state in sat, must be a member of the unsat group
		for (State state: map.getPossibleStates().getBeliefs())
		{
			if (!sat_report.contains(state))
				unsat_report.addBelief(state);
		}

		if (r.getReportedVal() == 0)
		{
			try {
				errors.addAll(modByReport(sat_report, unsat_report, operation, modifier, tri_res));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		else if (r.getReportedVal() == 1)
		{
			try {
				errors.addAll(modByReport(sat_report, unsat_report, operation, modifier, tri_res));
			} catch (Exception e) {
				System.out.println(e);
			}

		}
		return errors;

	}
	
	//precourser matrix?
	/**
	 * Construct a Minimax distance DistanceMap computing the shortest path between all pairs of states in the DistanceMap member object.
	 * 
	 * @return
	 */
	public DistanceMap miniMaxDistance(DistanceMap map) {
		DistanceMap mm_dist = new DistanceMap(map);
		State si,sj,sk;
		ArrayList<State> states;
		int i,j,k,n;
		double inter_dist;
		
		n = map.getPossibleStates().getBeliefs().size();
		states = map.getPossibleStates().getBeliefs();
		
		for (k = 0; k < n; k++)
		{
			sk = states.get(k);
			for (j = 0; j < n; j++)
			{
				sj = states.get(j);
				for (i = 0; i < n; i++)
				{
					si = states.get(i);
					inter_dist = map.getDistance(si, sk) + map.getDistance(sk, sj);
					if (inter_dist < map.getDistance(si, sj))
					{
						mm_dist.setDistance(si, sk, inter_dist);
					}
				}
			}
		}
		
		return mm_dist;
	}
	
	
}
