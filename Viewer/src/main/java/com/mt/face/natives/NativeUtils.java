package com.mt.face.natives;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mt.face.*;
import com.mt.face.util.MiscUtil;

public class NativeUtils
{
	static
	{
		LibraryUtils.load();
	}

	// JNI methods defined in C++
	public static native void init();
	
	public static native void setTime(double time);

	public static native double getTime();

	public static native void play();

	public static native void pause();

	public static native ArrayList<Person> getKnownPeople();

	public static native ArrayList<Person> getUnknownPeople();
	
	public static native void onMouseEvent(int button, boolean press);
	public static native void onKeyEvent(int key, boolean press);
	public static native void onChar(char c);
	public static native void onScrollEvent(double x, double y);

	// JNI methods to be called from C++
	public static long getWindowHandle()
	{
		return 0;
	}

	private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	public static String getClipboard()
	{
		try
		{
			return (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "<Error Reading Clipboard>";
	}

	public static void setClipboard(String text)
	{
		StringSelection selection = new StringSelection(text);
		NativeUtils.clipboard.setContents(selection, null);
	}

	public static boolean isFocused()
	{
		return Main.window.isFocused();
	}

	public static boolean isMouseButtonDown(int button)
	{
		return Mouse.isButtonDown(button);
	}

	public static void getCursorPos(long x, long y)
	{
		MiscUtil.getUnsafe().putDouble(x, Mouse.getX());
		MiscUtil.getUnsafe().putDouble(y, Mouse.getY());
	}

	public static void setCursorPos(double x, double y)
	{
		Mouse.setCursorPosition((int) x, (int) y);
	}

	public static void getWindowSize(long x, long y)
	{
		MiscUtil.getUnsafe().putInt(x, Main.window.getWidth());
		MiscUtil.getUnsafe().putInt(y, Main.window.getHeight());
	}

	private static long start = -1;

	public static double getApplicationTime()
	{
		if (start == -1)
			start = System.nanoTime();
		return (System.nanoTime() - start) / 1000000000.0;
	}

}
