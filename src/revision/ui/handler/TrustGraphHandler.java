/**
 * 
 */
package revision.ui.handler;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import distance.DistanceState;
import language.BeliefState;
import language.State;
import revision.ui.TrustGraphPanel;

/**
 * @author sam_t
 *
 */
public class TrustGraphHandler {

	
	
	public void rebuildGrid(TrustGraphPanel trust, JPanel grid, BeliefState allstates, double[][] dist_val, ArrayList<ArrayList<JTextField>> grid_text, DistanceState distance) {
		State s1, s2;
		double dist;
		JTextField tf;
		int grids = allstates.getBeliefs().size();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		grid.removeAll();
		
		dist_val = new double[grids][grids];
    	grid_text = new ArrayList<ArrayList<JTextField>>();
    	
    	//init text field arrays
    	for (int i = 0; i < grids; i++)
    		grid_text.add(new ArrayList<JTextField>());
    	
    	
		//iterate through all textfields and set to distancestate
		grid.add(new JLabel("State/State"));
    	for (int i = 0; i < allstates.getBeliefs().size(); i++)
    		grid.add(new JLabel("" + allstates.getBeliefs().get(i).getState()));
    	
    	for (int i = 0; i < allstates.getBeliefs().size(); i++)
    	{
    		//danger
    		for (int j = -1; j < allstates.getBeliefs().size(); j++)
    		{
    			if (j < 0)
    				grid.add(new JLabel("" + allstates.getBeliefs().get(i).getState()));
    			else 
    			{
    				s1 = allstates.getBeliefs().get(i);
    				s2 = allstates.getBeliefs().get(j);
    				dist = distance.getDistance(s1, s2);
    				tf = new JTextField(Double.toString(dist));
    				if (i == j || j > i)
    					tf.setEditable(false);
    				
    				
    				//add listener
    				tf.addFocusListener(trust);
    				//add distance to data structure
    				dist_val[i][j] = dist;
    				//add to jtext array // this box maps to the distance array
    				grid_text.get(i).add(tf);
    				//add to ui
    				grid.add(tf);
    			}
    		}
    	}
	}
	
}
