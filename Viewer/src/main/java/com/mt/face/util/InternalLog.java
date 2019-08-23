package com.mt.face.util;

import java.io.*;
import java.util.*;

public class InternalLog
{

	private static final ByteArrayOutputStream BYTES = new ByteArrayOutputStream(1024);
	private static final PrintStream DEBUG_STREAM = new PrintStream(BYTES);

	private static final boolean USE_SYSOUT;

	static
	{
		String result = System.getProperty("TBSeperateLog");
		if ((result == null)
				|| !(result.equalsIgnoreCase("false") || result.equals("0") || result.equalsIgnoreCase("no")))
		{
			USE_SYSOUT = false;
		} else
		{
			USE_SYSOUT = true;
		}
	}

	public static void println()
	{
		DEBUG_STREAM.println();
		if (USE_SYSOUT)
			System.out.println();
	}

	public static void println(String s)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(s);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(s);
		}

	}

	public static void println(Object o)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(o);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(o);
		}
	}

	public static void println(boolean[] b)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(Arrays.toString(b));
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(Arrays.toString(b));
		}
	}

	public static void println(boolean b)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(b);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(b);
		}
	}

	public static void println(byte[] b)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(Arrays.toString(b));
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(Arrays.toString(b));
		}
	}

	public static void println(byte b)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(b);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(b);
		}
	}

	public static void println(short[] s)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(Arrays.toString(s));
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(Arrays.toString(s));
		}
	}

	public static void println(short s)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(s);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(s);
		}
	}

	public static void println(int[] i)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(Arrays.toString(i));
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(Arrays.toString(i));
		}
	}

	public static void println(int i)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(i);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(i);
		}
	}

	public static void println(float[] f)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(Arrays.toString(f));
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(Arrays.toString(f));
		}
	}

	public static void println(float f)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(f);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(f);
		}
	}

	public static void println(long[] l)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(Arrays.toString(l));
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(Arrays.toString(l));
		}
	}

	public static void println(long l)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(l);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(l);
		}
	}

	public static void println(double[] d)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(Arrays.toString(d));
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(Arrays.toString(d));
		}
	}

	public static void println(double d)
	{
		DEBUG_STREAM.print("[Troy Berry] ");
		DEBUG_STREAM.println(d);
		if (USE_SYSOUT)
		{
			System.out.print("[Troy Berry] ");
			System.out.println(d);
		}
	}

	public static void print(String s)
	{
		DEBUG_STREAM.print(s);
		if (USE_SYSOUT)
			System.out.print(s);

	}

	public static void print(Object o)
	{
		DEBUG_STREAM.print(o);
		if (USE_SYSOUT)
			System.out.print(o);
	}

	public static void print(boolean[] b)
	{
		DEBUG_STREAM.print(Arrays.toString(b));
		if (USE_SYSOUT)
			System.out.print(Arrays.toString(b));

	}

	public static void print(boolean b)
	{
		DEBUG_STREAM.print(b);
		if (USE_SYSOUT)
			System.out.print(b);
	}

	public static void print(byte[] b)
	{
		DEBUG_STREAM.print(Arrays.toString(b));
		if (USE_SYSOUT)
			System.out.print(Arrays.toString(b));
	}

	public static void print(byte b)
	{
		DEBUG_STREAM.print(b);
		if (USE_SYSOUT)
			System.out.print(b);
	}

	public static void print(short[] s)
	{
		DEBUG_STREAM.print(Arrays.toString(s));
		if (USE_SYSOUT)
			System.out.print(Arrays.toString(s));
	}

	public static void print(short s)
	{
		DEBUG_STREAM.print(s);
		if (USE_SYSOUT)
			System.out.print(s);
	}

	public static void print(int[] i)
	{
		DEBUG_STREAM.print(Arrays.toString(i));
		if (USE_SYSOUT)
			System.out.print(Arrays.toString(i));
	}

	public static void print(int i)
	{
		DEBUG_STREAM.print(i);
		if (USE_SYSOUT)
			System.out.print(i);
	}

	public static void print(float[] f)
	{
		DEBUG_STREAM.print(Arrays.toString(f));
		if (USE_SYSOUT)
			System.out.print(Arrays.toString(f));
	}

	public static void print(float a)
	{
		DEBUG_STREAM.print(a);
		if (USE_SYSOUT)
			System.out.print(a);
	}

	public static void print(long[] l)
	{
		DEBUG_STREAM.print(Arrays.toString(l));
		if (USE_SYSOUT)
			System.out.print(Arrays.toString(l));
	}

	public static void print(long l)
	{
		DEBUG_STREAM.print(l);
		if (USE_SYSOUT)
			System.out.print(l);
	}

	public static void print(double[] d)
	{
		DEBUG_STREAM.print(Arrays.toString(d));
		if (USE_SYSOUT)
			System.out.print(Arrays.toString(d));
	}

	public static void print(double d)
	{
		DEBUG_STREAM.print(d);
		if (USE_SYSOUT)
			System.out.print(d);
	}

	public static String getLog()
	{
		return BYTES.toString();
	}

	public static void dump()
	{
		try
		{
			FileOutputStream stream = new FileOutputStream(new File("FR Internal Log.txt"));
			stream.write(BYTES.toByteArray());
			stream.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}