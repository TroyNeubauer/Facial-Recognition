package com.mt.face;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.mt.face.natives.NativeUtils;

public class Window extends JFrame
{
	private JPanel video;
	private VideoRenderer renderer;
	private JPanel main;
	private JButton open;
	private JFileChooser openVideo;
	private JSlider videoSlider;
	private JList<Person> known;
	private JList<Person> unknown;
	private DefaultListModel<Person> knownModel;
	private DefaultListModel<Person> unknownModel;
	private JButton playAndPause;
	private boolean isPaused = false;

	private ImageIcon pause = new ImageIcon(new ImageIcon("pause.png").getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
	private ImageIcon play = new ImageIcon(new ImageIcon("play.png").getImage().getScaledInstance(70,  70, Image.SCALE_SMOOTH));

	//trying to use these to fulfill the "which video is x face from"
	private String[] fileNames;
	private int i = 0;

	public static final Font HELVETICA_LARGE = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
	public static final Font HELVETICA_SMALL = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

	public Window()
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     	setExtendedState(Window.MAXIMIZED_BOTH);
		setState(Frame.NORMAL);
		main = new JPanel();
		main.setLayout(new BorderLayout());
		setContentPane(main);
		setUndecorated(true);
	
		video = new JPanel(new BorderLayout());
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
		
		open.setFont(HELVETICA_LARGE);
		open.setBackground(ColorScheme.LIGHT_BLUE);
		
		JPanel videoControls = new JPanel();
		videoControls.setLayout(new FlowLayout());
		playAndPause = new JButton();
		playAndPause.setIcon(pause);
		playAndPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isPaused)
				{
					playAndPause.setIcon(pause);
					NativeUtils.play();
					isPaused = false;
				}
				else
				{
					playAndPause.setIcon(play);
					NativeUtils.pause();
					isPaused = true;
				}	
			}	
		});
		
		videoControls.add(playAndPause);
		videoSlider = new JSlider(SwingConstants.HORIZONTAL, 0 , 100, 0);
		videoSlider.setPaintTicks(true);
        videoSlider.setPaintLabels(true);
        videoSlider.setPreferredSize(new Dimension(500, 30));
        videoSlider.setMajorTickSpacing(20);
        videoSlider.setMinorTickSpacing(5);
		videoSlider.addChangeListener(new ChangeListener() {
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider) e.getSource();
			        int currentValue = source.getValue();
			        if (!source.getValueIsAdjusting()) {
			            System.out.println("VAL: " + currentValue);
			           //this method needs to put the video at whatever time the user clicks
			        }
			    }
		});
		
		videoControls.add(videoSlider);

		video.add(open, BorderLayout.NORTH);
		video.add(canvas, BorderLayout.CENTER);
		video.add(videoControls, BorderLayout.SOUTH);
		main.add(video, BorderLayout.CENTER);
		
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
		knownFaces.setFont(HELVETICA_LARGE);
		knownFaces.setBackground(ColorScheme.SKY_BLUE);
		north.add(knownFaces, BorderLayout.WEST);
		JLabel unknownFaces = new JLabel("Unknown Faces");
		unknownFaces.setOpaque(true);
		unknownFaces.setFont(HELVETICA_LARGE);
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
		export.setFont(HELVETICA_LARGE);
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
		exit.setFont(HELVETICA_LARGE);
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
