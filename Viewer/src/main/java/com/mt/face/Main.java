package com.mt.face;

import javax.swing.*;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;

import com.mt.face.natives.*;

public class Main
{
	public static Window window;

	private static boolean initDone = false;

	public static void main(String[] args) throws Exception
	{
		LibraryUtils.load();
		SwingUtilities.invokeAndWait(() -> {
			try
			{
				UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
				window = new Window(false);
				Person mahi = new Person("Mahi Nair", new ImageIcon("MahiFace.jpg"), 0.00, true, "NOVIDEO");
				window.addPerson(mahi);
				initDone = true;
			}
			catch (Exception e)
			{
				System.err.println("Unexpected error while doing GUI initialization");
				e.printStackTrace();
				System.exit(1);
			}
		});
		while (!initDone)// Wait for initialization to complete
		{
			Thread.sleep(1);
		}
		window.nativeInit();
		System.out.println("Back in java from native init");
		while (window.isOpen())
		{
			window.update();
		}
		window.destroy();

	}
}
