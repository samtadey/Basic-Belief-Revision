/**
 * 
 */
package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sam_t
 *
 */
public class UiData {

	private static int num_vars;
	
	private static Set<Character> var_order;
	
	private static HashMap<Character, Double> var_weights = new HashMap<Character, Double>();
	
	private static String test;
	
	public UiData() {
		setVarOrder(new HashSet<Character>());
		//setVarWeights();
	}

	public static HashMap<Character, Double> getVarWeights() {
		return var_weights;
	}

	public static void setVarWeights(HashMap<Character, Double> vw) {
		var_weights = vw;
	}

	public static Set<Character> getVarOrder() {
		return var_order;
	}

	public static void setVarOrder(Set<Character> vo) {
		var_order = vo;
	}
	
	public static void addVarOrder(char c) {
		var_order.add(c);
	}
	
	public static void addVarWeights(char key, double val) {
		var_weights.put(key, val);
	}
	
	public static String getTest() {
		return test;
	}
	
	public static void setTest(String s) {
		test = s;
	}
	
//	public static void weightsToString() {
//		for (var_weights)
//	}
}
