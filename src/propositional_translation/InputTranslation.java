/**
 * 
 */
package propositional_translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.logic.common.ParserException;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.ConjunctionOfClauses;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import constants.Strings;
import language.BeliefState;
import language.State;
import solver.DPLL;
import solver.Formula;
import solver.FormulaSet;

/**
 * 
 * The static class InputTranslation is used to convert inputs into system objects
 * 
 * @author sam_t
 *
 */
public class InputTranslation {
	
    /**
     * @param
     * 	String input
     * 
     * Recieves a string representation of a BeliefState and converts it into
     * a BeliefState object.
     */
    public static BeliefState parseInput(String input) {
    	
	   	BeliefState bstate = new BeliefState();     
	    for (String line : input.split("\n")) 
	    {
	    	bstate.addBelief(new State(line));
	    }
	    return bstate;
    }
    
    /**
     * Converts a specified string input into a list of character
     * 
     * @param input as a string of characters separated by commas
     * @return an ArrayList of Characters
     * @throws Exception if the input does not meet the parser's specifications
     */
	public static ArrayList<Character> getVocabAsList(String input) throws Exception {
		ArrayList<Character> vocab = new ArrayList<Character>();
		
		for (String var : input.split(",")) 
	    {
	    	if (var.length() == 1)
	    		vocab.add(var.charAt(0));
	    	else
	    		throw new Exception(Strings.error_bad_vocab);
	    }
		return vocab;
	}
	
	/**
	 * Converts a specified string input into a set of character
	 * 
     * @param input as a string of characters separated by commas
     * @return an LinkedHashSet of Characters
     * @throws Exception if the input does not meet the parser's specifications
	 */
	public static Set<Character> getVocabAsSet(String input) throws Exception {
		Set<Character> vocab = new LinkedHashSet<Character>();
		
		for (String var : input.split(",")) 
	    {
	    	if (var.length() == 1)
	    		vocab.add(var.charAt(0));
	    	else
	    		throw new Exception(Strings.error_bad_vocab);
	    }
		return vocab;
	}
    
	
	/**
	 * Converts an ArrayList of character into a set
	 * 
	 * @param arr
	 * @return arr represented as a LinkedHashSet
	 */
    public static Set<Character> arrToSet(ArrayList<Character> arr) {
    	Set<Character> set = new LinkedHashSet<Character>();
    	for (Character c: arr)
    		set.add(c);
    	
    	return set;
    }
    
    
    /**
     * Converts  Set of character into an ArrayList
     * 
     * @param set
     * @return set now represented by an ArrayList
     */
    public static ArrayList<Character> setToArr(Set<Character> set) {
    	ArrayList<Character> arr = new ArrayList<Character>();
    	
    	for (Character c : set)
    		arr.add(c);
    	
    	return arr;
    }
	
    

    
    /**
     * Converts a propositional formula (represented as a string) into a FormulaSet object (Conjunctive Normal Form). 
     * This method uses aima.core.logic parsing to convert String input into sets of clauses and literals, which are then converted into
     * Formula and FormulaSet objects.
     * 
     * @param formula propositional formula input
     * @param vocab as a set of characters
     * @return FormulaSet object
     * @throws ParserException
     */
	private static FormulaSet propToCNFForm(String formula, Set<Character> vocab) throws ParserException {
		//parser representation of literals, clauses
		PLParser p;
		Sentence sparsed;
		ConjunctionOfClauses prepross;
		Set<Clause> clauses;
		Set<Literal> literals;
		String symbol;
		
		//system representation of clauses
		FormulaSet fset;
		Formula fclause;
		
		HashMap<Character, Integer> mappingChartoInt = new HashMap<Character, Integer>();
		//create mapping
		int i = 1;
		for (Character var: vocab)
			mappingChartoInt.put(var, i++);

		p = new PLParser();
		sparsed = p.parse(formula);		
		prepross = ConvertToConjunctionOfClauses.convert(sparsed);
		
		clauses = prepross.getClauses();
		fset = new FormulaSet(vocab.size());
		
		for (Clause clause: clauses)
		{
			fclause = new Formula();
			literals = clause.getLiterals();
			for (Literal literal : literals)
			{
				symbol = literal.getAtomicSentence().getSymbol();
				if (symbol.length() != 1)
					throw new ParserException("Variable Length Not 1");

				char propvar = symbol.charAt(0);
				if (literal.isPositiveLiteral())
					fclause.addValue(mappingChartoInt.get(propvar));
				else
					fclause.addValue(-mappingChartoInt.get(propvar));
			}
			fset.addFormula(fclause);	
		}
		
		return fset;
	}
	
    /**
     * Converts a propositional formula into a BeliefState. This is done by converting the formula into conjunctive normal form, represented by a FormulaSet object.
     * Next, the FormulaSet is used in the ALLSAT DPLL algorithm to find all satisfying assignments for the propositional formula. The result of this algorithm is
     * a BeliefState containing all satisfying assignments.
     * 
     * 
     * @param text as the propositional formula
     * @param chars as a set of propositional variables
     * @return BeliefState as all satisfying assignments for the propositional formula
     * @throws ParserException
     */
    public static BeliefState convertPropInput(String text, Set<Character> chars) throws ParserException {
    	FormulaSet formset;
		BeliefState soln;
		//probs some checking here
		formset = InputTranslation.propToCNFForm(text, chars);

		DPLL dpll = new DPLL();
		soln = dpll.allSatDpllBlock(formset);
		
		return soln;
    }
	
}
