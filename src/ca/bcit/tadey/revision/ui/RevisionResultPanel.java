/**
 * 
 */
package ca.bcit.tadey.revision.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import ca.bcit.tadey.revision.RevisionStateScore;
import ca.bcit.tadey.revision.trust.DistanceState;
import ca.bcit.tadey.revision.trust.RankingState;

/**
 * @author sam_t
 *
 */
public class RevisionResultPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static GridLayout visual;
	
	/**
	 * 
	 */
	public RevisionResultPanel(DistanceState distance, RankingState rank, RevisionStateScore score) {
		
		
		//set layout to number correct width and length
		//grid can be set to show all states
		//grid can be set to show only the states with min score
		
        visual = new GridLayout(distance.getMap().getPossibleStates().getBeliefs().size(), 3);
        this.setLayout(visual);
		//testing for a basic panel with 8 states
		//needs a DistanceState
		//needs a RankingState
		//needs a GeneralRevisionScore
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new RevisionResultPanel();
	}

}
