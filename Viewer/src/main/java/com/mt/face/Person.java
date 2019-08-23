package com.mt.face;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Person
{
	private String name;
	private ImageIcon face;
	private double firstSeen;
	private boolean known;
	
	//each person object has the videoFile which they are first seen in as an attribute?
	private String videoFile;
	
	public Person(String name, ImageIcon face, double firstSeen, boolean known, String videoFile)
	{
		this.name = name;
		Image temp = face.getImage().getScaledInstance(100,  100, Image.SCALE_SMOOTH);
		this.face = new ImageIcon(temp);
		this.firstSeen = firstSeen;
		this.known = known;
		this.videoFile = videoFile;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void SetName(String n)
	{
		name = n;
	}
	
	public ImageIcon getFace()
	{
		return face;
	}
	
	public double getFirstTime()
	{
		return firstSeen;
	}
	
	public boolean isKnown()
	{
		return known;
	}
	
	public void setKnown(boolean k)
	{
		known = k;
	}
	
	public String getVideoFile()
	{
		return videoFile;
	}
	
	public String toString() 
	{
		return name + ", " + videoFile + ", " + firstSeen;
	}

}
