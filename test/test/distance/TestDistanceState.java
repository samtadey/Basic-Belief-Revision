package test.distance;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.trust.DistanceMap;
import ca.bcit.tadey.revision.trust.Report;

public class TestDistanceState {
	
	private DistanceMap distState;
	
	private Set<Character> vocab;
	
	private State s1;
	private State s2;
	private State s3;

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		this.vocab = new LinkedHashSet<Character>();
//		vocab.add('A');
//	}

	@Before
	public void setUp() throws Exception {
		this.vocab = new LinkedHashSet<Character>();
		vocab.add('A');
		vocab.add('B');
		vocab.add('C');
		
		distState = new DistanceMap(vocab);
		
		s1 = new State("000");
		s2 = new State("111");
		s3 = new State("1010");
		
		
	}
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	/*
	 * Testing getDistance
	 */
	
	@Test
	public void getDistanceValueOfStatesInOrder() {
		assertEquals(" " , DistanceMap.DEFAULT_VAL, distState.getDistance(s1, s2), 0.5);
	}
	@Test
	public void getDistanceValueOfStatesOutOfOrder() {
		assertEquals(" " , DistanceMap.DEFAULT_VAL, distState.getDistance(s2, s1), 0.5);
	}
	@Test
	public void getDistanceValueOfStateNotExist() throws Exception {
		expected.expect(Exception.class);
		distState.getDistance(s2, s3);
	}
	@Test
	public void getDistanceValueOfNullState() {
		expected.expect(Exception.class);
		distState.getDistance(s2, null);
	}
	
	/*
	 * Testing setDistance
	 */
	
	@Test
	public void setDistanceValueToValueNotZero() {
		double val = 3.0;
		
		distState.setDistance(s1, s2, val);
		assertEquals(" " , DistanceMap.DEFAULT_VAL, distState.getDistance(s1, s2), val);
	}
	@Test
	public void setDistanceValueToValueBelowZero() {
		double val = -2.0;
		double val_before = distState.getDistance(s1, s2);
		distState.setDistance(s1, s2, val);
		assertEquals(" " , DistanceMap.DEFAULT_VAL, distState.getDistance(s1, s2), val_before);
	}
	
//	//nothing will happen if key doesn't exist in hashmap
//	@Test
//	public void setDistanceValueButStateIsInvalid() {
//		expected.expect(Exception.class);
//		distState.setDistance(s1, s3, 0);
//	}
	
	@Test
	public void setDistanceValueButStateIsNull() {
		expected.expect(Exception.class);
		distState.setDistance(s1, null, 0);
	}
	

	
	/*
	 * Test Add Report methods
	 */
//	@Test
//	public void addReportToDistanceFunctionAllDistancesToChangeAreChangedWhenReportFalse() {
//		DistanceState model = new DistanceState(vocab);
//		DistanceState modified = new DistanceState(vocab);
//		
//		State one = new State("000");
//		State two = new State("001");
//		State three = new State("010");
//		State four = new State("011");
//		State five = new State("100");
//		State siz = new State("101");
//		State seven = new State("110");
//		State eight = new State("111");
//		
//
//		//what should happen to the hashmap
//		model.setDistance(seven, one, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(seven, two, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(seven, three, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(seven, four, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(seven, five, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(seven, siz, DistanceState.DEFAULT_VAL+1);
//		
//		model.setDistance(eight, one, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(eight, two, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(eight, three, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(eight, four, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(eight, five, DistanceState.DEFAULT_VAL+1);
//		model.setDistance(eight, siz, DistanceState.DEFAULT_VAL+1);
//		
//		Report r = new Report("A & B", 0);
//		modified.addReport(r);
//		
//		assertTrue(model.equals(modified));
//	}
	
//	public void addReportToDistanceFunctionSomeDistancesToChangeAreChanged() {
//		DistanceState model = new DistanceState(vocab);
//		Report r = new Report("A & B", 0);
//	}
}
