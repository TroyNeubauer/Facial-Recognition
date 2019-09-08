package com.mt.face.natives;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.*;

import org.apache.commons.io.FileUtils;

import com.mt.face.util.*;

public class LibraryUtils
{
	public static final String LIBRARY_NAME = "FR";
	public static final String REAL_LIBRARY_NAME = System.mapLibraryName(LIBRARY_NAME);
	public static final String LIBRARY_FILE_OVERRIDE = "FRNativeLib";
	private static boolean loaded = false;

	static
	{
		InternalLog.println("\tOS: " + System.getProperty("os.name") + " v" + System.getProperty("os.version"));
		InternalLog.println("\tJRE: " + System.getProperty("java.version") + " " + System.getProperty("os.arch"));
		InternalLog.println("\tJVM: " + System.getProperty("java.vm.name") + " v" + System.getProperty("java.vm.version") + " by " + System.getProperty("java.vm.vendor"));
		InternalLog.println();
		
		if (System.getProperty("isIDE").equals("true"))
		{
			System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparatorChar + new File("./native/").getAbsolutePath());
			copyDynamicLib();
		}

		InternalLog.println("Attempting to load Facial Recognition native library");
		try
		{
			loadFromExplictFile();
			loadFromJavaLibPath();
			loadFromClassPath();
			try
			{
				File file = new File(LibraryUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
				if (file != null && file.isFile())
				{
					InternalLog.println("Facial Recognition is running inside jar file: " + file);
					loadFromJarFile(file);
				}
			}
			catch (Throwable t)
			{
				InternalLog.println("Failed to load Facial Recognition native library from jar file!\n" + MiscUtil.getStackTrace(t));
			}
		}
		catch (Throwable t)
		{
			loaded = false;
		}
		if (!loaded)
		{
			InternalLog.println("[SEVERE] Failed to load Facial Recognition native library!!!!");
			InternalLog.println("[SEVERE] This will most likley result in errors later when native methods are called!!!");
			System.err.println("[Facial Recognition] [SEVERE] Failed to load Facial Recognition native library");
			InternalLog.dump();
		}
		
	}

	private static boolean loadFromClassPath()
	{
		if (loaded) return true;
		InternalLog.println("Attempting to load Facial Recognition native library from classpath");
		InputStream stream = Class.class.getResourceAsStream(REAL_LIBRARY_NAME);
		loadFromStream(stream, "Classpath");
		return loaded;
	}

	private static void loadFromExplictFile()
	{
		if (loaded) return;
		if (System.getProperty(LIBRARY_FILE_OVERRIDE) == null)
		{
			InternalLog.println("No explicit library specified");
		}
		else
		{
			String value = System.getProperty(LIBRARY_FILE_OVERRIDE);
			InternalLog.println("Attempting to load file " + value);
			try
			{
				System.load(value);
				InternalLog.println("Successfully loaded native lib from file: " + value + "!");
			}
			catch (Exception e)
			{
				InternalLog.println("Unable to load native lib from file: " + value + " Error " + MiscUtil.getStackTrace(e));
				InternalLog.println("Re-trying with java streams");
				try
				{
					loadFromStream(new FileInputStream(new File(value)), "File-JavaIO");
				}
				catch (FileNotFoundException e1)
				{
					InternalLog.println("Unable to load native lib from file: " + value + " using java io. Error " + MiscUtil.getStackTrace(e));
				}
			}
		}
	}

	private static void loadFromStream(InputStream stream, String method)
	{
		if (loaded) return;
		if (stream != null)
		{
			File libraryFile;
			try
			{
				libraryFile = File.createTempFile(LIBRARY_NAME, null);// Create a temp file to copy the native lib
																		// inside the jar to
			}
			catch (Throwable t)
			{
				throw new UnsatisfiedLinkError(
						"Unable to load libary " + REAL_LIBRARY_NAME + " from stream because the temp directory creation failed(" + method + ")\"\n" + MiscUtil.getStackTrace(t));
			}
			try
			{
				FileOutputStream dest = new FileOutputStream(libraryFile);
				MiscUtil.copy(stream, dest);// Copy from the jar file to the temp file
				dest.flush();
				dest.getChannel().force(true);// To ensure that System.load() doesn't get angry that "another process is
												// using the file..."
				dest.close();
			}
			catch (Throwable t)
			{
				throw new UnsatisfiedLinkError(
						"Unable to load libary " + REAL_LIBRARY_NAME + " from stream because copying to the temp file failed!(" + method + ")\"\n" + MiscUtil.getStackTrace(t));
			}
			try
			{
				System.load(libraryFile.getAbsolutePath());// Give the temp file to System.load to attempt to load it
			}
			catch (Throwable t)
			{
				throw new UnsatisfiedLinkError("Unable to load libary " + REAL_LIBRARY_NAME + " from stream!\n" + MiscUtil.getStackTrace(t));
			}
			loaded = true;
			InternalLog.println("Successfully loaded Facial Recognition native library from input stream, (" + method + ")");
			libraryFile.deleteOnExit();// Delete the temp file when we exit because we don't want to delete it when
										// System.load() is reading from
										// it
		}
		else
		{
			InternalLog.println("Failed to load Facial Recognition native library from stream because the stream was null (" + method + ")");
		}
	}

	private static void loadFromJarFile(File file)
	{
		if (loaded) return;
		InternalLog.println("Attempting to load Facial Recognition native library from jar file");
		try
		{
			ZipFile zip = new ZipFile(file);// Create a zip file object from the file so we can look at the entries
			ZipEntry entry = zip.getEntry(REAL_LIBRARY_NAME);// Grab the entry with the file name we want
			if (entry == null)
			{// zip.getEntry will return null if the desired file name is not found so throw
				// an exception if the entry is null
				zip.close();// Close the zip file because the other line wont get executed
				throw new FileNotFoundException("Unable to find file " + REAL_LIBRARY_NAME + " in zip file " + file);
			}
			InternalLog.println("Found Facial Recognition native library in zip file (" + entry + ") last modified "
					+ new SimpleDateFormat().format(new Date(entry.getLastModifiedTime().toMillis())));
			loadFromStream(zip.getInputStream(entry), "Jar File");// Now get the input stream for reading the library
																	// and use it to load it
			zip.close();
		}
		catch (Throwable e)
		{
			InternalLog.println("Failed to load Facial Recognition native library from jar file " + file + "\n" + MiscUtil.getStackTrace(e));
		}
	}

	private static boolean loadFromJavaLibPath()
	{
		if (loaded) return true;
		InternalLog.println("Attempting to load Facial Recognition native library from java.library.path");
		try
		{
			System.loadLibrary(LIBRARY_NAME);
			loaded = true;
			InternalLog.println("Successfully loaded Facial Recognition native library from java.library.path");
		}
		catch (Throwable t)
		{
			InternalLog.println("Failed to load Facial Recognition native library from java.library.path");
		}
		return loaded;
	}

	/**
	 * Loads all required native libraries for Facial Recognition. Can be called as
	 * many times without harm
	 */
	public static void load()
	{
		// Empty to call static initializer
	}

	private static void copyDynamicLib()
	{
		InternalLog.println("Attempting to copy FR dynamic library");
		copyFile(new File("../bin/"));
	}

	private static void copyFile(File file)
	{
		if (file.isDirectory())
		{
			for (File child : file.listFiles())
			{
				copyFile(child);
			}
		}
		else
		{
			if (file.getName().equals(System.mapLibraryName("FR")))
			{
				InternalLog.println("Copying file " + file);
				try
				{
					FileUtils.copyFile(file, new File("./native/", file.getName()));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
