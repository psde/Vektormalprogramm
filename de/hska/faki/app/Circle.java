package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Circle extends Shape{
	public Circle(Point origin, int radius, Color color) {
		super(origin, new Dimension(radius, radius), color);
	}

	@Override
	public void paintComponent(Graphics2D gr) {
		if (this.isSelected()) {
			gr.setColor(Color.DARK_GRAY);
			gr.fillRect(super.origin.x , super.origin.y, super.dimension.width, super.dimension.height);
		}
		
		gr.setColor(super.color);
		gr.fillOval(super.origin.x , super.origin.y, super.dimension.width, super.dimension.height);
		
		gr.setColor(Color.black);
		gr.drawOval(super.origin.x , super.origin.y, super.dimension.width, super.dimension.height);
	}
	
	@Override
	public void setDimension(Dimension dimension)
	{
		int max = dimension.height;
		if(dimension.width > max) max = dimension.width;
		
		super.dimension = new Dimension(max, max);
	}
}
