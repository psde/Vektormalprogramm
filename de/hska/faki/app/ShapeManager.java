package de.hska.faki.app;

import java.util.ArrayList;

public class ShapeManager {
	
	private ArrayList<Shape> shapes;
	
	public ShapeManager()
	{
		this.shapes = new ArrayList<Shape>();
	}
	
	public void addShape(Shape shape)
	{
		this.shapes.add(shape);
	}
	
	public ArrayList<Shape> getShapes()
	{
		return this.shapes;
	}
}
