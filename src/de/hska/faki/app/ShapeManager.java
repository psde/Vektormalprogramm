package de.hska.faki.app;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapeManager {
	
	private ArrayList<Shape> shapes;
	private boolean changes;
	
	public ShapeManager()
	{
		this.shapes = new ArrayList<Shape>();
		this.changes = false;
	}
	
	public void setChanges(boolean change)
	{
		this.changes = change;
	}
	
	public void reset(ArrayList<Shape> shapes)
	{
		this.eraseAll();
		for (Shape curShape : shapes) 
		{
			this.addShape(curShape);
		}
	}
	
	public void addShape(Shape shape)
	{
		this.shapes.add(0, shape);
		this.changes = true;
	}
	
	public void addShapes(ArrayList<Shape> shapes)
	{
		for (Shape curShape : shapes) 
		{
			this.addShape(curShape);
		}
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
	
	public void ungroupSelectedShapes()
	{
		ArrayList<Shape> oldList = (ArrayList<Shape>)this.shapes.clone();
		
		for(int i = 0; i < oldList.size(); i++)
		{
			Shape curShape = oldList.get(i);
			if(curShape.isSelected() && curShape instanceof de.hska.faki.app.Group)
			{
				this.addShapes(((Group)curShape).getShapes());
				this.erase(curShape);
			}
		}
	}
	
	public ArrayList<Shape> getSelection() {
		ArrayList<Shape> selection = new ArrayList<Shape>();
		
		for (Shape curShape : this.shapes) 
		{
			if(curShape.isSelected())
			{
				selection.add(curShape);
			}
		}
		
		return selection;
	}
	
	public int getSelectedShapesCount()
	{
		return this.getSelection().size();
	}
	
	public int getSelectedGroupsCount()
	{
		int c = 0;
		for (Shape curShape : this.getSelection()) {
			if(curShape instanceof de.hska.faki.app.Group)
			{
				c++;
			}
		}
		return c;
	}
	
	public void moveToTop(ArrayList<Shape> shapes)
	{
		Shape tmp;
		for (int i = 0; i < shapes.size(); i++) {
			for (int j = 0; j < this.shapes.size(); j++) {
				if (this.shapes.get(j).equals(shapes.get(i))) {
					for (int k = j; k > 0; k--) {
						tmp = this.shapes.get(k - 1);
						this.shapes.set(k - 1, this.shapes.get(k));
						this.shapes.set(k, tmp);
					}
					break;
				}
			}
		}
		this.changes = true;
	}
	
	public void moveToBottom(ArrayList<Shape> shapes)
	{
		Shape tmp;
		for (int i = 0; i < shapes.size(); i++) {
			for (int j = 0; j < this.shapes.size(); j++) {
				if (this.shapes.get(j).equals(shapes.get(i))) {
					for (int k = j; k < this.shapes.size() - 1; k++) {
						tmp = this.shapes.get(k + 1);
						this.shapes.set(k + 1, this.shapes.get(k));
						this.shapes.set(k, tmp);
					}
					break;
				}
			}
		}
		this.changes = true;
	}
	
	public void moveUp(ArrayList<Shape> shapes) {
		Shape tmp;
		for (int i = 0; i < shapes.size(); i++) {
			for (int j = 0; j < this.shapes.size(); j++) {
				if (this.shapes.get(j).equals(shapes.get(i)) && j > 0) {
					tmp = this.shapes.get(j - 1);
					this.shapes.set(j - 1, this.shapes.get(j));
					this.shapes.set(j, tmp);
					break;
				}
			}
		}
		this.changes = true;
	}

	public void moveDown(ArrayList<Shape> shapes) {
		Shape tmp;
		for (int i = 0; i < shapes.size(); i++) {
			for (int j = 0; j < this.shapes.size(); j++) {
				if (this.shapes.get(j).equals(shapes.get(i)) && j < this.shapes.size() - 1) {
					tmp = this.shapes.get(j + 1);
					this.shapes.set(j + 1, this.shapes.get(j));
					this.shapes.set(j, tmp);
					break;
				}
			}
		}
		this.changes = true;
	}
	
	public void sort()
	{
		Collections.sort(this.shapes);
		this.changes = true;
	}
}
