package revision_ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.UiData;

public class TestComp extends JPanel implements ActionListener {

	private JTextField t;
	private JLabel l;
	
	public TestComp() {
		l = new JLabel("test");
		t = new JTextField(10);
		
		this.setBackground(Color.ORANGE);
		this.add(l);
		this.add(t);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(t.getText());
		l.setText(t.getText());
		//set data in component handler
		UiData.setTest(t.getText());
	}
}
