package com.mt.face;

import java.awt.EventQueue;

import javax.swing.*;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;


public class Main
{
	public static Window window;
	
	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
		            Window window = new Window();
		            Person mahi = new Person("Mahi Nair", new ImageIcon("MahiFace.jpg"), 0.00, true, "NOVIDEO");
					window.addPerson(mahi);
		            window.setVisible(true);
		            } 
				catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		});
		/*while (window.isOpen())
		{
			window.update();
		}
		window.destroy();
		*/
	}
}
