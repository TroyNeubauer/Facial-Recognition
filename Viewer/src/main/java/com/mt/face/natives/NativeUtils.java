package com.mt.face.natives;

import java.util.ArrayList;

import com.mt.face.util.MiscUtil;

public class NativeUtils
{
	static
	{
		LibraryUtils.load();
	}

	public static native void setTime(double time);

	public static native double getTime();

	public static native void play();

	public static native void pause();

	public static native ArrayList<Person> getKnownPeople();

	public static native ArrayList<Person> getUnknownPeople();
	
	public static long getWindowHandle() {
		return 0;
	}
	
	public static String getClipboard() {
		return "";
	}
	
	public static void setClipboard(String clipboard) {
		
	}
	
	public static boolean isFocused() {
		return true;
	}

	public static boolean isMouseButtonDown(int button)
	{
		return false;
	}

	public static void getCursorPos(long x, long y)
	{
		MiscUtil.getUnsafe().putDouble(x, 0.0);
		MiscUtil.getUnsafe().putDouble(y, 0.0);
	}

	public static void setCursorPos(double x, double y)
	{

	}

	public static void getWindowSize(long x, long y)
	{
		MiscUtil.getUnsafe().putDouble(x, 0.0);
		MiscUtil.getUnsafe().putDouble(y, 0.0);
	}

	private static long start = -1;

	public static double getApplicationTime()
	{
		if (start == -1)
			start = System.nanoTime();
		return (System.nanoTime() - start) / 1000000000.0;
	}

}
