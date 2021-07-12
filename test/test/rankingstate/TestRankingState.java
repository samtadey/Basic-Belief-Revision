/**
 * 
 */
package test.rankingstate;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.trust.RankingState;



/**
 * @author sam_t
 *
 */
public class TestRankingState {

	private ArrayList<Character> vocab;
	private RankingState rs;
	
	private State s1,s2,s3,s4,s5,s6,s7,s8;
	
	@Before
	public void setUp() throws Exception {
		this.vocab = new ArrayList<Character>();
		vocab.add('A');
		vocab.add('B');
		vocab.add('C');
		rs = new RankingState(vocab);
		
		s1 = new State("000");
		s2 = new State("010");
		s3 = new State("110");
		s4 = new State("001");
		s5 = new State("100");
		s6 = new State("111");
		s7 = new State("101");
		s8 = new State("011");
		rs.setRank(s2, 0);
		rs.setRank(s3, 3);
		rs.setRank(s1, 2);
		rs.setRank(s4, 4);
		rs.setRank(s5, 2);
		rs.setRank(s6, 3);
		rs.setRank(s7, 5);
		rs.setRank(s8, 5);
	}
	
	
	@Test
	public void setRankNewBucket() {
		rs.setRank(s8, 7);
		assertTrue(rs.getRank(s8) == 7);
	}
	
	public void setRankOldBucket() {
		rs.setRank(s3, 2);
		assertTrue(rs.getRank(s3) == 2);
	}
	
	
}
