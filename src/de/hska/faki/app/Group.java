package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Group extends Shape{
	private static final long serialVersionUID = 5573805402677703243L;
	
	private ArrayList<Shape> shapes;
	
	public Group() {
		super(new Point(0,0), new Dimension(0,0), null);
		this.shapes = new ArrayList<Shape>();
	}
	
	public void addShape(Shape shape)
	{
		shape.setSelected(false);
		this.shapes.add(0, shape);
		updateCoordinates();
	}
	
	public void removeShape(Shape shape)
	{
		this.shapes.remove(shape);
		updateCoordinates();
	}
	
	public ArrayList<Shape> getShapes()
	{
		return this.shapes;
	}
	
	private void updateCoordinates()
	{
		if(this.shapes.size() == 0)
		{
			System.out.println("updateCoordinates with 0 shapes?");
			return;
		}
		
		Point newOrigin = (Point)shapes.get(0).origin.clone();
		Dimension newDimension = (Dimension)shapes.get(0).dimension.clone();
		
		for (Shape curShape : this.shapes) {
			if(curShape.origin.x < newOrigin.x) newOrigin.x = curShape.origin.x;
			if(curShape.origin.y < newOrigin.y) newOrigin.y = curShape.origin.y;
			
			if((curShape.origin.x + curShape.dimension.width) > newDimension.width) newDimension.width = (curShape.origin.x + curShape.dimension.width);
			if((curShape.origin.y + curShape.dimension.height) > newDimension.height) newDimension.height = (curShape.origin.y + curShape.dimension.height);
		}
		System.out.println("newOrigin: " + newOrigin.x + " " + newOrigin.y);
		this.origin = newOrigin;
		this.dimension = newDimension;
	}
	
	@Override
	public void setOrigin(Point origin)
	{
		Point offset = new Point(origin.x - this.origin.x, origin.y - this.origin.y);
		System.out.println("setOrigin offset: " + offset.x + " " + offset.y);
		for (Shape curShape : this.shapes) {
			Point newOrigin = new Point(curShape.getOrigin().x + offset.x, curShape.getOrigin().y + offset.y);
			curShape.setOrigin(newOrigin);
		}
		updateCoordinates();
	}
	
	@Override
	public void setDimension(Dimension dimension)
	{
		return;
	}
	
	@Override
	public Group clone()
	{
		Group newGroup = new Group();
		for (Shape curShape : this.shapes) {
			newGroup.addShape((Shape)curShape.clone());
		}
		return newGroup;
	}
	
	@Override
	public void paintComponent(Graphics2D gr) {
		if(this.isSelected())
		{
			gr.setColor(Color.DARK_GRAY);
			gr.fillRect(super.origin.x, super.origin.y, super.dimension.width - super.origin.x, super.dimension.height - super.origin.y);
		}
		
		for (Shape curShape : this.shapes) {
			curShape.paintComponent(gr);
		}
	}

}
