/**
 * 
 */
package ca.bcit.tadey.revision.trust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.translation.InputTranslation;

/**
 * @author sam_t
 *
 */
public class VariableWeights {
	
	private static final double DEFAULT_WEIGHT = 1;
	
	DistanceMap map;
	
	public VariableWeights(DistanceMap map) {
		this.map = map;
	}
	
	
	//not sure how to use sample points AND population mean
	private double findSd(double mean, double val_count, DistanceMap map) {
		
		double sd, distances = 0;
		int square = 2;
	
		for (State s1 : map.getDistances().keySet())
			for (State s2 : map.getDistances().get(s1).keySet())
				distances += Math.pow(map.getDistance(s1, s2) - mean, square);
		
		
		sd = Math.sqrt(distances / val_count);

		return sd;
	}
	
//	private double findMean(BeliefState b1, BeliefState b2, DistanceMap map) {
//		double sum = 0, val_count = 0;
//		
//		for (State s1 : b1.getBeliefs())
//			for (State s2 : b2.getBeliefs())
//			{
//				sum += map.getDistance(s1, s2);
//				val_count++;
//			}
//		
//		return sum / val_count;
//	}
	
	
	private double assignWeight(double pop_mean, double sample_mean, double sd, double range_min, double range_max) {
		double weight, mean_diff, num_sds;
		double max_sd = 3.0, mid = ((range_max - range_min) / 2.0) + range_min; //min + half of the range
		
		System.out.println("Pop mean: " + pop_mean + " Sample Mean: " + sample_mean + " SD: " + sd);
		
		//if sd is 0.0 return mid value as weight
		mean_diff = sample_mean - pop_mean;
		if (sd == 0)
			return mid;
		else
			num_sds = mean_diff / sd;
		
		//check that the number of sd's is not greater than 3 or less than -3
		if (num_sds >= max_sd)
			weight = range_max - 0.01;
		else if (num_sds <= -max_sd)
			weight = range_min + 0.01;
		else
			//if sd's is within normal range, find the correct weight value
			weight = mid + (num_sds / max_sd); //this right?
		
		return weight;
	}
	
	//passing in each var from the map object vocabulary itself, so there will be uniformity
	private double findWeight(char var, DistanceMap map) {
		double range_min = 0, range_max = 2;
		double weight, sample_mean = 0, pop_mean, val_count = 0, total = 0, sd;
		BeliefState tstates, fstates = new BeliefState(), allstates;
		
		tstates = InputTranslation.convertPropInput(Character.toString(var), map.getVocab());
		allstates = map.getPossibleStates();
		
		//separate true and false states given the variable
		for (State s : allstates.getBeliefs())
			if (!tstates.contains(s))
				fstates.addBelief(s);
		
		//find mean of sample
		for (State s1 : tstates.getBeliefs())
			for (State s2 : fstates.getBeliefs())
			{
				sample_mean += map.getDistance(s1, s2);
				val_count++;
			}
		sample_mean = sample_mean / val_count;
				
		//find mean of population by iterating through all possible distance values
		val_count = 0;
		for (State s1 : map.getDistances().keySet())
			for (State s2 : map.getDistances().get(s1).keySet())
			{
				total += map.getDistance(s1, s2);
				val_count++;
			}
		pop_mean = total / val_count;
		
		//find standard deviation
		sd = findSd(pop_mean, val_count, map);
		
		//assign a variable weight
		weight = assignWeight(pop_mean, sample_mean, sd, range_min, range_max);
		
		return weight;
	}
	
	
	//could make an object for this
	public HashMap<Character, Double> findVariableWeights() {
		HashMap<Character, Double> weights = new HashMap<Character, Double>();
		
		for (Character c: this.map.getVocab())
			weights.put(c, findWeight(c, this.map));
		
		return weights;
	}
	
	

	
	//tester class testing out how 
	//generating weights to change with trust graph distance values
	public static void main(String[] args) {
		
		HashMap<Character, Double> weights;
		Set<Character> vocab = new LinkedHashSet<Character>();
		vocab.add('A');
		vocab.add('B');
		
		DistanceState state = new DistanceState(vocab);
		
		VariableWeights vw = new VariableWeights(state.getMap());
		
		
		State s1 = new State("00");
		State s2 = new State("10");
		State s3 = new State("11");
		//state.getMap().stateToConsole();
		System.out.println("Before Change");
		weights = vw.findVariableWeights();
		System.out.println(weights);
		
		state.getMap().setDistance(s1, s2, 4);
		state.getMap().setDistance(s2, s3, 1.0);
		//state.setMapMember(null, null, 0, 0, null, null);
		
		System.out.println("After Change");
		weights = vw.findVariableWeights();
		System.out.println(weights);

		
		
		
	}
}
