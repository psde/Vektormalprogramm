package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public class Line extends Shape{
	private static final long serialVersionUID = -3398789473395890913L;

	public Line(Point origin, Dimension dimension, Color color) {
		super(origin, dimension, color);
	}

	@Override
	public void paintComponent(Graphics2D gr) {
		if (this.isSelected()) {
			gr.setColor(Color.DARK_GRAY);
			gr.fillRect(super.origin.x , super.origin.y, super.dimension.width, super.dimension.height);
		}
		
		gr.setColor(super.color);		
		gr.drawLine(super.origin.x , super.origin.y, super.dimension.width + super.origin.x, super.dimension.height + super.origin.y);
	}

	public void setDimension(Dimension dimension)
	{
		if(dimension.width < 0) dimension.width = 0;
		if(dimension.height < 0) dimension.height = 0;
		
		super.dimension = dimension;
	}

	@Override
	public Line clone() {
		Line newLine = new Line((Point)this.origin.clone(), (Dimension)this.dimension.clone(), this.color);
		return newLine;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
