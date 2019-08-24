package com.mt.face;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.Silver;

public class Main
{
	public static void main(String[] args)
	{
		try { 
		    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			PlasticLookAndFeel.setPlasticTheme(new Silver());
	        UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
		//	UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Window window = new Window();
		Person mahi = new Person("Mahi Nair", new ImageIcon("MahiFace.jpg"), 0.00, true, "NOVIDEO");
		window.addPerson(mahi);
		/*while (window.isOpen())
		{
			window.update();
		}
		window.destroy();
		*/
	}
}
