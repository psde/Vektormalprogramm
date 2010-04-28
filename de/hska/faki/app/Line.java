package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Line extends Shape{
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
}
