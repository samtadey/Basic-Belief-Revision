/**
 * 
 */
package ca.bcit.tadey.revision.trust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import ca.bcit.tadey.revision.constants.Strings;
import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.translation.InputTranslation;
import ca.bcit.tadey.revision.trust.constraint.TriangleInequalityOperator;
import ca.bcit.tadey.revision.trust.constraint.TriangleInequalityResponse;
import ca.bcit.tadey.revision.trust.file.ReportFunction;

/**
 * @author sam_t
 */
public class DistanceState {
	
	private static final double MIN_MAP_VAL = 0.1;

	private DistanceMap map;
	
	public DistanceState(Set<Character> vocab) {
		this.map = new DistanceMap(vocab);
	}
	
	public DistanceState(Set<Character> vocab, double init_val) {
		this.map = new DistanceMap(vocab, init_val);
	}
	
	public DistanceState(DistanceMap map) {
		this.map = new DistanceMap(map);
	}
	
	public DistanceState(DistanceState map) {
		this.map = new DistanceMap(map.getMap());
	}

	public DistanceMap getMap() {
		return map;
	}

	public void setMap(DistanceMap map) {
		this.map = map;
	}
	
	/**
	 * 
	 * @param s1
	 * @param s2
	 * @param current_val
	 * @param new_val
	 * @param tri_res
	 * @param errors
	 */
	public void setMapMember(State s1, State s2, double current_val, double new_val, TriangleInequalityResponse tri_res, ArrayList<String> errors) {
		
		BeliefState invalid;
		
		if (current_val == new_val)
			return;
		
		if (new_val < MIN_MAP_VAL)
			new_val = MIN_MAP_VAL;
		
		//check triangle inequality validity given the naive value generated
		if (tri_res.getOp() != TriangleInequalityOperator.IGNORE)
		{
			invalid = checkTriangleInequality(s1, s2, new_val);
			
			//if any invalid states have been found
			//the new_val must be changed to satisfy triangle inequality
			if (invalid.getBeliefs().size() > 0)
				new_val = calcDistanceValueTriIneq(s1,s2,invalid,current_val,new_val,tri_res,errors);
		}
		
		//set the map with the new value
		map.setDistance(s1, s2, new_val);
	}
	
	

	
	
	
	private double applyWeights(State s1, State s2, double mod_val, HashMap<Character, Double> weights, Set<Character> vocab) throws Exception {
		
		double weight_sum = 0.0, num_diff = 0.0;
		ArrayList<Character> vars = InputTranslation.setToArr(vocab);
		
		if (s1.getState().length() != s2.getState().length() && s1.getState().length() != vars.size())
			throw new Exception("Apply Weights: state sizes or vocab sizes not equal");
		
		//iterate through states and apply weights
		//the vocabulary set keeps the variables in the same order as
		//the characters in the state object
		for (int i = 0; i < s1.getState().length(); i++)
		{
			//state var different between the two states
			if (s1.getState().charAt(i) != s2.getState().charAt(i))
			{
				weight_sum += weights.get(vars.get(i));
				num_diff++;
			}
		}
		
		if (weight_sum == 0.0 || num_diff == 0.0)
			return mod_val;
		
		//return the mod value, modified by the weights 
		return mod_val * (weight_sum / num_diff);
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
	private DistanceState modByReport(BeliefState b1, BeliefState b2, String trust_op, double mod_value, 
			TriangleInequalityResponse tri_res, HashMap<Character, Double> var_weights, ArrayList<String> errors) throws Exception {
		
		DistanceState update = new DistanceState(this);
		State s1, s2;
		double current_val, new_val, mod_val;
		//ArrayList<String> errors = new ArrayList<String>();
		
		for (int i = 0; i < b1.getBeliefs().size(); i++)
		{
			s1 = b1.getBeliefs().get(i);
			for (int j = 0; j < b2.getBeliefs().size(); j++)
			{
				s2 = b2.getBeliefs().get(j);
				//current value in the grid
				current_val = update.map.getDistance(s1, s2);
				//apply weights to mod_value
				mod_val = applyWeights(s1,s2, mod_value, var_weights, update.map.getVocab());
				System.out.println(s1.getState() + "/" + s2.getState() + " Mod: " + mod_val);
				//naive value assignment
				//assigns the value to the user designated value
				new_val = TrustRevisionOperation.reviseValue(current_val, mod_val, trust_op);
				//constraint controlled setter function for the DistanceState
				//sets the distance value based on current constraints and constraint handlers
				update.setMapMember(s1, s2, current_val, new_val, tri_res, errors);
			}
		}
		
		return update;
		//return error messages
		//return errors;
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

				//indirect distance
				hypot = a + b;
				
				//we are increasing a value
				if (proposed_val > c)
				{
					if (proposed_val > hypot)
						allinvalid.addBelief(t);
				}
				else //decreasing a value
				{		
					//proposed val is the updated version of c
					//the proposed value for (s,u)
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
	
	/**
	 * Construct a Minimax distance DistanceMap computing the shortest path between all pairs of states in the DistanceMap member object.
	 * 
	 * @return
	 */
	public DistanceMap miniMaxDistance() {
		DistanceMap mm_dist = new DistanceMap(this.map);
		State si,sj,sk;
		ArrayList<State> states;
		int i,j,k,n;
		double inter_dist;
		
		n = mm_dist.getPossibleStates().getBeliefs().size();
		states = mm_dist.getPossibleStates().getBeliefs();
		
		for (k = 0; k < n; k++)
		{
			sk = states.get(k);
			for (j = 0; j < n; j++)
			{
				sj = states.get(j);
				for (i = 0; i < n; i++)
				{
					si = states.get(i);
					inter_dist = mm_dist.getDistance(si, sk) + mm_dist.getDistance(sk, sj);
					if (inter_dist < mm_dist.getDistance(si, sj))
					{
						mm_dist.setDistance(si, sj, inter_dist);
					}
				}
			}
		}
		
		return mm_dist;
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
	public DistanceState addReport(Report r, String operation, double modifier, TriangleInequalityResponse tri_res, 
			HashMap<Character,Double> var_weights, ArrayList<String> errors) throws Exception {
		//convert report to states
		BeliefState sat_report = r.convertFormToStates(map.getVocab());
		BeliefState unsat_report = new BeliefState();
		//ArrayList<String> errors = new ArrayList<String>();
		
		//BeliefState unsat_report = //add all but sat_report;
		//if does not contain state in sat, must be a member of the unsat group
		for (State state: map.getPossibleStates().getBeliefs())
			if (!sat_report.contains(state))
				unsat_report.addBelief(state);
		
		//find weights here?
		//add weights to modByReport
		//false report
		if (r.getReportedVal() == 0)
			return modByReport(sat_report, unsat_report, operation, modifier, tri_res, var_weights, errors);
		//true report
		else 
			return modByReport(sat_report, unsat_report, operation, modifier, tri_res, var_weights, errors);

	}
	

	//
	//Specific UI2 stuff
	//
	
	
	/**
	 * Modify the DistanceState based on each State combination between b1 and b2. 
	 * An edge value between two state vertexes will be modified by a value found in the ReportFunction object
	 * 
	 * @param b1
	 * @param b2
	 * @param mod
	 * @param result
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	private DistanceState modByReport(BeliefState b1, BeliefState b2, ReportFunction mod, int result, ArrayList<String> errors) throws Exception {
		
		DistanceState update = new DistanceState(this);
		State s1, s2;
		double current_val;
		String formula;
		double formval;
		
		for (int i = 0; i < b1.getBeliefs().size(); i++)
		{
			s1 = b1.getBeliefs().get(i);
			for (int j = 0; j < b2.getBeliefs().size(); j++)
			{
				s2 = b2.getBeliefs().get(j);
				
				//current value in the grid
				current_val = update.map.getDistance(s1, s2);
				
				//get formula
				formula = findFormula(mod, s1, s2, result);
				
				//update trust graph value with formula
				formval = updateValue(current_val, formula, result);
				
				//set value in trust graph if the value is valid
				if (valueValid(current_val, formval, result))
					update.setMapMember(s1,s2,formval);
				else 
					throw new Exception("Updated Value does not satisfy TrustGraph Constraints: Formula invalid");
			}
		}
		
		return update;
	}
	
	
	/**
	 * An unrestricted setMapMember method. Sets s1 and s2 in the DistanceMap to the val parameter.
	 * 
	 * @param s1
	 * @param s2
	 * @param val
	 */
	public void setMapMember(State s1, State s2, double val) {
		map.setDistance(s1, s2, val);
	}
	
	/**
	 * Find the formula based on the type of report being applied to the DistanceState.
	 * Positive formula for positive reports and negative formula for negative reports.
	 * 
	 * @param mod
	 * @param s1
	 * @param s2
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private String findFormula(ReportFunction mod, State s1, State s2, int result) throws Exception {
		if (result == 1)
			return mod.findPosFormula(s1, s2);
		else if (result == 0)
			return mod.findNegFormula(s1, s2);
		else 
			throw new Exception("Result field invalid");
	}
	
	/**
	 * Find the updated value to set in the DistanceState. Return the new value produced using this formula.
	 * 
	 * @param currentval
	 * @param formula
	 * @param rep_result
	 * @return
	 * @throws Exception
	 */
	private double updateValue(double currentval, String formula, int rep_result) throws Exception {
		double updatedval;
		//if the formula is a double set it
		try {
			updatedval = Double.parseDouble(formula);
			if (rep_result == 1)
				return currentval + updatedval;
			return currentval - updatedval;
		//if the formula is a formula parse and find the new value using the formula
		} catch (Exception ex) {
			Function test = new Function(formula);
			Argument v = new Argument(Strings.function_variable + currentval);
			Expression e = new Expression(Strings.function_title, test, v);
			updatedval = e.calculate();
		}
		return updatedval;
	}
	
	/**
	 * Determines if the new value calculated using the formula is a valid value given the report.
	 * New values produced by a positive report must be >= the old value
	 * New values produced by a negative report must be <= the old value
	 * Values must be greater than 0.
	 * 
	 * @param oldvalue
	 * @param newvalue
	 * @param result
	 * @return
	 */
	private boolean valueValid(double oldvalue, double newvalue, int result) {
		if (result == 0)
		{
			if (newvalue <= oldvalue && newvalue > 0)
				return true;
		}
		else if (result == 1)
		{
			if (newvalue >= oldvalue)
				return true;
		}
		return false;
	}
	
	/**
	 * Add a report to the DistanceState and return the updated DistanceState
	 * 
	 * @param r
	 * @param mod
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	public DistanceState addReport(Report r, ReportFunction mod, ArrayList<String> errors) throws Exception {
	
		//generate Beliefstates that satisfy the report formula

		BeliefState sat_report = r.convertFormToStates(map.getVocab());
		BeliefState unsat_report = new BeliefState();
		int result = r.getReportedVal();
		
		//create Beliefstate that isn't satisfied by the report formula
		for (State state: map.getPossibleStates().getBeliefs())
			if (!sat_report.contains(state))
				unsat_report.addBelief(state);
		
		//return a DistanceState that has been modified by the report
		return modByReport(sat_report, unsat_report, mod, result, errors);
	}
}
