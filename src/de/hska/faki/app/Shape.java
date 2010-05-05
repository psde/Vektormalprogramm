package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public abstract class Shape extends JPanel {
	private static final long serialVersionUID = 2550836039772833235L;
	
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
	public abstract void update();
	public abstract Shape clone();
	
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
	
	public boolean isPointInside(Point pos)
	{
		Point topLeft = (Point) this.origin.clone();
		Point bottomRight = new Point(topLeft.x + this.dimension.width, topLeft.y + this.dimension.height);
		
		if(bottomRight.x < topLeft.x)
		{
			int temp = bottomRight.x;
			bottomRight.x = topLeft.x;
			topLeft.x = temp;
		}
		
		if(bottomRight.y < topLeft.y)
		{
			int temp = bottomRight.y;
			bottomRight.y = topLeft.y;
			topLeft.y = temp;
		}
		
		
		if(pos.x > topLeft.x && pos.y > topLeft.y && pos.x < bottomRight.x && pos.y < bottomRight.y)
			return true;
		
		return false;
	}
}
