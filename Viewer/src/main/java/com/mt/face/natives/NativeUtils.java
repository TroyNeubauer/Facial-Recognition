package com.mt.face.natives;


import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mt.face.*;
import com.mt.face.util.MiscUtil;

import sun.misc.Unsafe;

public class NativeUtils
{
	static
	{
		LibraryUtils.load();
	}

	// JNI methods defined in C++

	// Loads imgui. A valid OpenGL context must exist before this method is called
	public static native void init();

	public static native void render(int width, int height);

	public static native void setVideoTime(double time);

	public static native double getVideoTime();

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
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "<Error Reading Clipboard>";
	}

	public static void setClipboard(long text)
	{
		NativeUtils.clipboard.setContents(new StringSelection(decodeUTF8(text)), null);
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
		MiscUtil.getUnsafe().putDouble(y, Main.window.canvas.getHeight() - Mouse.getY());
	}

	public static void setCursorPos(double x, double y)
	{
		Mouse.setCursorPosition((int) x, (int) y);
	}

	private static long start = -1;

	public static double getApplicationTime()
	{
		if (start == -1) start = System.nanoTime();
		return (System.nanoTime() - start) / 1000000000.0;
	}

	public static void javaPrint(long string)
	{
		System.out.println("[Native]" + decodeUTF8(string));
	}

	
	//Taken from LWJGL 3
	static String decodeUTF8(long source)
	{
		return decodeUTF8(source, strlen64NT1(source, Integer.MAX_VALUE));
	}

	static String decodeUTF8(long source, int length)
	{
		if (length <= 0)
		{
			return "";
		}

		char[] string = new char[length];

		int i = 0, position = 0;

		while (position < length)
		{
			char c;
			Unsafe unsafe = MiscUtil.getUnsafe();
			int b0 = unsafe.getByte(null, source + position++) & 0xFF;
			if (b0 < 0x80)
			{
				c = (char) b0;
			}
			else
			{
				int b1 = unsafe.getByte(null, source + position++) & 0x3F;
				if ((b0 & 0xE0) == 0xC0)
				{
					c = (char) (((b0 & 0x1F) << 6) | b1);
				}
				else
				{
					int b2 = unsafe.getByte(null, source + position++) & 0x3F;
					if ((b0 & 0xF0) == 0xE0)
					{
						c = (char) (((b0 & 0x0F) << 12) | (b1 << 6) | b2);
					}
					else
					{
						int b3 = unsafe.getByte(null, source + position++) & 0x3F;
						int cp = ((b0 & 0x07) << 18) | (b1 << 12) | (b2 << 6) | b3;

						if (i < length)
						{
							string[i++] = (char) ((cp >>> 10) + 0xD7C0);
						}
						c = (char) ((cp & 0x3FF) + 0xDC00);
					}
				}
			}
			if (i < length)
			{
				string[i++] = c;
			}
		}

		return new String(string, 0, Math.min(i, length));
	}

	private static int strlen64NT1(long address, int maxLength)
	{
		int i = 0;
		Unsafe unsafe = MiscUtil.getUnsafe();
		if (8 <= maxLength)
		{
			int misalignment = (int) address & 7;
			if (misalignment != 0)
			{
				// Align to 8 bytes
				for (int len = 8 - misalignment; i < len; i++)
				{
					if (unsafe.getByte(null, address + i) == 0)
					{
						return i;
					}
				}
			}

			// Aligned longs for performance
			while (i <= maxLength - 8)
			{
				if (hasZeroByte(unsafe.getLong(null, address + i)))
				{
					break;
				}
				i += 8;
			}
		}

		// Tail
		for (; i < maxLength; i++)
		{
			if (unsafe.getByte(null, address + i) == 0)
			{
				break;
			}
		}

		return i;
	}

	public static boolean hasZeroByte(long value)
	{
		return ((value - 0x0101010101010101L) & ~value & 0x8080808080808080L) != 0L;
	}

}
