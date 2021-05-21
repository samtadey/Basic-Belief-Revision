/**
 * 
 */
package revision_ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import constants.Strings;
import data.UiData;

/**
 * @author sam_t
 *
 */
public class ParametrizedDiffPanel extends JPanel implements ActionListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//this is considered the unordered set
	JPanel list, buttons;
	DefaultListModel<Character> vlist;
	JList<Character> tolist;
	JButton up,down;
	
	Set<Character> varlist;
	
	public ParametrizedDiffPanel(Set<Character> vars) {	
		varlist = vars;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		list = new JPanel();
		//list.setPreferredSize(new Dimension(100,100));
		buttons = new JPanel();
		vlist = new DefaultListModel<Character>();
		
		//add variables to list
		for (Character c: vars)
		{
			vlist.addElement(c);
		}
		
		//add list to list pane
		tolist = new JList<Character>(vlist);
		//tolist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		list.add(tolist);
		
		//set up buttons
		up = new JButton(Strings.var_up);
		down = new JButton(Strings.var_down);
		up.addActionListener(this);
		down.addActionListener(this);
		
		//add buttons to button pane
		buttons.add(up);
		buttons.add(down);
		
		//add panes to main pane
		this.add(list);
		this.add(buttons);
	}
	
	/*
	 * Action handler
	 */
	public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals(Strings.var_up))
        {
        	//get selected index
        	int idx = this.tolist.getSelectedIndex();
        	System.out.println(idx);
        	moveUp(idx);
        } 
        else if (s.equals(Strings.var_down))
        {
        	int idx = this.tolist.getSelectedIndex();
        	System.out.println(idx);
        	moveDown(idx);
        }
//        else if (s.equals("submit")) //outside submit
//        {
//        	//set the UiData?
//        }
        //set teh order in ds
        Set<Character> newset = new LinkedHashSet<Character>();
        for (int i = 0; i < vlist.size(); i++)
        {
        	System.out.println(vlist.get(i));
        	newset.add(vlist.get(i));
        }
        	
        //set the order in data obj
        UiData.setVarOrder(newset);
	}
	
	
	private void moveUp(int idx) throws IndexOutOfBoundsException {
		if (idx > 0)
		{
			Character ele = this.vlist.remove(idx);
			this.vlist.add(idx-1, ele);
		}
	}
	
	
	public void moveDown(int idx) throws IndexOutOfBoundsException {
		if (idx < this.vlist.size() - 1 && idx >= 0)
		{
			Character ele = this.vlist.remove(idx);
			this.vlist.add(idx+1, ele);
		}
	}




	
}
