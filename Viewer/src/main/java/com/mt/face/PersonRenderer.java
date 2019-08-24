package com.mt.face;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PersonRenderer extends JPanel implements ListCellRenderer<Person>
{

	@Override
	public Component getListCellRendererComponent(JList<? extends Person> list, Person person, int index,
			boolean isSelected, boolean cellHasFocus)
	{
		setLayout(new BorderLayout());
		JButton goToFirstSeen = new JButton();
		goToFirstSeen.setText(person.getFirstTime() + "");
		goToFirstSeen.setFont(Window.HELVETICA_SMALL);
		goToFirstSeen.setOpaque(true);
		goToFirstSeen.setBackground(ColorScheme.LIGHT_TURQUOISE);
		goToFirstSeen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//go to the video at the time this person was first seen
				System.out.println("the button is working");
			}
		});
		JLabel face = new JLabel();
		face.setIcon(person.getFace());
		JLabel name = new JLabel();
		name.setText(person.getName());
		name.setFont(Window.HELVETICA_SMALL);
		add(face, BorderLayout.WEST);
		JPanel east = new JPanel();
		east.setLayout(new BorderLayout());
		east.add(name, BorderLayout.NORTH);
		east.add(goToFirstSeen, BorderLayout.SOUTH);
		add(east, BorderLayout.EAST);
		return this;
	}
}
