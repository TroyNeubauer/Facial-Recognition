package com.mt.face;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.mt.face.natives.NativeUtils;

public class Window extends JFrame
{
	private JPanel panel;
	private VideoRenderer renderer;
	private JPanel main;
	private JButton open;
	private JFileChooser openVideo;
	private JList<Person> known;
	private JList<Person> unknown;
	private DefaultListModel<Person> knownModel;
	private DefaultListModel<Person> unknownModel;

	//trying to use these to fulfill the "which video is x face from"
	private String[] fileNames;
	private int i = 0;


	public Window()
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     	setExtendedState(Window.MAXIMIZED_BOTH);
		setState(Frame.NORMAL);
		main = new JPanel();
		main.setLayout(new BorderLayout());
		setContentPane(main);
		setUndecorated(true);
	
		panel = new JPanel(new BorderLayout());
		setSize(1280, 720);
		Canvas canvas = new Canvas();
		renderer = new VideoRenderer();
		openVideo = new JFileChooser(System.getProperty("user.home") +"/Desktop");
		open = new JButton("Open a Video");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int x = openVideo.showOpenDialog(null);

				if (x == JFileChooser.APPROVE_OPTION)
				{
					URL mediaURL = null;
					try
					{
						mediaURL = openVideo.getSelectedFile().toURL();
					} catch (MalformedURLException malformedURLException)
					{
						System.err.println("Could not create URL for the file");
					}
					if (mediaURL != null)
					{
						// Player mediaPlayer = Manager.
					}
				}
			}
		});
		
		open.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		open.setBackground(ColorScheme.LIGHT_BLUE);

		panel.add(open, BorderLayout.NORTH);
		panel.add(canvas, BorderLayout.CENTER);
		main.add(panel, BorderLayout.CENTER);
		
		knownModel = new DefaultListModel<>();
		unknownModel = new DefaultListModel<>();
		known = new JList<>(knownModel);
		known.setCellRenderer(new PersonRenderer());
		unknown = new JList<>(unknownModel);
		unknown.setCellRenderer(new PersonRenderer());
		main.add(new JScrollPane(known), BorderLayout.WEST);
		main.add(new JScrollPane(unknown), BorderLayout.EAST);
		
		JPanel north = new JPanel();
		north.setLayout(new BorderLayout());
		JLabel knownFaces = new JLabel ("Known Faces");
		knownFaces.setOpaque(true);
		knownFaces.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		knownFaces.setBackground(ColorScheme.SKY_BLUE);
		north.add(knownFaces, BorderLayout.WEST);
		JLabel unknownFaces = new JLabel("Unknown Faces");
		unknownFaces.setOpaque(true);
		unknownFaces.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		unknownFaces.setBackground(ColorScheme.SKY_BLUE);
		north.add(unknownFaces, BorderLayout.EAST);
		main.add(north, BorderLayout.NORTH);

		JPanel south = new JPanel();
		south.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton export = new JButton();
		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				  BufferedWriter writer = null;
				    try {
				        writer = new BufferedWriter(new FileWriter("./output.txt"));
				        for(Person p: exportPeople())
				        	writer.write(p.toString() + "\n");
				        	
				    } catch (IOException e1) {
				        System.err.println(e1);
				    } finally {
				        if (writer != null) {
				            try {
				                writer.close();
				            } catch (IOException e2) {
				                System.err.println(e2);
				            }
				        }
				    }
				
			}
		});

		export.setText("Export");
		export.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		export.setOpaque(true);
		export.setBackground(ColorScheme.LIGHT_TURQUOISE);
		south.add(export);
		JButton exit = new JButton();
		
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
					
			}
		});
		
		exit.setText("Exit");
		exit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		exit.setOpaque(true);
		exit.setBackground(ColorScheme.LIGHT_TURQUOISE);
		south.add(exit);
		main.add(south, BorderLayout.SOUTH);
		
		setVisible(true);

		try
		{
			Display.setParent(canvas);
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		NativeUtils.init();
	}

	public boolean isOpen()
	{
		return Display.isCloseRequested();
	}

	public void update()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		renderer.render();

		Display.update();
		Display.sync(60);
	}

	public void destroy()
	{
		Display.destroy();
	}

	public void addPerson(Person p)
	{
		if(p.isKnown())
			knownModel.addElement(p);
		else
			unknownModel.addElement(p);
	}
	
	//need to add something to move a person to known list from unknown once they become "known"

	public ArrayList<Person> exportPeople()
	{
		ArrayList<Person> exportPeople = new ArrayList<>();
		for (int i = 0; i < knownModel.getSize(); i++)
		{
			exportPeople.add(knownModel.getElementAt(i));
		}
		for (int i = exportPeople.size(); i < exportPeople.size() + unknownModel.getSize(); i++)
		{
			exportPeople.add(unknownModel.getElementAt(i));
		}
		return exportPeople;
	}
}
