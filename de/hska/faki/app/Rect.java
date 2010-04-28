package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Rect extends Shape{
	public Rect(Point origin, Dimension dimension, Color color) {
		super(origin, dimension, color);
	}

	@Override
	public void paintComponent(Graphics2D gr) {
		if (this.isSelected()) {
			gr.setColor(Color.DARK_GRAY);
			gr.fillRect(super.origin.x , super.origin.y, super.dimension.width, super.dimension.height);
		}
		
		gr.setColor(super.color);
		gr.fillRect(super.origin.x , super.origin.y, super.dimension.width, super.dimension.height);
		
		gr.setColor(Color.black);
		gr.drawRect(super.origin.x , super.origin.y, super.dimension.width, super.dimension.height);
	}
}
