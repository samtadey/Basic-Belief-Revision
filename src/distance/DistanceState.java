package distance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import aima.core.logic.common.ParserException;
import constants.ArithmeticOperations;
import distance.revision.TriangleInequalityResponse;
import distance.revision.TrustRevisionOperation;
import language.BeliefState;
import language.State;
import language.StateHelper;

/**
 * @author sam_t
 *
 */
public class DistanceState {
	
	public static final double DEFAULT_VAL = 2.0;
	
	private Set<Character> vocab;
	private BeliefState possible_states;
	private HashMap<State, HashMap<State, Double>> distances;
	
	/*
	 * Constructor for the DistanceState object
	 * Initiates all State combinations in the distances member, and sets all the distances to 0.0
	 * 
	 * @params
	 * 	Set<Character> representing the propositional variables
	 */
	public DistanceState(Set<Character> vocab) {
		this.vocab = vocab;
		//must initialize all possible states with a distance of 0
		//not easy to generate states as strings, thats why they are 
		possible_states = new BeliefState(StateHelper.generateStates(vocab.size()));
		HashMap<State, Double> inner;
		//state combinations and set distances
		
		distances = new HashMap<State, HashMap<State, Double>>();
		for (int i = 0; i < possible_states.getBeliefs().size() - 1; i++)
		{
			inner = new HashMap<State, Double>();
			for (int j = i+1; j < possible_states.getBeliefs().size(); j++)	
				inner.put(possible_states.getBeliefs().get(j), DEFAULT_VAL);
			distances.put(possible_states.getBeliefs().get(i), inner);
		}
	}
	
	/*
	 * Getter function for the vocab member variable
	 * 
	 * @returns 
	 * 	Set<Character> as the propositional characters
	 */
	public Set<Character> getVocab() {
		return this.vocab;
	}
	
	/*
	 * Getter function for the distances member variable
	 * 
	 * @returns
	 * 	distances as a HashMap<State, HashMap<State, Double>>
	 */
	public HashMap<State, HashMap<State, Double>> getDistances() {
		return this.distances;
	}
	
	
	public BeliefState getPossibleStates() {
		return this.possible_states;
	}
	/*
	 * Getter function for the distance between two state objects
	 * 
	 * @params
	 * 	State s1
	 * 	State s2
	 * @returns
	 * 	The distance between the two parameter states as a double.
	 * 	If the states are equal, the distance returned is 0.
	 */
	public double getDistance(State s1, State s2) throws NullPointerException {
		
		int result = s1.compareTo(s2);
		//do a string compare
		//smaller states will always be the first argument
		if (result < 0)
			return this.distances.get(s1).get(s2);
		//first state is larger, therefore we must switch order
		else if (result > 0)
			return this.distances.get(s2).get(s1);
		
		//states are equal
		return 0.0;
	}
	
	/*
	 * Setter function for the distance between two state objects
	 * 
	 * @params
	 * 	State s1
	 * 	State s2
	 *  double dist as the distance between s1 and s2
	 * 
	 * @throws
	 * 
	 */
	public void setDistance(State s1, State s2, double dist) throws NullPointerException {
		
		if (dist < 0)
		{
			//should I set the distance TO zero in this case?
			//throw new DistanceStateException("Attempting to set " + s1.getState() + "/" + s2.getState() + " below zero");
			System.out.println("Attempting to set " + s1.getState() + "/" + s2.getState() + " below zero: No Change");
			return;
		}
		
		int result = s1.compareTo(s2);
		
		if (result < 0)
			this.distances.get(s1).replace(s2, dist);
		//first state is larger, therefore we must switch order
		else if (result > 0)
			this.distances.get(s2).replace(s1, dist);
		
		//if states are equal to nothign
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
		
		State s1, s2, intermediate;
		double current_val, new_val, lst_invalid_val;
		ArrayList<String> errors = new ArrayList<String>();
		
		for (int i = 0; i < b1.getBeliefs().size(); i++)
		{
			s1 = b1.getBeliefs().get(i);
			for (int j = 0; j < b2.getBeliefs().size(); j++)
			{
				s2 = b2.getBeliefs().get(j);
				
				current_val = this.getDistance(s1, s2);
				new_val = TrustRevisionOperation.reviseValue(current_val, mod_value, trust_op);
				
				if (current_val != new_val)
				{
					intermediate = checkTriangleInequality(s1, s2, new_val);
					//if inter is not null -> the state returned has violated triangle inequality
					if (intermediate != null)
					{
						//get value that violates triangle inequality
						lst_invalid_val = this.getDistance(s1, intermediate) + this.getDistance(intermediate, s2);
						//set errors
						errors.add(s1.getState() + "/" + s2.getState() + " Triangle Inequality Violated by " 
								+ intermediate.getState() + " value proposed: " + new_val);
						//invalid value is (s,t) + (t,u)
						//handle triangle inequality with TriangleInequalityResponse object
						new_val = tri_res.handleTriangleInequality(current_val, lst_invalid_val);
					}
					//set distance value to the return value
					this.setDistance(s1, s2, new_val);
				}
			}
		}
		return errors;
	}
	
	
	/*
	 * Checks a proposed distance value for triangle inequality among intermediate states
	 * 
	 * @returns
	 * 	State that violates triangle inequality between the States s and u. 
	 *  Returns null if triangle inequality is not violated
	 */
	public State checkTriangleInequality(State s, State u, double proposed_val) {
		double hypot, edge_val;
		double a,b,c; //actuals
		//State tri_ineq = null;
		
		for (State t : this.possible_states.getBeliefs())
		{
			if (!s.equals(t) && !u.equals(t))
			{
				a = this.getDistance(s, t);
				b = this.getDistance(t, u);
				c = this.getDistance(s, u);

				hypot = a + b;
				
				//we are increasing a value
				if (proposed_val > c)
				{
					if (proposed_val > hypot)
						return t;
				}
				else //decreasing a value
				{		
					edge_val = proposed_val + a;
					if (edge_val < b)
						return t;
						//return s;
					edge_val = proposed_val + b;
					if (edge_val < a)
						return t;
					
				}
			}
		}
		return null;
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
		BeliefState sat_report = r.convertFormToStates(this.vocab);
		BeliefState unsat_report = new BeliefState();
		ArrayList<String> errors = new ArrayList<String>();
		
		//BeliefState unsat_report = //add all but sat_report;
		//if does not contain state in sat, must be a member of the unsat group
		for (State state: this.possible_states.getBeliefs())
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
	
	
	/*
	 * Prints state combinations and their distances to the console
	 */
	public void stateToConsole() {
		for (Map.Entry<State, HashMap<State, Double>> entry: distances.entrySet())
		{
			State key = entry.getKey();
			for (Map.Entry<State, Double> inner: entry.getValue().entrySet())
			{
				System.out.println(key.getState() + " " + inner.getKey().getState() + " =  " + inner.getValue());
			}
		}
	}
	
	
//	public static void main(String[] args) {
////		Set<Character> vocab = new LinkedHashSet<Character>();
////		vocab.add('A');
////		vocab.add('B');
////		vocab.add('C');
////		
//////		
//////		State s1 = new State("000");
//////		State s2 = new State("001");
//////		State s3 = new State("111");
//////		State s4 = new State("101");
//////		State s5 = new State("010");
//////		
////		DistanceState dist = new DistanceState(vocab);	
////		Report r = new Report("A & B", 0);
////		Report r2 = new Report("A & B", 1);
////		dist.stateToConsole();
////		dist.addReport(r);
////		System.out.println("After Adding Report");
////		dist.stateToConsole();
////		dist.addReport(r2);
////		System.out.println("After Adding Report 2");
////		dist.stateToConsole();
////		
////		dist.stateToConsole();
//	}
//
	
	
}



