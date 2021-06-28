/**
 * 
 */
package revision.ui;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import distance.DistanceState;
import distance.RankingState;
import distance.revision.RevisionStateScore;

/**
 * @author sam_t
 *
 */
public class RevisionResultPanel extends JPanel {

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
