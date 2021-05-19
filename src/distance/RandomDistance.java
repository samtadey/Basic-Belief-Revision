/**
 * 
 */
package distance;

import java.util.Random;
import java.util.Set;

import language.State;

/**
 * @author sam_t
 *
 */
public class RandomDistance extends Distance {
	
	public RandomDistance(Set<Character> vars) {
		super(vars);
	}

	@Override
	public double getDistance(State s1, State s2) {
		Random rand;
		int upper;
	
		if (s1.getState().length() != s2.getState().length())
		{
			System.out.println("States not same length");
			return -1;
		}
		
		//upper bound not inclusive , therefore range is 0 - length + 1
		upper = s1.getState().length() + 1;
		rand = new Random();
		//will cast and thats fine
		return rand.nextInt(upper);
	}

}
