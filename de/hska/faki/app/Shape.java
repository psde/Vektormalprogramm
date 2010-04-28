package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public abstract class Shape extends JPanel {
	
	protected Point origin;
	protected Dimension dimension;
	protected Color color;
	protected boolean selected;
	
	public Shape(Point origin, Dimension dimension, Color color)
	{
		this.origin = origin;
		this.dimension = dimension;
		this.color = color;
	}
	
	public abstract void paintComponent(Graphics2D gr);
	
	public Point getOrigin()
	{
		return (Point)this.origin.clone();
	}
	
	public Dimension getDimensions()
	{
		return (Dimension)this.dimension.clone();
	}
	
	public void setOrigin(Point origin)
	{
		this.origin = origin;
	}
	
	public void setDimension(Dimension dimension)
	{
		this.dimension = dimension;
	}
	
	public boolean isSelected()
	{
		return this.selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
}
