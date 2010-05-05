package de.hska.faki.app;

import java.awt.Point;
import java.util.ArrayList;

public class ShapeManager {
	
	private ArrayList<Shape> shapes;
	private boolean changes;
	
	public ShapeManager()
	{
		this.shapes = new ArrayList<Shape>();
		this.changes = false;
	}
	
	public void addShape(Shape shape)
	{
		this.shapes.add(0, shape);
		this.changes = true;
	}
	
	public void eraseAll()
	{
		this.shapes.clear();
		this.changes = true;
	}
	
	public void erase(Shape shape)
	{
		if(this.shapes.remove(shape))
			this.changes = true;
	}
		
	public ArrayList<Shape> getShapes()
	{
		return this.shapes;
	}
	
	public Shape getTopShape()
	{
		return this.shapes.get(0);
	}
	
	public Shape getShapeAtPoint(Point pos)
	{
		for (Shape curShape : this.getShapes()) {
			if(curShape.isPointInside(pos))
			{
				return curShape;
			}
		}
		return null;
	}	
	
	public Shape getFirstSelectedShape()
	{
		for (Shape curShape : this.getShapes()) {
			if(curShape.isSelected())
			{
				return curShape;
			}
		}
		return null;
	}
	
	public void deselectAll()
	{
		for (Shape curShape : this.getShapes()) {
			curShape.setSelected(false);
		}
	}
	
	public void selectShape(Shape shape)
	{
		for (Shape curShape : this.getShapes()) {
			if(curShape == shape)
				curShape.setSelected(true);
		}
	}
	
	public boolean hasChanged()
	{
		return this.changes;
	}
	
	public void groupSelectedShapes()
	{
		Group newGroup = new Group();
		/*for (Shape curShape : this.getShapes()) {
			if(curShape.isSelected())
			{
				newGroup.addShape((Shape)curShape.clone());
				//this.shapes.remove(curShape);
			}
		}*/

		ArrayList<Shape> oldList = (ArrayList<Shape>) this.shapes.clone();
		
		for(int i = 0; i < oldList.size(); i++)
		{
			Shape curShape = oldList.get(i);
			if(curShape.isSelected())
			{
				newGroup.addShape((Shape)curShape.clone());
				this.erase(curShape);
			}
		}
		
		this.addShape(newGroup);
	}
	
	public int getSelectedShapesCount()
	{
		int c = 0;
		for (Shape curShape : this.getShapes()) {
			if(curShape.isSelected())
			{
				c++;
			}
		}
		return c;
	}
	
	public int getSelectedGroupsCount()
	{
		int c = 0;
		for (Shape curShape : this.getShapes()) {
			if(curShape.isSelected() && curShape instanceof de.hska.faki.app.Group)
			{
				c++;
			}
		}
		return c;
	}
}
