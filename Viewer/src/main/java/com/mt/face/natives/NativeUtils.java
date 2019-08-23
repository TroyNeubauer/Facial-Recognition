package com.mt.face.natives;

public class NativeUtils
{

	static
	{
		LibraryUtils.load();
	}

	public static native void setTime(double time);
	public static native double getTime();
	
	
}
