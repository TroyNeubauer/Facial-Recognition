package com.mt.face;

import javax.swing.ImageIcon;

public class Main
{
	public static Window window;
	
	public static void main(String[] args)
	{
		window = new Window();
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
