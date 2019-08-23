package com.mt.face;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;

public class Window extends JFrame
{
	private JPanel panel;
	private JFileChooser uploadVideo;
	private JButton upload;
	private VideoRenderer renderer;

	public Window()
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel = new JPanel(new BorderLayout());
		setSize(1280, 720);

		Canvas canvas = new Canvas();
		renderer = new VideoRenderer();

		uploadVideo = new JFileChooser();
		upload = new JButton("Upload a Video");
		upload.addActionListener(new UploadListener());

		panel.add(upload, BorderLayout.NORTH);
		panel.add(canvas, BorderLayout.CENTER);

		add(panel);
		setVisible(true);

		try
		{
			Display.setParent(canvas);
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
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

	private class UploadListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int x = uploadVideo.showOpenDialog(null);

			if (x == JFileChooser.APPROVE_OPTION)
			{
				URL mediaURL = null;
				try
				{
					mediaURL = uploadVideo.getSelectedFile().toURL();
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
	}
}
