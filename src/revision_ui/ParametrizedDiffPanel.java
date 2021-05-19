/**
 * 
 */
package revision_ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * @author sam_t
 *
 */
public class ParametrizedDiffPanel<T> extends JPanel implements ActionListener{

	//this is considered the unordered set
	private Set<T> vars;
	JPanel list, buttons;
	DefaultListModel<T> vlist;
	JList<T> tolist;
	JButton up,down;
	
	public ParametrizedDiffPanel(Set<T> vars) {
		this.vars = vars;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		list = new JPanel();
		buttons = new JPanel();
		
		vlist = new DefaultListModel<T>();
		
		for (T c: vars)
		{
			vlist.addElement(c);
		}
		
		tolist = new JList<T>(vlist);
		list.add(tolist);
		
		//set up buttons
		up = new JButton("Move Up");
		down = new JButton("Move Down");
		up.addActionListener(this);
		down.addActionListener(this);
		
		buttons.add(up);
		buttons.add(down);
		
		this.add(list);
		this.add(buttons);
	}
	
	
	public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Move Up"))
        {
        	//get selected index
        	int idx = this.tolist.getSelectedIndex();
        	System.out.println(idx);
        	moveUp(idx);
        } 
        else if (s.equals("Move Down"))
        {
        	int idx = this.tolist.getSelectedIndex();
        	System.out.println(idx);
        	moveDown(idx);
        }
	}
	
	private void moveUp(int idx) throws IndexOutOfBoundsException {
		if (idx > 0)
		{
			T ele = this.vlist.remove(idx);
			this.vlist.add(idx-1, ele);
		}
	}
	
	
	public void moveDown(int idx) throws IndexOutOfBoundsException {
		if (idx < this.vlist.size() - 1 && idx >= 0)
		{
			T ele = this.vlist.remove(idx);
			this.vlist.add(idx+1, ele);
		}
	}




	
}
