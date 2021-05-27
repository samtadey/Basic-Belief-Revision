/**
 * 
 */
package propositional_translation;

import java.util.HashMap;
import java.util.Set;

import aima.core.logic.common.ParserException;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.ConjunctionOfClauses;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import solver.Formula;
import solver.FormulaSet;

/**
 * @author sam_t
 *
 */
public class InputTranslation {
	
	
	public static FormulaSet propToCNFForm(String formula, Set<Character> vocab) throws ParserException {
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
				else
				{
					char propvar = symbol.charAt(0);
    				
    				if (literal.isPositiveLiteral())
    					fclause.addValue(mappingChartoInt.get(propvar));
    				else
    					fclause.addValue(-mappingChartoInt.get(propvar));
				}
			}
			fset.addFormula(fclause);	
		}
		
		return fset;
	}
}
