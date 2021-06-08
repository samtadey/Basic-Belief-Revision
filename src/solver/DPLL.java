package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import language.BeliefState;
import language.State;
import language.StateHelper;


/**
 * @author sam_t
 * 
 * Change into a SAT, ALLSAT, and DPLL Class?
 * 
**/
public class DPLL {
	
	private static boolean runDPLL(FormulaSet set, ArrayList<Integer> soln) {
		int literal;
		int counts[], uniq[];
		Set<Integer> units;
		FormulaSet newset;

		
		if (set.hasEmptyClause())
			return false;
		
		uniq = set.countLiteralsUnique();
		if (set.isConsistent(uniq))
		{
			for (Formula f : set.getFormulas())
				for (int i : f.getFormula())
					soln.add(i);
			
			return true;
		}
		
		//change to while
		//findunitvalues call as while condition
		newset = new FormulaSet(set);
		do {
			units = findUnitValues(newset);
			newset = runUnitProp(units, newset, soln);
		} while (units.size() > 0);
		
		
		uniq = newset.countLiteralsUnique();
		newset = pureListAssign(uniq, newset, soln);
		
		//don't think I need to do this again
		uniq = newset.countLiteralsUnique();
		
		if (newset.hasEmptyClause())
			return false;
		
		if (newset.isConsistent(uniq))
		{
			for (Formula f : newset.getFormulas())
				for (int i : f.getFormula())
					soln.add(i);
			
			return true;
		}
		
		counts = newset.countLiterals();
		literal = chooseHighestLiteralCount(counts);
		
		//no new literal to try found, therefore must be false
//		if (literal < 1)
//			return false;

		return runDPLL(enactDecision(literal, newset, soln), soln) || runDPLL(enactDecision(-literal, newset, soln), soln);
	}
	
	/*
	 * This method is used to generate a new FormulaSet following a literal assignment.
	 * The literal is added to the solution list.
	 * The clauses containing that literal are removed from the set.
	 * 
	 * @params
	 * 	int literal - the literal being used in the solution
	 * 	FormulaSet set - the current set being evaluated
	 *  ArrayList<Integer> soln - list of literals that have been tried/eliminated by the algorithm
	 *  
	 * @returns
	 * 	FormulaSet that is a subset of the FormulaSet 'set'
	 */
	private static FormulaSet enactDecision(int literal, FormulaSet set, ArrayList<Integer> soln) {
		soln.add(literal);
		return eliminateFormulas(literal, set);
	}
	
	/*
	 * This method removes Formulas from the Formulaset that are satisfied by the literal argument
	 * Formulas that contain the negation to the literal will add all other literals in the formula BUT that negation
	 * 
	 * @params
	 * 	int literal
	 * 	FormulaSet set
	 * 
	 * @returns
	 * 	FormulaSet that is a subset of the argument FormulaSet set
	 */
	private static FormulaSet eliminateFormulas(int literal, FormulaSet set) {
		FormulaSet newset = new FormulaSet(set.getVarcount());
		Formula clause;
		
		for (int i = 0; i < set.getFormulas().size(); i++)
		{
			clause = set.getFormulas().get(i);
			//did not find literal, therefore must be added to the new set
			if (!clause.isSatisfiedBy(literal))
			{
				//if find negation we need to create a new formula without that literal
				if (clause.isSatisfiedBy(-literal))
					clause = clause.addAllBut(-literal);
				
				newset.addFormula(clause);
			}
		}
		return newset;
	}
	
	/*
	 * 
	 * ******** Unit Propagation ********
	 * 
	 */

	
	private static Set<Integer> findUnitValues(FormulaSet set) {
		Set<Integer> units = new HashSet<Integer>();
		int unit_value;
				
		for (int i = 0; i < set.getFormulas().size(); i++)
		{
			//add multiple unit clauses if they are the same sign
			//do not add the negation
			if (set.getFormulas().get(i).getFormula().size() == 1)
			{
				unit_value = set.getFormulas().get(i).getFormula().get(0);
				if (!units.contains(unit_value) && !units.contains(-unit_value))
					units.add(unit_value);
			}
		}
		
		return units;
	}
	
	
	/*
	 * The Unit Propagation method used in the DPLL algorithm.
	 * Unit Clauses are clauses with containing only one literal. Since these clauses must be true for the FormulaSet to be true
	 * they can be eliminated from the FormulaSet, along with any other clause containing the variable found in the unit clause. The
	 * resulting FormulaSet is reduced in size.
	 * 
	 * @params
	 * 	ArrayList<Integer> unit_val - a list of unit literal values
	 *  FormulaSet set
	 *  ArrayList<Integer> soln - solution list
	 * 
	 * @return
	 *  FormulaSet as a subset of the method argument
	 */
	private static FormulaSet runUnitProp(Set<Integer> unit_val, FormulaSet set, ArrayList<Integer> soln) {
		FormulaSet newset;
		
		if (unit_val.size() == 0)
			return set;
		
		newset = new FormulaSet(set);
		
		for (Integer unit: unit_val) 
		{
			//add to solution list
			soln.add(unit);
			//run unit propagation
			newset = eliminateFormulas(unit, newset);
		}

		return newset;
	}
	
	
	/*
	 * 
	 * ******** Pure Literal Propagation ********
	 * 
	 */
	
	/*
	 * Finds the pure literals in the FormulaSet, using the unique count of variables
	 * 
	 * @params
	 * 	int[] uniq_count - a array containing the unique counts of the literals
	 * 	int varcount - number of variables in the FormulaSet
	 * 
	 * @return
	 *  ArrayList<Integer> containing the literals that are pure in the FormulaSet
	 */
	private static ArrayList<Integer> findPure(int[] uniq_count, int varcount) {
		ArrayList<Integer> purelist = new ArrayList<Integer>();
		
		for (int i = 0; i < varcount; i++)
		{
			if (i + varcount < uniq_count.length)
			{
				if (uniq_count[i] > 0 && uniq_count[i + varcount] == 0)
					purelist.add(i+1);
				else if (uniq_count[i] == 0 && uniq_count[i + varcount] > 0)
					purelist.add(-(i+1));
			}
		}
		
		return purelist;
	}
	

    /*
     * This method finds the pure literals in the FormulaSet and removes them.
     * Pure Literals are literals without negations in the FormulaSet. This means they can only be one value. They can be removed
     * from consideration in the FormulaSet.
     * 
     * @params
     *  int[] uniq_count - a array containing the unique counts of the literals
     *  FormulaSet set
     *  ArrayList<Integer> soln - current solutions for the formulas
     * 
     * @return
     *  FormulaSet as a subset of the original set
     */
	private static FormulaSet pureListAssign(int[] uniq_count, FormulaSet set, ArrayList<Integer> soln) {
		ArrayList<Integer> purelist = findPure(uniq_count, set.getVarcount());
		ArrayList<Formula> forms = (ArrayList<Formula>) set.getFormulas();
		FormulaSet newset = new FormulaSet(set.getVarcount());
		boolean found;
		
		//add pure literals to solution list
		for (int i = 0; i < purelist.size(); i++)
			soln.add(purelist.get(i));
		
		//remove clauses that contain pure literals
		for (int i = 0; i < forms.size(); i++)
		{
			found = false;
			
			//can we break at found?
			//if were removing clauses, we could break
			for (int j = 0; j < purelist.size() && !found; j++)
				if (forms.get(i).isSatisfiedBy(purelist.get(j)))
					found = true;

			if (!found)
				newset.addFormula(forms.get(i));
		}
		
		return newset;
	}
	
	
	/*
	 * This method determines which literal to choose when trying values in the algorithm
	 * The literal chosen is the one occuring the most in the FormulaSet 
	 *
	 * @params
	 *  int[] lit_count containing the counts of the literals 
	 * 
	 * @return
	 *  literal as an int
	 */
	private static int chooseHighestLiteralCount(int lit_count[]) {
		int highest = 0;
		int val = 0;
		
		for (int i = 0; i < lit_count.length; i++)
		{
			//if (lit_count[i] > highest && !sol.isValueAssigned(i+1))
			if (lit_count[i] > highest)
			{
				highest = lit_count[i];
				val = i+1;
			}
		}	
		//returning the value of the literal
		return val;
	}
	
	/*
	 * This method runs a DPLL SAT solver and prints the solution to the console if found
	 * 
	 * @params
	 * 	Dimacs input in ArrayList<ArrayList<Integer>> form
	 * 	int num_vars - the number of variables in the formulas
	 * 
	 * @return
	 * 	boolean results of the search
	 */
	public boolean satDpll(ArrayList<ArrayList<Integer>> dimacs, int num_vars) {
		
		//dimacs find variables
		System.out.println(num_vars + " vars");
		FormulaSet forms = new FormulaSet(num_vars);
		ArrayList<Integer> solution = new ArrayList<Integer>();

		forms.setFormulas(dimacs);
		forms.toConsole();

		boolean sat = DPLL.runDPLL(forms, solution);
		System.out.println(sat);
		
		//print solution
		if (sat)
		{
			int[] soln = DPLL.finalSoln(solution, forms.getVarcount());
			for (int i = 0; i < soln.length; i++)
				System.out.print(soln[i] + " ");
			System.out.println(" ");
		}
		
		return sat;
	}
	
	
	
	/*
	 * The ALLSAT algorithm for finding all of the satisfiabilities for a formula set
	 * This ALLSAT uses DPLL for the SAT and blocking clauses to find all solutions
	 *  
	 * @params
	 * 	Dimacs input in ArrayList<ArrayList<Integer>> form
	 * 	int num_vars - the number of variables in the formulas
	 * 	
	 * @return
	 *  All solutions as a ArrayList<ArrayList<Integer>>
	 */
	public BeliefState allSatDpllBlock(FormulaSet set) {
		FormulaSet forms;
		Formula blocking;
		ArrayList<ArrayList<Integer>> allsol = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> allassignments;
		ArrayList<Integer> solution = new ArrayList<Integer>();
		int[] soln;
		BeliefState beliefs = new BeliefState();
		int zeroes;
		
		forms = new FormulaSet(set);
		//forms.setFormulas(dimacs);
			
		while(DPLL.runDPLL(forms, solution)) // DPLL
		{
			//find the simplified solution from the solution tree
			soln = DPLL.finalSoln(solution, forms.getVarcount());
			//add solution to the list of solutions
			
			//if there is a zero in the solution, that means we must create two new states, one for the pos and one for the negation
			//if no zeroes the second argument will not be used
			zeroes = countZeroes(soln);
			
			if (zeroes > 0)
			{
				allassignments = StateHelper.generateAssignments(zeroes);
				System.out.println(allassignments);
				for (int i = 0; i < allassignments.size(); i++)
					beliefs.addBelief(solutionToBelief(soln, allassignments.get(i)));
			}
			else if (zeroes == 0)
			{
				beliefs.addBelief(solutionToBelief(soln));
			}
			

			allsol.add(convert(soln));
			//generate the new blocking clause for the new solution
			blocking = generateBlockingClause(soln);
			//add to original FormulaSet
			forms.addFormula(blocking);			//ALLSAT IMPLEMENTATION -- BLOCKING CLAUSES
			//zero solution array
			solution.clear();
		}
		return beliefs;
	}
	
	
	private int countZeroes(int[] soln) {
		int count = 0;
		for (int i = 0; i < soln.length; i++)
			if (soln[i] == 0)
				count++;
		return count;
	}
	
	//must handle the case where a variable is 0, which means it can take either value
	//and must be convereted into two states
	private State solutionToBelief(int[] soln, ArrayList<Integer> z_assign) throws IndexOutOfBoundsException {
		StringBuilder s_input = new StringBuilder();
		int z = 0;
		
		for (int i = 0; i < soln.length; i++)
		{
			if (soln[i] < 0)
				s_input.append("0");
			else if (soln[i] > 0)
				s_input.append("1");
			else
				s_input.append(z_assign.get(z++)); //could throw errors if num of zeroes was greater than calculated
		}
		
		return new State(s_input.toString());
	}
	
	private State solutionToBelief(int[] soln) {
		StringBuilder s_input = new StringBuilder();
		
		for (int i = 0; i < soln.length; i++)
		{
			if (soln[i] < 0)
				s_input.append("0");
			else if (soln[i] > 0)
				s_input.append("1");

		}
		
		return new State(s_input.toString());
	}
	
	/*
	 * Generates the blocking clause for the ALLSAT algorithm
	 * The blocking clause is the inverse of the most recent solution. Therefore when DPLL attempts to 
	 * look for more satisfiabilities it will not produce the same answer multiple times
	 */
	private static Formula generateBlockingClause(int[] solution) {
		Formula bc = new Formula();
		
		//if literal is not 0 -> matters in the assignment
		//add the negation to the new blocking clause
		for (int i = 0; i < solution.length; i++)
			if (solution[i] != 0)
				bc.addValue(-solution[i]);
		return bc;
	}
	

	
	/*
	 * 
	 * ******** Helpers ********
	 * 
	 */
	
	/*
	 * Converts the int[] argument into an ArrayList<Integer>
	 */
	private static ArrayList<Integer> convert(int[] arr) {
		ArrayList<Integer> list = new ArrayList<Integer>(arr.length);
		
		for (int i = 0; i < arr.length; i++)
			list.add(arr[i]);
		return list;
	}
	
	/*
	 * Traverses the solution list in reverse to find the true values for the formula
	 * If a value is true it will not be chosen again by the algorithm, therefore the last occurance of a literal
	 * is the answer to the formula
	 */
	private static int[] finalSoln(ArrayList<Integer> soln, int vars) throws IndexOutOfBoundsException {
		int[] assign = new int[vars];
		boolean[] found = new boolean[vars];
		Arrays.fill(found, false);
		Arrays.fill(assign, 0);
		
		//reverse
		for (int i = soln.size() - 1 ; i >= 0; i--)
		{
			if (!found[Math.abs(soln.get(i)) - 1])
			{
				assign[Math.abs(soln.get(i)) - 1] = soln.get(i);
				found[Math.abs(soln.get(i)) - 1] = true;
			}
		}
			
		return assign;
	}

}
