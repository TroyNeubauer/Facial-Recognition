package com.mt.face;

public class Main
{
	public static void main(String[] args)
	{
		Window window = new Window();
		while (window.isOpen())
		{
			window.update();
		}
		window.destroy();
	}
}
